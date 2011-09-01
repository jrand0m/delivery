package controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import controllers.Secure.Security;

import models.Client;
import models.MenuItem;
import models.Order;
import models.Order.OrderStatus;
import models.OrderItem;
import models.User;
import models.User.UserRoles;
import models.User.UserStatus;
import play.Logger;
import play.Play;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.Before;
import play.mvc.Controller;
import play.vfs.VirtualFile;
import play.test.Fixtures;

public class Application extends Controller {

    public static final String  USER_RENDER_KEY          = "user";
    public static final String  ANONYMOUS_BASKET_ID      = "BID";
    // TODO Make more flexible
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;

    public static void loadFix() {
        // if (Play.mode.isDev()) {
        if (User.count() > 0) {
            Fixtures.deleteDatabase();
        }
        VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
        Play.javaPath.add(0, appRoot.child("test"));
        try {

            Fixtures.loadModels("dev_data.yml");
            renderText("fixtures loaded");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            renderText("fixtures load failed: " + e.getMessage());
        }
        return;

        // } else
        // ok();

    }

    @Before
    public static void _prepare() {
        User user = getCurrentUser();
        renderArgs.put(USER_RENDER_KEY, user);
        Order order = null;
        if (user != null) {
            order = Order.find("orderStatus = ? and user =?",
                    Order.OrderStatus.OPEN, user).first();
        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            order = Order.find("orderStatus = ? and anonBasketId = ?",
                    Order.OrderStatus.OPEN, session.get(ANONYMOUS_BASKET_ID))
                    .first();
        }

        if (order != null) {
            renderArgs.put("basketCount", order.items.size());
        } else {
            renderArgs.put("basketCount", 0);
        }

    }

    public static void index() {

        List<Client> clients = Client.findAll();
        // List<WorkHours> whorkHours = Model.all(WorkHours.class)
        // .filter("client", clients).fetch();
        // List<Day> days = Model.all(Day.class).filter("workDay", whorkHours)
        // .fetch();
        // TODO Get Work hours

        render(clients /* whorkHours, days, */);
    }

    private static User getCurrentUser() {
        User user = null;
        if (Security.isConnected()) {
            List<User> users = User.find("login = ?", Security.connected())
                    .first();
            if (users.size() != 0) {
                user = users.get(0);
            }
        } 
        return user;
    }

    public static void showMenu(Long id) {
        Client client = Client.findById(id);
        Set<MenuItem> menuItems = client.menuBook;
        renderArgs.put("clientName", client.title);
        render(menuItems);
    }

    public static void newUser() {
        render();
    }

    public static void registerNewUser(User user) {
        Logger.trace("Registering new user %s", user.toString());
        user.joinDate = new Date();
        user.lastLoginDate = new Date();
        user.userStatus = UserStatus.PENDING_APPROVEMENT;
        user.create();
        if (session.contains(ANONYMOUS_BASKET_ID)
                && session.get(ANONYMOUS_BASKET_ID) != null) {
            Logger.trace("Annonymous basket found , converting order history.");
        }
        try {
            Secure.authenticate(user.login, user.password, false);
        } catch (Throwable e) {
            Logger.error(e, "Failed to login in App.registerNewUser()");
            error();
        }

        index();
    }

    public static void addOrderItem(@Required Long id, @Required Integer count) {
        Logger.trace("Adding items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.trace("Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.trace("No open order found, creating one..");
                order = createNewOpenOrder(user);
            }
            createOrAddOrderItem(id, order, count);
            Logger.trace("Order item added, sending ok response");
            renderJSON("{}");
        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            String bid = session.get(ANONYMOUS_BASKET_ID);
            Logger.trace("Annonymous basket id: %s", bid);
            Order order = Order.find("orderStatus = ? and anonBasketId = ?",
                    OrderStatus.OPEN, bid).first();

            createOrAddOrderItem(id, order, count);
        } else {
            Logger.trace("No basket found/user connected!");
            String bid = Codec.UUID();
            session.put(ANONYMOUS_BASKET_ID, bid);
            Order order = createNewOpenOrder(null);
            order.anonBasketId = bid;
            order.save();
            createOrAddOrderItem(id, order, count);
        }

    }

    public static void delOrderItem(@Required Long id, @Required Integer count) {
        Logger.trace("Delitinging items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.trace("Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.trace("no order found, sending ok response");
                ok();
                return;
            }
            deleteOrRemOrderItem(id, order, count);
            Logger.trace("Order item deleted, sending ok response");
        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            String bid = session.get(ANONYMOUS_BASKET_ID);
            Logger.trace("Annonymous basket id: %s", bid);
            Order order = Order.find("orderStatus = ? and anonBasketId = ?",
                    OrderStatus.OPEN, bid).first();
            deleteOrRemOrderItem(id, order, count);
        } else {
            Logger.trace("No basket found/user connected!");
            String bid = Codec.UUID();
            session.put(ANONYMOUS_BASKET_ID, bid);
            Order order = createNewOpenOrder(null);
            order.anonBasketId = bid;
            order.save();
        }
        ok();

    }

    /**
     * @param id
     * @param order
     */
    private static void deleteOrRemOrderItem(Long id, Order order, Integer count) {
        Logger.trace("deleting or decreasinf item %s for order #%s, count %s", id, order.shortHandId(),count);
        count = normalizeCount(count);
        MenuItem menuItem = MenuItem.findById(id);
        OrderItem orderitem = OrderItem.find("order = ? and menuItem =? ",
                order, menuItem).first();
        if (orderitem == null) {
            Logger.trace("no such item found, sending ok response");
            return;
        }
        int remain = orderitem.count - count;
        if (remain < 1) {
            Logger.trace("Deleting id = %s, from order #%s", id, order.shortHandId());
            orderitem.deleted = true;
        } else {
            Logger.trace("Decreased count for id = %s, to = %s, order #%s", id, remain, order.shortHandId());
            orderitem.count = remain;
        }
        orderitem.save();
    }

    static void createOrAddOrderItem(Long menuItemId, Order order, Integer count) {

        count = normalizeCount(count);
        MenuItem menuItem = MenuItem.findById(menuItemId);
        if (menuItem == null) {
            Logger.warn("MenuItem not found:  %s", menuItemId.toString());
            return;
        }
        OrderItem orderItem = null;
        List<OrderItem> list = order.items;
        for (OrderItem i : list) {
            Logger.trace("Checking %s vs %s (%s)", menuItem, i.menuItem,
                    menuItem == i.menuItem);
            if (menuItem == i.menuItem) {
                orderItem = i;
                break;
            }
        }
        if (orderItem == null) {
            Logger.trace("Item not found, creating new");
            orderItem = new OrderItem(menuItem, order);
            orderItem.count = count;
            orderItem.orderItemUserPrice = menuItem.price;
            orderItem.orderItemPrice = menuItem.price;
            orderItem.create();
        } else {
            Logger.trace("Item found, adding count");
            if (orderItem.count + count > MAX_ITEM_COUNT_PER_ORDER) {
                orderItem.count = MAX_ITEM_COUNT_PER_ORDER;
            } else {
                orderItem.count += count;
            }
            orderItem.save();
            Logger.trace("New item count %i", orderItem.count);
        }
        Logger.trace("Created order item %s ", orderItem.id.toString());
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
    static Order createNewOpenOrder(User user) {
        Logger.trace("Creating new order for user: %s", user);
        Order o = new Order();
        o.orderStatus = OrderStatus.OPEN;
        o.orderOwner = user;
        o.deleted = false;
        o.create();
        Logger.trace("Created new order: %s", o.toString());
        return o;
    }

}