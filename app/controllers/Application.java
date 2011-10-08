package controllers;

import helpers.CACHE_KEYS;
import helpers.GeoDataHelper;
import helpers.OrderUtils;
import helpers.PropertyVault;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jobs.DevBootStrap;
import models.MenuItem;
import models.MenuItemGroup;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.RestaurantCategory;
import models.RestaurantNetwork;
import models.dto.extern.LastOrdersJSON;
import models.geo.City;
import models.geo.IpGeoData;
import models.geo.IpGeoData.HQL;
import models.settings.SystemSetting;
import models.settings.SystemSetting.KEYS;
import models.users.EndUser;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.i18n.Lang;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;
import play.test.Fixtures;
import enumerations.OrderStatus;
import enumerations.UserRoles;
import enumerations.UserStatus;

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

	@Before(unless = {"serveLogo","loadFix"}/*unless = {"getCurrentUser","guessCity","deleteOrRemOrderItem","createNewOpenOrder", "createOrAddOrderItem", }*/)
	public static void _pre() {
		EndUser user = getCurrentUser();
		renderArgs.put(RENDER_KEYS.USER, user);
		Order order = null;
		if (user != null) {
			order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS, user,
					OrderStatus.OPEN).first();
		} else {
			order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
					OrderStatus.OPEN, session.getId()).first();
		}

		if (order != null) {
			renderArgs.put("basketCount", order.items.size());
		} else {
			renderArgs.put("basketCount", 0);
		}
		if (!session.contains(SESSION_KEYS.CITY_ID)){
			Logger.debug("No city defined in cookies");
			session.put(SESSION_KEYS.CITY_ID, Application.guessCity(request.remoteAddress).getId() );
		}
	}
	@After
	public static void _post(){
		await(1000);
	}

	public static void deliveryAndPaymentMethod() {
		Order order = null;
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		if (user != null) {
			order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS, user,
					OrderStatus.OPEN).first();
		} else {
			order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
					OrderStatus.OPEN, session.getId()).first();
		}
		if (order == null) {
			order = Application.createNewOpenOrder(null);
		}
		renderArgs.put("order", order);
		render("/Application/prepareOrder.html");
	}

	public static void index() {
		List<Restaurant> restaurants = (List<Restaurant>) Cache
				.get(CACHE_KEYS.INDEX_PAGE_RESTAURANTS+session.get(SESSION_KEYS.CITY_ID));
		if (restaurants == null) {
			String cityId = session.get(SESSION_KEYS.CITY_ID);
			//TODO decide whether to cache city
			City city = City.getCityByIdSafely(cityId);
			restaurants = Restaurant.find(Restaurant.HQL.BY_CITY_AND_SHOW_ON_INDEX, city, true).fetch(4);
			Cache.set(CACHE_KEYS.INDEX_PAGE_RESTAURANTS+cityId, restaurants, "2h");
		}
		List<City> cityList = (List<City>) Cache.get(CACHE_KEYS.AVALIABLE_CITIES);
		if (cityList == null){
			cityList = City.find(City.HQL.BY_DISPLAY, true).fetch();
			Cache.set(CACHE_KEYS.AVALIABLE_CITIES, cityList, "8h");
		}
		Logger.debug("%s cities will be dispalyed", cityList.size());
		Logger.debug("%s restaurants will be dispalyed", restaurants.size());
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
	public static void showRestaurants(){
		List<City> cityList = (List<City>) Cache.get(CACHE_KEYS.AVALIABLE_CITIES);
		if (cityList == null){
			cityList = City.find(City.HQL.BY_DISPLAY, true).fetch();
			Cache.set(CACHE_KEYS.AVALIABLE_CITIES, cityList, "8h");
		}
		City city = City.getCityByIdSafely(session.get(SESSION_KEYS.CITY_ID));
		List<Restaurant> restaurants = Restaurant.find(Restaurant.HQL.BY_CITY, city ).fetch();
		//categories
		Set<RestaurantCategory> categories = new HashSet<RestaurantCategory>();
		for (Restaurant rest : restaurants){
			RestaurantCategory category = rest.category;
			if (category!=null){
				categories.add(category);
			}
		}
		renderArgs.put(RENDER_KEYS.RESTAURANTS_CATEGORIES, categories);
		renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
		renderArgs.put(RENDER_KEYS.SHOW_MENU_RESTAURANTS, restaurants);
		render();
	}
	public static void showMenu(Long id) {
		Restaurant restaurant = Restaurant.findById(id);
		List<MenuItemGroup> menuItems = restaurant.menuBook;
		renderArgs.put("restaurant", restaurant);
		render(menuItems);
	}

	public static void newUser() {
		render();
	}

	public static void registerNewUser(EndUser user) {
		Logger.debug(">>> Registering new user %s", user.toString());
		user.joinDate = new Date();
		user.lastLoginDate = new Date();
		user.userStatus = UserStatus.PENDING_APPROVEMENT;
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

	public static void checkAndSend(String id) {
		Logger.debug("Sending... id = %s", id);
		Order o = Order.findById(id);
		o.orderStatus = OrderStatus.SENT;
		o.save();
		Logger.debug("Sent... id = %s", id);
		ok();
	}
	public static void basket() {
		Logger.debug(">>> Entering basket");
		Order order = null;
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		if (user != null) {
			order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS, user,
					OrderStatus.OPEN).first();
		} else {
			order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
					OrderStatus.OPEN, session.getId()).first();
		}
		if (order == null) {
			order = Application.createNewOpenOrder(null);
		}
		renderArgs.put("order", order);
		render();

	}

	/* ------------ UTIL Pages -------------- */
	/**
	 * Change Language
	 * */
	public static void changeLang(String lang) {
		Lang.change(lang);
		index();
	}
	
	public static void changeCity(Long id){
		Logger.debug("changing city to id = %s", id);
		City city  = City.findById(id);
		if (city == null){
			session.put(SESSION_KEYS.CITY_ID, GeoDataHelper.getSystemDefaultCity().getId().toString());
		} else {
			session.put(SESSION_KEYS.CITY_ID, id.toString() );
		}
		redirect("/");
	}
	
	/* ------------ AJAX ----------------*/
	
	public static void getLastOrders(boolean top){
		ArrayList<LastOrdersJSON> o = new ArrayList<LastOrdersJSON>();
		if(session.contains(SESSION_KEYS.CITY_ID)){
			City city = City.getCityByIdSafely(session.get(SESSION_KEYS.CITY_ID));
			List<Order> recent = null;
			if (top){
				recent  = Order.find(Order.HQL.LAST_ORDERS_BY_CITY, city).fetch(10);
			} else {
				boolean isEmpty = true;
				while (isEmpty){
					recent  = Order.find(Order.HQL.LAST_ORDERS_BY_CITY_AND_AFTER_DATE, city, request.date).fetch();
					await(10000);
					isEmpty = recent.isEmpty();
				}
			}
			for (Order ord : recent){
				o.add(new LastOrdersJSON(ord));
			}
		} else {
			Logger.debug("No city in sesion, waiting to prevent ddos/dos");
			await(20000);
		}
		
		renderJSON(o);
	}
	
	public static void addOrderItem(@Required Long id, @Required Integer count) {
		Logger.debug(">>> Adding items with id %s in count %s", id, count);
		if (Security.isConnected()) {
			Logger.debug(">>> Connected user login: %s", Security.connected());
			EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
			Order order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS,
					user, OrderStatus.OPEN).first();
			if (order == null) {
				Logger.debug(">>> No open order found, creating one..");
				order = Application.createNewOpenOrder(user);
			}
			createOrAddOrderItem(id, order, count);
			Logger.debug(">>> Order item added, sending ok response");

		} else {
			String bid = session.getId();
			Logger.debug(">>> Annonymous sid: %s", bid);
			Order order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
					OrderStatus.OPEN, bid).first();
			if (order == null) {
				order = Application.createNewOpenOrder(null);
			}
			createOrAddOrderItem(id, order, count);
		}
		renderJSON("{}");
	}

	public static void delOrderItem(@Required Long id, @Required Integer count) {
		Logger.debug(">>> Delitinging items with id %s in count %s", id, count);
		if (Security.isConnected()) {
			Logger.debug(">>> Connected user login: %s", Security.connected());
			EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
			Order order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS,
					user, OrderStatus.OPEN).first();
			if (order == null) {
				Logger.debug(">>> no order found, sending ok response");
				ok();
				return;
			}
			deleteOrRemOrderItem(id, order, count);
			Logger.debug(">>> Order item deleted, sending ok response");
		} else {
			String bid = session.getId();
			Logger.debug(">>> Annonymous basket id: %s", bid);
			Order order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
					OrderStatus.OPEN, bid).first();
			if (order == null) {
				order = Application.createNewOpenOrder(null);
			}
			deleteOrRemOrderItem(id, order, count);
		}
		renderJSON("{}");

	}
	
	/* -----------  private --------------*/
	
	/**
	 * @param id
	 * @param order
	 */
	private static void deleteOrRemOrderItem(Long id, Order order, Integer count) {
		Logger.debug(
				">>> deleting or decreasinf item %s for order #%s, count %s",
				id, order.id, count);
		Logger.debug(">>> session id: %s", session.getId());
		count = OrderUtils.normalizeCount(count);
		MenuItem menuItem = MenuItem.findById(id);
		OrderItem orderitem = OrderItem.find(
				OrderItem.HQL.BY_ORDER_AND_MENU_ITEM, order, menuItem).first();
		if (orderitem == null) {
			Logger.debug(">>> no such item found, sending ok response");
			return;
		}
		int remain = orderitem.count - count;
		if (remain < 1) {
			Logger.debug(">>> Deleting id = %s, from order #%s", id,
					order.getShortHandId());
			orderitem.deleted = true;
			order.items.remove(orderitem);
			if (order.items.size() == 0) {
				order.restaurant = null;
				order.save();
			}
		} else {
			Logger.debug(">>> Decreased count for id = %s, to = %s, order #%s",
					id, remain, order.getShortHandId());
			orderitem.count = remain;
		}
		orderitem.save();

	}

	private static void createOrAddOrderItem(Long menuItemId, Order order,
			Integer count) {

		count = OrderUtils.normalizeCount(count);
		MenuItem menuItem = MenuItem.findById(menuItemId);
		if (menuItem == null) {
			Logger.warn("MenuItem not found:  %s", menuItemId.toString());
			return;
		}
		if (order.restaurant != null
				&& !menuItem.restaurant.equals(order.restaurant)) {
			// TODO error message about "cannot add form other restaurant"
			todo();
			return;
		}
		if (order.restaurant == null) {
			order.restaurant = menuItem.restaurant;
			order.save();
		}
		OrderItem orderItem = null;
		List<OrderItem> list = order.items;
		for (OrderItem i : list) {
			Logger.debug(">>> Checking %s vs %s (%s)", menuItem, i.menuItem,
					menuItem == i.menuItem);
			if (menuItem == i.menuItem) {
				orderItem = i;
				break;
			}
		}
		if (orderItem == null) {
			Logger.debug(">>> Item not found, creating new");
			orderItem = new OrderItem(menuItem, order);
			orderItem.count = count;
			orderItem.create();
		} else {
			Logger.debug(">>> Item found, adding count");
			if (orderItem.count + count > MAX_ITEM_COUNT_PER_ORDER) {
				orderItem.count = MAX_ITEM_COUNT_PER_ORDER;
			} else {
				orderItem.count += count;
			}
			orderItem.save();
			Logger.debug(">>> New item count %i", orderItem.count);
		}
		Logger.debug(">>> Created order item %s ", orderItem.id.toString());
	}

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

	//FIXME fix guess system by moving to new MyJob.now()
	private static City guessCity(String ip) {
		Boolean guessByIp = (Boolean) Cache.get(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED);
		if (guessByIp == null){
			String guessIpEnabled = PropertyVault.getSystemValueFor(SystemSetting.KEYS.GUESS_CITY_ENABLED);
			guessByIp = Boolean.valueOf(guessIpEnabled);
			Cache.set(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED, guessByIp, "8h");
		}
		if (!guessByIp){
			play.Logger.debug("Guessing disabled, returning default");
			return GeoDataHelper.getSystemDefaultCity();
		}
		play.Logger.debug("Guessing enabled, trying to guess city by ip");
		IpGeoData igdata = IpGeoData.find(IpGeoData.HQL.BY_IP, ip).first();
		if (igdata == null){
			igdata= await (GeoDataHelper.requestFromExternalServer(ip));
		}
		if (igdata == null || igdata.city == null){
			return GeoDataHelper.getSystemDefaultCity();
		}
		return igdata.city;
	}

	/**
	 * intended for local use so no 'public' modifier
	 * */
	private static Order createNewOpenOrder(final EndUser user) {
		Logger.debug(">>> Creating new order for user: %s", user);
		Order o = new Order();
		o.orderStatus = OrderStatus.OPEN;
		o.orderOwner = user;
		if (user == null) {
			o.anonSID = session.getId();
		}
		o.deleted = false;
		o.orderCreated = new Date();
		o.create();
		Logger.debug(">>> Created new order: %s", o.toString());
		return o;
	}

}