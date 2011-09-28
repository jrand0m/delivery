package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jobs.DevBootStrap;
import models.MenuItem;
import models.MenuItemGroup;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.RestaurantNetwork;
import models.users.EndUser;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.data.validation.Required;
import play.i18n.Lang;
import play.mvc.Before;
import play.mvc.Controller;
import play.test.Fixtures;
import enumerations.OrderStatus;
import enumerations.UserRoles;
import enumerations.UserStatus;

public class Application extends Controller {

    public static final String USER_RENDER_KEY = "user";
    // TODO Make more flexible(extract to SystemSetting)
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;

    public static void loadFix() {
	Fixtures.deleteDatabase();
	new DevBootStrap().doJob();
	renderText("Cleared db and forsed fixture load");
    }

    @Before
    public static void _prepare() {
	EndUser user = getCurrentUser();
	renderArgs.put(USER_RENDER_KEY, user);
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

    }

    public static void deliveryAndPaymentMethod() {
	Order order = null;
	EndUser user = (EndUser) renderArgs.get(Application.USER_RENDER_KEY);
	if (user != null) {
	    order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS, user,
		    OrderStatus.OPEN).first();
	} else {
	    order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
		    OrderStatus.OPEN, session.getId()).first();
	}
	if (order == null) {
	    order = createNewOpenOrder(null);
	}
	renderArgs.put("order", order);
	render("/Application/prepareOrder.html");
    }

    public static void index() {
	List<Restaurant> restaurants = (List<Restaurant>) Cache
		.get("restaurants");
	if (restaurants == null) {
	    List<RestaurantNetwork> metas = RestaurantNetwork.findAll();

	    List<Restaurant> combined = new ArrayList<Restaurant>();
	    for (RestaurantNetwork meta : metas) {
		combined.addAll(meta.restoraunts); // removing restaurants that
						   // is in networks
	    }
	    restaurants = Restaurant.findAll();
	    restaurants.removeAll(combined);
	    Cache.set("restaurants", restaurants, "3min");
	}
	render(restaurants);
    }

    private static EndUser getCurrentUser() {
	EndUser user = null;
	if (Security.isConnected()) {
	    user = EndUser.find(EndUser.HQL.BY_LOGIN, Security.connected()).first();
	}
	return user;
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

    public static void addOrderItem(@Required Long id, @Required Integer count) {
	Logger.debug(">>> Adding items with id %s in count %s", id, count);
	if (Security.isConnected()) {
	    Logger.debug(">>> Connected user login: %s", Security.connected());
	    EndUser user = (EndUser) renderArgs.get(USER_RENDER_KEY);
	    Order order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS,
		    user, OrderStatus.OPEN).first();
	    if (order == null) {
		Logger.debug(">>> No open order found, creating one..");
		order = createNewOpenOrder(user);
	    }
	    createOrAddOrderItem(id, order, count);
	    Logger.debug(">>> Order item added, sending ok response");

	} else {
	    String bid = session.getId();
	    Logger.debug(">>> Annonymous sid: %s", bid);
	    Order order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
		    OrderStatus.OPEN, bid).first();
	    if (order == null) {
		order = createNewOpenOrder(null);
	    }
	    createOrAddOrderItem(id, order, count);
	}
	renderJSON("{}");
    }

    public static void delOrderItem(@Required Long id, @Required Integer count) {
	Logger.debug(">>> Delitinging items with id %s in count %s", id, count);
	if (Security.isConnected()) {
	    Logger.debug(">>> Connected user login: %s", Security.connected());
	    EndUser user = (EndUser) renderArgs.get(USER_RENDER_KEY);
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
		order = createNewOpenOrder(null);
	    }
	    deleteOrRemOrderItem(id, order, count);
	}
	renderJSON("{}");

    }

    /**
     * @param id
     * @param order
     */
    private static void deleteOrRemOrderItem(Long id, Order order, Integer count) {
	Logger.debug(
		">>> deleting or decreasinf item %s for order #%s, count %s",
		id, order.id, count);
	Logger.debug(">>> session id: %s", session.getId());
	count = normalizeCount(count);
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

	count = normalizeCount(count);
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
	    orderItem.orderItemUserPrice = menuItem.price;
	    orderItem.orderItemPrice = menuItem.price;
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

    public static void basket() {
	Logger.debug(">>> Entering basket");
	Order order = null;
	EndUser user = (EndUser) renderArgs.get(Application.USER_RENDER_KEY);
	if (user != null) {
	    order = Order.find(Order.HQL.BY_ORDER_OWNER_AND_ORDER_STATUS, user,
		    OrderStatus.OPEN).first();
	} else {
	    order = Order.find(Order.HQL.BY_ORDER_STATUS_AND_ANON_SID,
		    OrderStatus.OPEN, session.getId()).first();
	}
	if (order == null) {
	    order = createNewOpenOrder(null);
	}
	renderArgs.put("order", order);
	render();

    }

    /**
     * Change Language
     * */
    public static void changeLang(String lang) {
	Lang.change(lang);
	index();
    }
    
    public static void serveLogo(long id) throws IOException {
	   final Restaurant restaurant = Restaurant.findById(id);
	   notFoundIfNull(restaurant);
	   InputStream is;
	   if (restaurant.logo.exists()){
	       response.setContentTypeIfNotSet(restaurant.logo.type());
	       is = restaurant.logo.get();
	   } else {
	       is = new FileInputStream(Play.applicationPath+"/public/images/no_image.jpg");
	   }
	   renderBinary(is);
    }


    /**
     * @param count
     * @return value within range [1;64]
     */
    private static Integer normalizeCount(Integer count) {
	count = Math.abs(count);
	if (count > MAX_ITEM_COUNT_PER_ORDER) {
	    return MAX_ITEM_COUNT_PER_ORDER;
	} else if (count == 0) {
	    return 1;
	}
	return count;
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