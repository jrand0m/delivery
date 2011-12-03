package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import models.MenuItem;
import models.MenuItemComponent;
import models.MenuItemGroup;
import models.Order;
import models.Restaurant;
import models.RestaurantCategory;
import models.device.RestaurantDevice;
import models.geo.City;
import models.settings.SystemSetting;
import models.time.WorkHours;
import models.users.RestaurantBarman;
import models.users.SystemAdministrator;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import enumerations.DeviceStatus;

@With(Secure.class)
@Check(SystemAdministrator.class)
public class Admin extends Controller {
	public static void index() {
		render();
	}

	public static void showCities() {
		List<City> cities = City.findAll();
		render(cities);
	}

	public static void showTypes() {
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		render(types);
	}

	public static void showCategories() {
		List<MenuItemGroup> categories = MenuItemGroup.findAll();
		render(categories);
	}

	public static void showMenuItems(Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			id = restaurants.get(0).id;
		}
		List<MenuItem> items = MenuItem.find("restaurant.id = ?", id).fetch();
		renderArgs.put("items", items);
		render();
	}

	public static void showOrders(Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			List<Order> orders = Order.find("order by orderCreated desc")
					.fetch();
			renderArgs.put("orders", orders);
		} else {
			List<Order> orders = Order.find(
					"restaurant.id = ? order by orderCreated desc", id).fetch();
			renderArgs.put("orders", orders);
		}
		render();
	}

	public static void showRestaurants(Long id) {
		List<Restaurant> restaurants = null;
		if (id == null) {
			restaurants = Restaurant.findAll();
		} else {
			restaurants = Restaurant.find("city.id = ?", id).fetch();
		}
		renderArgs.put("restaurants", restaurants);
		renderArgs.put("cities", City.find("display = ?", true).fetch());
		render();
	}

	public static void showUsers() {

	}

	public static void showSettings() {
		List<SystemSetting> settings = SystemSetting.findAll();
		renderArgs.put("settings", settings);
		render();
	}

	public static void addType(RestaurantCategory cat) {
		System.out.println("cat >>>> " + cat.id);
		if (cat.categoryDisplayNameUA == null) {
			renderArgs.put("thankmessage", "Please create new one.");
			render();
		}
		cat.create();
		renderArgs.put("thankmessage", "Sucessfuly added : "
				+ cat.categoryDisplayNameUA);
		render();
	}

	public static void addCategory(MenuItemGroup group, Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			renderArgs.put("message", "Please create new one.");
			render();
		}

		Restaurant r = Restaurant.findById(id);
		group.restaurant = r;
		group.save();
		renderArgs.put("message", "Sucessfuly added : " + group.name);
		render();
	}

	public static void editCategory(Long id) {
		notFoundIfNull(id);
		MenuItemGroup group = MenuItemGroup.findById(id);
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("group", group);
		renderArgs.put("restaurants", restaurants);
		renderTemplate("Admin/addCategory.html");
	}

	public static void addMenuItem(MenuItem item, Long id, Long groupid) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		List<MenuItemGroup> groups = MenuItemGroup.findAll();
		renderArgs.put("groups", groups);
		if (item == null || id == null || groupid == null) {
			renderArgs.put("message", "Please create new one.");
			render();
		}
		item.menuItemCreated = new Date();
		item.restaurant = Restaurant.findById(id);
		item.menuItemGroup = MenuItemGroup.findById(groupid);
		item.create();
		renderArgs.put("message", "Created!");
		render();
	}

	public static void editMenuItem(Long id) {
		MenuItem item = MenuItem.findById(id);
		renderArgs.put("group", item.menuItemGroup.id);
		renderArgs.put("id", item.restaurant.id);
		renderArgs.put("item", item);
		render("Admin/addMenuItem.html");
	}

	public static void addCity(City city) {
		System.out.println("name =>" + city.cityNameUA + "; display =>"
				+ request.headers.toString());
		if (city.cityNameUA == null) {
			render();
		}
		city.save();
		render();
	}

	public static void editCity(Long id) {
		notFoundIfNull(id);
		City city = City.findById(id);
		notFoundIfNull(city);
		renderArgs.put("city", city);
		renderTemplate("Admin/addCity.html");
	}

	public static void editType(Long id) {
		notFoundIfNull(id);
		RestaurantCategory cat = RestaurantCategory.findById(id);
		notFoundIfNull(id);
		renderArgs.put("cat", cat);
		renderTemplate("Admin/addType.html");
	}

	public static void addRestaurant(Restaurant restaurant, Long catid,
			Long cityid, String openfrom, String opento, String barmanlogin,
			String barmanpwd) {

		List<City> cities = City.findAll();
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		renderArgs.put("cities", cities);
		renderArgs.put("types", types);
		if (cityid == null) {
			renderArgs.put("errormessage", "no cityid");
			render();
		}
		City city = City.findById(cityid);
		// notFoundIfNull(city);
		RestaurantCategory type = RestaurantCategory.findById(catid);
		RestaurantBarman b = null;
		if (restaurant.id != null) {
			Restaurant r = Restaurant.findById(restaurant.id);
			r.desc = restaurant.desc;
			r.showOnIndex = restaurant.showOnIndex;
			r.title = restaurant.title;
			r.twoLetters = restaurant.twoLetters;
			restaurant = r;
			b = RestaurantBarman.find("restaurant = ?", restaurant).first();

		} else {
			b = new RestaurantBarman();
			restaurant.device = new RestaurantDevice();
			restaurant.device.deviceActivatedDate = new Date();
			restaurant.device.status = DeviceStatus.ACTIVE;
			restaurant.device.lastPing = new Date(0);
			restaurant.device.save();
			b.joinDate = new Date();
		}
		b.login = barmanlogin;
		b.usr_name = barmanlogin;
		b.password = barmanpwd;
		b.restaurant = restaurant;

		restaurant.city = city;
		restaurant.category = type;
		if (restaurant.workHours == null) {
			restaurant.workHours = new WorkHours(openfrom, opento);
		} else {
			restaurant.workHours.updateAll(openfrom, opento);
		}
		restaurant.workHours.save();
		restaurant.save();
		b.save();
		render();
	}

	public static void deleteCity() {

		index();
	}

	public static void editRestaurant(Long id) {
		notFoundIfNull(id);
		Restaurant restaurant = Restaurant.findById(id);
		renderArgs.put("restaurant", restaurant);
		renderArgs.put("catid", restaurant.category.id);
		renderArgs.put("cityid", restaurant.city.id);
		// renderArgs.put("openfrom",
		// restaurant.workHours.regularDays.iterator().next().from);
		// renderArgs.put("opento",
		// restaurant.workHours.regularDays.iterator().next().to);
		RestaurantBarman b = RestaurantBarman
				.find("restaurant = ?", restaurant).first();
		renderArgs.put("barmanlogin", b.login);
		renderArgs.put("barmanpwd", b.password);
		List<City> cities = City.findAll();
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		renderArgs.put("cities", cities);
		renderArgs.put("types", types);
		renderTemplate("Admin/addRestaurant.html");
	}

	public static void deleteRestaurant(Long id) {
		notFoundIfNull(id, "missing argument");
		Restaurant restaurant = Restaurant.findById(id);
		notFoundIfNull(restaurant, "restaraunt not found");
		flash.put(
				"errormessage",
				String.format(
						"cannot delete restaurant '%s[%s]' because there is some orders assigned",
						restaurant.title, restaurant.id));
		index();
	}

	public static void addComp(Long id, String name, String descr, Integer price) {
		MenuItem item = MenuItem.findById(id);
		MenuItemComponent component = new MenuItemComponent();
		component.itm_name = name;
		component.itm_price = price;
		component.itm_desc = descr;
		component.itm_root = item;
		if (item.showComponents == false) {
			item.showComponents = true;
			item.save();
		}
		component.save();
		editMenuItem(item.id);

	}

	public static void deleteComponent(Long id) {
		MenuItemComponent component = MenuItemComponent.findById(id);
		component.delete();
	}

	public static void uploadLogo(Long id, File logo)
			throws FileNotFoundException {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			render();
		}
		Restaurant restaurant = Restaurant.findById(id);

		restaurant.logo = new Blob();
		restaurant.logo.set(new FileInputStream(logo),
				MimeTypes.getContentType(logo.getName()));
		restaurant.save();

	}

}
