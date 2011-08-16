package controllers;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

import play.Play;
import play.PlayPlugin;
import play.data.binding.types.DateBinder;
import play.vfs.VirtualFile;
import siena.Model;

public class SienaFixutre {

	static Pattern keyPattern = Pattern.compile("([^(]+)\\(([^)]+)\\)");
	static Pattern datePattern = Pattern
			.compile("^(\\d{1,2})\\.(\\d{1,2})\\.(\\d{2})$");
	static Pattern timePattern = Pattern
			.compile("^(\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");
	static Pattern dateTimePattern = Pattern
			.compile("^(\\d{1,2})\\.(\\d{1,2})\\.(\\d{2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");

	public static void deleteAll() throws Exception {
		File modelDir = new File("app/models");
		if (modelDir.isDirectory()) {
			File[] listFiles = modelDir.listFiles();
			for (File file : listFiles) {
				String name = "models."
						+ file.getName().substring(0,
								file.getName().indexOf(".java"));
				Class<? extends Model> modelClass = (Class<? extends Model>) Play.classloader
						.loadClass(name);
				List<? extends Model> models = modelClass.newInstance()
						.all(modelClass).fetch();
				for (Model model : models) {
					model.delete();
				}
			}
		}

	}

	public static void load(String name) throws Exception {
		VirtualFile yamlFile = null;

		for (VirtualFile vf : Play.javaPath) {
			yamlFile = vf.child(name);
			if (yamlFile != null && yamlFile.exists()) {
				break;
			}
		}
		InputStream is = Play.classloader.getResourceAsStream(name);
		if (is == null) {
			throw new RuntimeException("Cannot load fixture " + name
					+ ", the file was not found");
		}
		Yaml yaml = new Yaml();
		Object o = yaml.load(is);
		if (o instanceof LinkedHashMap<?, ?>) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<Object, Map<?, ?>> objects = (LinkedHashMap<Object, Map<?, ?>>) o;
			Map<String, Object> idCache = new HashMap<String, Object>();
			for (Object key : objects.keySet()) {
				Matcher matcher = keyPattern.matcher(key.toString().trim());
				if (matcher.matches()) {
					String type = matcher.group(1);
					String id = matcher.group(2);
					if (!type.startsWith("models.")) {
						type = "models." + type;
					}
					if (idCache.containsKey(type + "-" + id)) {
						throw new RuntimeException("Cannot load fixture "
								+ name + ", duplicate id '" + id
								+ "' for type " + type);
					}
					Map<String, String[]> params = new HashMap<String, String[]>();
					if (objects.get(key) == null) {
						objects.put(key, new HashMap<Object, Object>());
					}

					serialize(objects.get(key), "object", params);
					@SuppressWarnings("unchecked")
					Class<Model> cType = (Class<Model>) Play.classloader
							.loadClass(type);
					// resolveDependencies(cType, params, idCache);
					Model model = cType.newInstance();
					for (Field f : model.getClass().getFields()) {
						if (f.getName().equals("id")) {
							continue;
						} else if (f.getType().isAssignableFrom(Map.class)) {
							f.set(model, objects.get(key).get(f.getName()));
						} else if (Model.class.isAssignableFrom(f.getType())) {
							String k = f.getType().getName() + "-"
									+ objects.get(key).get(f.getName());
							Object value = null;
							if ((value = idCache.get(k)) != null) {
								f.set(model, value);
							}
						} else {
							Object value = objects.get(key).get(f.getName());
							if (value != null) {
								if (f.getType().isEnum()) {
									for (Object enumConstant : f.getType()
											.getEnumConstants()) {
										if (enumConstant.toString()
												.equalsIgnoreCase(
														value.toString())) {
											f.set(model, enumConstant);
											break;
										}
									}

								} else if (f.getType().isAssignableFrom(
										java.util.Date.class)) {
									Matcher date = datePattern.matcher(value
											.toString());
									Matcher time = timePattern.matcher(value
											.toString());
									Matcher dateTime = dateTimePattern
											.matcher(value.toString());
									Calendar c = Calendar.getInstance();
									c.setTimeInMillis(0);
									if (date.matches()) {
										int day = Integer.parseInt(date
												.group(1)); // day
										int month = Integer.parseInt(date
												.group(2)); // month
										int year = Integer.parseInt(date
												.group(3)); // year
										c.set(Calendar.DAY_OF_MONTH, day);
										c.set(Calendar.MONTH, month);
										c.set(Calendar.YEAR, year);
										c.getTime();
										f.set(model, c.getTime());
									} else if (time.matches()) {
										int hours = Integer.parseInt(time
												.group(1)); // hours
										int min = Integer.parseInt(time
												.group(2)); // minutes
										int sec = Integer.parseInt(time
												.group(3)); // seconds
										c.set(Calendar.HOUR_OF_DAY, hours);
										c.set(Calendar.MINUTE, min);
										c.set(Calendar.SECOND, sec);
										f.set(model, c.getTime());
									} else if (dateTime.matches()) {
										int day = Integer.parseInt(dateTime
												.group(1)); // day
										int month = Integer.parseInt(dateTime
												.group(2)); // month
										int year = Integer.parseInt(dateTime
												.group(3)); // year
										int hours = Integer.parseInt(dateTime
												.group(4)); // hours
										int min = Integer.parseInt(dateTime
												.group(5)); // minutes
										int sec = Integer.parseInt(dateTime
												.group(6)); // seconds
										c.set(Calendar.DAY_OF_MONTH, day);
										c.set(Calendar.MONTH, month);
										c.set(Calendar.YEAR, year);
										c.set(Calendar.HOUR_OF_DAY, hours);
										c.set(Calendar.MINUTE, min);
										c.set(Calendar.SECOND, sec);
										f.set(model, c.getTime());
									} else {
										System.out
												.println("Cannot convert value '"
														+ value
														+ "' to type java.util.Date for field '"
														+ f.getName() + "';");
									}

								} else {
									f.set(model, value);
								}
							}
						}

					}

					model.insert();

					Class<?> tType = cType;
					System.out.println("put " + tType.getName() + "-" + id);
					idCache.put(tType.getName() + "-" + id, model);

				}
			}
		}
		// Most persistence engine will need to clear their state
		for (PlayPlugin plugin : Play.plugins) {
			plugin.afterFixtureLoad();
		}

	}

	static void serialize(Map<?, ?> values, String prefix,
			Map<String, String[]> serialized) {
		for (Object key : values.keySet()) {
			Object value = values.get(key);
			if (value == null) {
				continue;
			}
			if (value instanceof Map<?, ?>) {
				serialize((Map<?, ?>) value, prefix + "." + key, serialized);
			} else if (value instanceof Date) {
				serialized.put(prefix + "." + key.toString(),
						new String[] { new SimpleDateFormat(DateBinder.ISO8601)
								.format(((Date) value)) });
			} else if (value instanceof List<?>) {
				List<?> l = (List<?>) value;
				String[] r = new String[l.size()];
				int i = 0;
				for (Object el : l) {
					r[i++] = el.toString();
				}
				serialized.put(prefix + "." + key.toString(), r);
			} else if (value instanceof String
					&& value.toString().matches("<<<\\s*\\{[^}]+}\\s*")) {
				Matcher m = Pattern.compile("<<<\\s*\\{([^}]+)}\\s*").matcher(
						value.toString());
				m.find();
				String file = m.group(1);
				VirtualFile f = Play.getVirtualFile(file);
				if (f != null && f.exists()) {
					serialized.put(prefix + "." + key.toString(),
							new String[] { f.contentAsString() });
				}
			} else {
				serialized.put(prefix + "." + key.toString(),
						new String[] { value.toString() });
			}
		}
	}
}
