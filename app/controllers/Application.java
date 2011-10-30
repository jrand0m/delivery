package controllers;

import helpers.CACHE_KEYS;
import helpers.GeoDataHelper;
import helpers.PropertyVault;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jobs.DevBootStrap;
import models.MenuItem;
import models.MenuItemComponent;
import models.MenuItemGroup;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.RestaurantCategory;
import models.dto.extern.BasketJSON;
import models.dto.extern.LastOrdersJSON;
import models.dto.extern.MenuCompWrapJson;
import models.dto.extern.MenuComponentsJSON;
import models.geo.City;
import models.geo.IpGeoData;
import models.geo.Street;
import models.geo.UserAddress;
import models.settings.SystemSetting;
import models.users.AnonymousEndUser;
import models.users.EndUser;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.i18n.Lang;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;
import play.test.Fixtures;
import enumerations.OrderStatus;

public class Application extends Controller {

	// TODO Make more flexible(extract to SystemSetting)
	public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;

	public static class RENDER_KEYS {
		public static final String USER = "user";
		public static final String INDEX_RESTAURANTS = "restaurants";
		public static final String AVALIABLE_CITIES = "cities";
		public static final String SHOW_MENU_RESTAURANTS = "restaurants";
		public static final String RESTAURANTS_CATEGORIES = "categories";
	}

	public static class SESSION_KEYS {
		public static final String CITY_ID = "city";
	}

	public static void loadFix() {
		Fixtures.deleteDatabase();
		new DevBootStrap().doJob();
		renderText("Cleared db and forsed fixture load");
	}

	@Before(unless = { "serveLogo", "loadFix", "comps", "changeCity",
			"changeLang" }/*
						 * unless =
						 * {"getCurrentUser","guessCity","deleteOrRemOrderItem"
						 * ,"createNewOpenOrder", "createOrAddOrderItem", }
						 */)
	public static void _pre() {
		EndUser user = getCurrentUser();
		renderArgs.put(RENDER_KEYS.USER, user);
		if (!session.contains(SESSION_KEYS.CITY_ID)) {
			Logger.debug("No city defined in cookies");
			session.put(SESSION_KEYS.CITY_ID,
					Application.guessCity(request.remoteAddress).getId());
		}
		flash.put("url", request.url);
	}

	public static void order(String id) {
		notFoundIfNull(id);
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		notFoundIfNull(user);
		Order order = Order.find(Order.HQL.BY_SHORT_ID, id).first();
		notFoundIfNull(order);
		if (!order.orderOwner.equals(user)) {
			notFound();
		}
		renderArgs.put("order", order);
		render("/Application/order.html");
	}



	public static void checkout(String id) {
		// FIXME move to locker ?
		notFoundIfNull(id);
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		notFoundIfNull(user);
		Order order = Order.find(Order.HQL.BY_SHORT_ID, id).first();
		notFoundIfNull(order);
		if (!order.orderOwner.equals(user)) {
			notFound();
		}
		if (order.orderStatus != OrderStatus.OPEN) {
			redirect("Application.order", id);
		}
		if (order.items.isEmpty()) {
			redirect("Application.showMenu", order.restaurant.getId());
		}
		City city = order.restaurant.city;
		List<Street> streets = (List<Street>) Cache.get("streets" + city.id);
		if (streets == null) {
			streets = Street.find("city = ? and use = ?", city, true).fetch();
			Cache.add("streets" + city.id, streets, "1d");
		}
		renderArgs.put("streets", streets);
		renderArgs.put("order", order);
		render("/Application/prepareOrder.html");
	}

	public static void index() {
		List<Restaurant> restaurants = (List<Restaurant>) Cache
				.get(CACHE_KEYS.INDEX_PAGE_RESTAURANTS
						+ session.get(SESSION_KEYS.CITY_ID));
		if (restaurants == null) {
			String cityId = session.get(SESSION_KEYS.CITY_ID);
			// TODO decide whether to cache city
			City city = City.getCityByIdSafely(cityId);
			restaurants = Restaurant.find(
					Restaurant.HQL.BY_CITY_AND_SHOW_ON_INDEX, city, true)
					.fetch(4);
			Cache.set(CACHE_KEYS.INDEX_PAGE_RESTAURANTS + cityId, restaurants,
					"2h");
		}
		List<City> cityList = (List<City>) Cache
				.get(CACHE_KEYS.AVALIABLE_CITIES);
		if (cityList == null) {
			cityList = City.find(City.HQL.BY_DISPLAY, true).fetch();
			Cache.set(CACHE_KEYS.AVALIABLE_CITIES, cityList, "8h");
		}
		renderArgs.put(RENDER_KEYS.INDEX_RESTAURANTS, restaurants);
		renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
		render();
	}

	private static EndUser getCurrentUser() {
		EndUser user = null;
		if (Security.isConnected()) {
			user = EndUser.find(EndUser.HQL.BY_LOGIN, Security.connected())
					.first();
		}
		return user;
	}

	public static void showRestaurants() {
		List<City> cityList = (List<City>) Cache
				.get(CACHE_KEYS.AVALIABLE_CITIES);
		if (cityList == null) {
			cityList = City.find(City.HQL.BY_DISPLAY, true).fetch();
			Cache.set(CACHE_KEYS.AVALIABLE_CITIES, cityList, "8h");
		}
		City city = City.getCityByIdSafely(session.get(SESSION_KEYS.CITY_ID));
		List<Restaurant> restaurants = Restaurant.find(Restaurant.HQL.BY_CITY,
				city).fetch();
		// categories
		Set<RestaurantCategory> categories = new HashSet<RestaurantCategory>();
		for (Restaurant rest : restaurants) {
			RestaurantCategory category = rest.category;
			if (category != null) {
				categories.add(category);
			}
		}
		renderArgs.put(RENDER_KEYS.RESTAURANTS_CATEGORIES, categories);
		renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
		renderArgs.put(RENDER_KEYS.SHOW_MENU_RESTAURANTS, restaurants);
		render();
	}

	public static void showMenu(Long id) {
		notFoundIfNull(id);
		Restaurant restaurant = Restaurant.findById(id);
		notFoundIfNull(restaurant);
		List<MenuItemGroup> menuItems = restaurant.menuBook;
		renderArgs.put("restaurant", restaurant);
		// FIXME Cache for 5 hours or it will die.
		render(menuItems);
	}

	public static void newUser() {
		render();
	}

	public static void registerNewUser(EndUser user) {
		Logger.debug(">>> Registering new user %s", user.toString());
		user.joinDate = new Date();
		user.lastLoginDate = new Date();
		user.create();
		Logger.debug(">>> TODO: Try converting order history.");
		try {
			Secure.authenticate(user.login, user.password, false);
		} catch (Throwable e) {
			Logger.error(e, "Failed to login in App.registerNewUser()");
			error();
		}

		index();
	}

	/**
	 * id:ff808181333095ab0133309ed4e90005 name:sda city:1 surname:asd
	 * street:asd email:asd@cacc.ccom apartment:ds phone:asd oplata:on
	 * 
	 * */
	public static void checkAndSend(String id, Long aid, String name,
			Integer city, String sname, Long streetid, @Email String email,
			String app, @Phone String phone, String oplata) {
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		if (user == null)
			badRequest();
		checkAuthenticity();
		if (id == null || id.isEmpty()) {
			badRequest();
		}

		Order o = Order.findById(id);
		if (o == null) {
			badRequest();
		}
		if (!o.orderOwner.equals(user)) {
			badRequest();
		}
		// TODO 'FROZEN' STATUS
		if (!o.orderStatus.equals(OrderStatus.OPEN)) {
			badRequest();
		}
		Restaurant r = o.restaurant;
		for (OrderItem itm : o.items) {
			if (!itm.menuItem.restaurant.equals(r)) {
				o.orderStatus = OrderStatus.DECLINED;
				o.save();
				Logger.error(
						"Different restaturants in order items. order will be declined. IP: %s, user id: %s ",
						request.remoteAddress, user.id);
				error("Consistency error, root node mismatch. Order declined");
			}
		}
		UserAddress address = null;
		if (user instanceof AnonymousEndUser) {
			if ((user.usr_name == null || user.usr_name.isEmpty())) {
				user.usr_name = name;
				if (!validation.valid(user.usr_name).ok) {
					validation.addError("name", "name.required");
				}
			}
			if ((user.usr_surname == null || user.usr_surname.isEmpty())) {
				user.usr_surname = sname;
			}

			if (user.phoneNumber == null || !user.phoneNumber.isEmpty()) {
				user.phoneNumber = phone;
				validation.required(phone);
			}

			address = new UserAddress();
			Street str = null;
			if (streetid != null) {
				str = Street.findById(streetid);
			}
			if (str != null && str.city.equals(o.restaurant.city)) {
				address.street = str;
			} else {
				validation.addError("address.street", "street.notacceptable");
			}
			address.appartamentsNumber = app;
			address.user = user;
			// TODO do proper validation
			address.validateAndCreate();
			if (validation.hasErrors()) {
				params.flash();
				validation.keep();
				checkout(o.getShortHandId());
			}
			user.save();

		} else {
			if (aid != null) {
				address = UserAddress.findById(aid);
				if (address == null || !address.user.equals(user)) {
					address = null;
				}
			}
			if (address == null) {
				address = new UserAddress();
				Street str = null;
				if (streetid != null) {
					str = Street.findById(streetid);
				}
				if (str != null && str.city.equals(o.restaurant.city)) {
					address.street = str;
				} else {
					validation.addError("address.street",
							"street.notacceptable");
				}
				address.appartamentsNumber = app;
				address.user = user;
				// TODO do proper validation
				address.validateAndCreate();
				if (validation.hasErrors()) {
					params.flash();
					validation.keep();
					checkout(o.getShortHandId());
				}
			}
		}
		o.deliveryAddress = address;
		o.orderStatus = OrderStatus.SENT;
		o.save();
		order(o.getShortHandId());
	}

	/* ------------ UTIL Pages -------------- */
	/**
	 * Change Language
	 * */
	public static void changeLang(String lang) {
		Lang.change(lang);
		String url = flash.get("url");
		if (url == null || url.isEmpty()) {
			url = "/";
		}
		redirect(url);
	}

	public static void changeCity(Long id) {
		Logger.debug("changing city to id = %s", id);
		City city = City.findById(id);
		if (city == null) {
			session.put(SESSION_KEYS.CITY_ID, GeoDataHelper
					.getSystemDefaultCity().getId().toString());
		} else {
			session.put(SESSION_KEYS.CITY_ID, id.toString());
		}
		String url = flash.get("url");
		if (url == null || url.isEmpty()) {
			url = "/";
		}
		redirect(url);
	}

	/* ------------ AJAX ---------------- */

	public static void getLastOrders(boolean top) {
		ArrayList<LastOrdersJSON> o = new ArrayList<LastOrdersJSON>();
		if (session.contains(SESSION_KEYS.CITY_ID)) {
			City city = City.getCityByIdSafely(session
					.get(SESSION_KEYS.CITY_ID));
			List<Order> recent = null;
			if (top) {
				await(5000);
				recent = Order.find(Order.HQL.LAST_ORDERS_BY_CITY_AND_STATUS,
						city, OrderStatus.ACCEPTED).fetch(10);
			} else {
				boolean isEmpty = true;
				while (isEmpty) {
					recent = Order
							.find(Order.HQL.LAST_ORDERS_BY_CITY_AND_STATUS_AND_AFTER_DATE,
									city, OrderStatus.ACCEPTED, request.date)
							.fetch();
					await(10000);
					isEmpty = recent.isEmpty();
				}
			}
			for (Order ord : recent) {
				o.add(new LastOrdersJSON(ord));
			}
		} else {
			Logger.debug("No city in sesion, waiting to prevent ddos/dos");
			await(20000);
		}
		renderJSON(o);
	}

	public static void comps(final Long id) {
		notFoundIfNull(id);
		await(1000);
		MenuItem mi = MenuItem.findById(id);
		List<MenuItemComponent> comps = MenuItemComponent.find(
				MenuItemComponent.FIELDS.ITM_ROOT + "= ?", mi).fetch();
		List<MenuComponentsJSON> asJsons = new ArrayList<MenuComponentsJSON>();
		for (MenuItemComponent i : comps) {
			asJsons.add(new MenuComponentsJSON(i));
		}
		MenuCompWrapJson wrap = new MenuCompWrapJson();
		wrap.no = id.toString();
		wrap.dc = mi.description();
		wrap.nm = mi.name();
		wrap.pc = mi.price;
		wrap.items = asJsons;
		renderJSON(wrap);
	}

	public static void addOrderItem(Long id, Long... component) {
		if (!Security.isConnected() || id == null) {
			await(15000);
			notFound("Order not found");
		}
		MenuItem itm = MenuItem.findById(id);
		// FIXME problems if we dynamicly delete order and user will refresh
		// page. add to js basket failure to refresh by updateurl
		notFoundIfNull(itm);
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		notFoundIfNull(user);
		// TODO add safe caching
		Order order = Order.find(
				Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS_AND_RESTAURANT, user,
				OrderStatus.OPEN, itm.restaurant).first();
		notFoundIfNull(order);
		OrderItem oi = new OrderItem(itm, order, component);
		oi.save();
		order.items.add(oi);
		order.save();
		renderJSON(new BasketJSON(order));
	}

	public static void cngOrderItem(Long id, Integer count) {
		if (!Security.isConnected() || id == null || count == null) {
			await(15000);
			notFound("Order not found");
		}
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		notFoundIfNull(user);
		OrderItem itm = OrderItem.findById(id);
		notFoundIfNull(itm);
		if (!itm.order.orderOwner.equals(user)) {
			notFound();
		}
		if (itm.order.orderStatus == OrderStatus.OPEN) {
			if (count < 1) {
				itm.delete();
			} else {
				itm.count = count;
			}
			renderJSON(new BasketJSON(itm.order));
		}
		notFound();
	}

	public static void basket(Long chart) {
		/*
		 * FIXME best place to create new user when user reaches place with
		 * basket create token basket checks if the user is logged in and if it
		 * is not searches for token and if token found and correct then creates
		 * new anon user and logs him in. name for the user will be
		 * Anonymous_<num>
		 * 
		 * if user chooses to register than we create new user for him and
		 * transferring all orders, addresses from anon to new one and deleting
		 * anon.
		 * 
		 * also there is need to remove all anonymous that has last login date >
		 * 30 days and have no orders in state sent.
		 * 
		 * please notice that some far away time in future there will be a
		 * moment when we will need to run batch job for deleting all anons with
		 * last login date > 1 year(or other period, mb 3 month) which have
		 * associated orders in above sent state
		 */
		notFoundIfNull(chart);
		Order order = null;
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		if (user == null) {
			user = createAnonymousUser();
		}
		Restaurant restaurant = (Restaurant) Restaurant.findById(chart);
		notFoundIfNull(restaurant);
		order = Order.find(
				Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS_AND_RESTAURANT, user,
				OrderStatus.OPEN, restaurant).first();
		if (order == null) {
			order = createNewOpenOrder(user, restaurant);
		}
		renderJSON(new BasketJSON(order));
	}

	/* ----------- private -------------- */
	public static void serveLogo(long id) throws IOException {
		final Restaurant restaurant = Restaurant.findById(id);
		notFoundIfNull(restaurant);
		InputStream is;
		if (restaurant.logo.exists()) {
			response.setContentTypeIfNotSet(restaurant.logo.type());
			is = restaurant.logo.get();
		} else {
			is = new FileInputStream(Play.applicationPath
					+ "/public/images/no_image.jpg");
		}
		renderBinary(is);
	}

	private static EndUser createAnonymousUser() {
		AnonymousEndUser user = new AnonymousEndUser();
		user.create();
		user.login = "Anonymous_" + user.getId();
		user.lastLoginDate = new Date();
		user.password = Crypto.passwordHash(user.login + "1");
		session.put("username", user.login);
		user.save();
		return user;
	}

	// FIXME fix guess system by moving to new MyJob.now()
	private static City guessCity(String ip) {
		Boolean guessByIp = (Boolean) Cache
				.get(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED);
		if (guessByIp == null) {
			String guessIpEnabled = PropertyVault
					.getSystemValueFor(SystemSetting.KEYS.GUESS_CITY_ENABLED);
			guessByIp = Boolean.valueOf(guessIpEnabled);
			Cache.set(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED, guessByIp, "8h");
		}
		if (!guessByIp) {
			play.Logger.debug("Guessing disabled, returning default");
			return GeoDataHelper.getSystemDefaultCity();
		}
		play.Logger.debug("Guessing enabled, trying to guess city by ip");
		IpGeoData igdata = IpGeoData.find(IpGeoData.HQL.BY_IP, ip).first();
		if (igdata == null) {
			igdata = await(GeoDataHelper.requestFromExternalServer(ip));
		}
		if (igdata == null || igdata.city == null) {
			return GeoDataHelper.getSystemDefaultCity();
		}
		return igdata.city;
	}

	/**
	 * intended for local use so no 'public' modifier
	 * 
	 * @param jpaBase
	 * */
	private static Order createNewOpenOrder(final EndUser user,
			Restaurant jpaBase) {
		Logger.debug(">>> Creating new order for user: %s", user);
		Order o = new Order();
		o.orderStatus = OrderStatus.OPEN;
		o.orderOwner = user;
		if (user == null) {
			o.anonSID = session.getId();
		}
		o.deleted = false;
		o.orderCreated = new Date();
		o.restaurant = jpaBase;
		o.create();
		Logger.debug(">>> Created new order: %s", o.toString());
		return o;
	}

}