package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Cache;

import models.Restaurant;
import models.MenuItem;
import models.RestaurantNetwork;
import models.Order;
import enumerations.OrderStatus;
import models.OrderItem;
import models.User;
import enumerations.UserRoles;
import enumerations.UserStatus;
import play.Logger;
import play.Play;
import play.cache.CacheFor;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.test.Fixtures;
import play.vfs.VirtualFile;
import controllers.Secure.Security;

public class Application extends Controller {

    public static final String USER_RENDER_KEY = "user";
    // TODO Make more flexible
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;
    public static void loadFix(){
        if (Play.mode.isDev()) {
            Logger.warn("Loading fixtures!");
            if (User.count() > 0) {
                Logger.warn("Database not empty, clearing db");
                Fixtures.deleteDatabase();
            }
            VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
            Play.javaPath.add(0, appRoot.child("test"));
            try {
                Fixtures.loadModels("dev_data.yml");
                Logger.warn("fixtures loaded");
            } catch (Exception e) {
                e.printStackTrace();
                Logger.warn("fixtures load failed: ", e);
            }
        }
    }
    @Before
    public static void _prepare() {
	User user = getCurrentUser();
	renderArgs.put(USER_RENDER_KEY, user);
	Order order = null;
	if (user != null) {
	    order = Order.find("orderStatus = ? and orderOwner =?",
		    OrderStatus.OPEN, user).first();
	} else {
	    order = Order.find("orderStatus = ? and anonSID = ?",
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
	User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
	if (user != null) {
	    order = Order.find("orderOwner = ? and orderStatus = ?", user,
		    OrderStatus.OPEN).first();
	} else {
	    order = Order.find("anonSID = ? and orderStatus = ?",
		    session.getId(), OrderStatus.OPEN).first();
	}
	if (order == null) {
	    order = createNewOpenOrder(null);
	}
	renderArgs.put("order", order);
	render("/Application/prepareOrder.html");
    }
    @CacheFor("2m")
    public static void index() {
	List<RestaurantNetwork> metas = RestaurantNetwork.findAll();
	List<Restaurant> combined = new ArrayList<Restaurant>();
	for (RestaurantNetwork meta: metas){
	    combined.addAll(meta.restoraunts); //removing restaurants that is in networks
	}
	List<Restaurant> clients = Restaurant.findAll();
	clients.removeAll(combined);
	render(clients);
    }

    private static User getCurrentUser() {
	User user = null;
	if (Security.isConnected()) {
	    user = User.find("login = ?", Security.connected()).first();
	}
	return user;
    }

    public static void showMenu(Long id) {
	Restaurant client = Restaurant.findById(id);
	Set<MenuItem> menuItems = client.menuBook;
	renderArgs.put("clientName", client.title);
	render(menuItems);
    }

    public static void newUser() {
	render();
    }

    public static void registerNewUser(User user) {
	Logger.debug(">>> Registering new user %s", user.toString());
	user.joinDate = new Date();
	user.lastLoginDate = new Date();
	user.userStatus = UserStatus.PENDING_APPROVEMENT;
	user.role = UserRoles.USER;
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
	    User user = (User) renderArgs.get(USER_RENDER_KEY);
	    Order order = Order.find("orderOwner = ?  and orderStatus = ?",
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
	    Order order = Order.find("orderStatus = ? and anonSID = ?",
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
	    User user = (User) renderArgs.get(USER_RENDER_KEY);
	    Order order = Order.find("orderOwner = ?  and orderStatus = ?",
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
	    Order order = Order.find("orderStatus = ? and anonSID = ?",
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
	OrderItem orderitem = OrderItem.find("order = ? and menuItem =? ",
		order, menuItem).first();
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
		order.client = null;
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
	if (order.client != null && !menuItem.client.equals(order.client)) {
	    // TODO error message about "cannot add form other client"
	    todo();
	    return;
	}
	if (order.client == null) {
	    order.client = menuItem.client;
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
	User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
	if (user != null) {
	    order = Order.find("orderOwner = ? and orderStatus = ?", user,
		    OrderStatus.OPEN).first();
	} else {
	    order = Order.find("anonSID = ? and orderStatus = ?",
		    session.getId(), OrderStatus.OPEN).first();
	}
	if (order == null) {
	    order = createNewOpenOrder(null);
	}
	renderArgs.put("order", order);
	render();

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
    private static Order createNewOpenOrder(final User user) {
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