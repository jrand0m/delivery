package controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
import play.mvc.Before;
import play.mvc.Controller;
import play.vfs.VirtualFile;
import play.test.Fixtures;

public class Application extends Controller {

    public static final String USER_RENDER_KEY     = "user";
    public static final String ANONYMOUS_BASKET_ID = "BID";
    //TODO Make more flexible
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
        if (user != null) {
            Order order = Order.find("orderStatus = ?", Order.OrderStatus.OPEN)
                    .first();
            if (order != null) {
                renderArgs.put("basketCount", order.items.size());
            } else {
                renderArgs.put("basketCount", 0);
            }

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
            // forbidden();

        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            
        }
        return user;
    }

    public static void showMenu(String id) {
        Client client = Client.findById(id);
        Set<MenuItem> menuItems = client.menuBook;
        renderArgs.put("clientName", client.title);
        render(menuItems);
    }

    public static void newUser() {
        render();
    }

    public static void registerNewUser(User user) {
        Logger.debug("Registering new user %s", user.toString());
        user.joinDate = new Date();
        user.lastLoginDate = new Date();
        user.userStatus = UserStatus.PENDING_APPROVEMENT;
        user.create();
        if (session.contains(ANONYMOUS_BASKET_ID)
                && session.get(ANONYMOUS_BASKET_ID) != null) {
            Logger.debug("Annonymous basket found , converting order history.");
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
        Logger.debug("Adding items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.debug("Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.debug("No open order found, creating one..");
                order = createNewOpenOrder(user);
            }
            createOrAddOrderItem(id, order, user, count);
            Logger.debug("Order item added, sending ok response");
            renderJSON("{}");
        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            String bid = session.get(ANONYMOUS_BASKET_ID);
            Logger.debug("Annonymous basket id: %s", bid);
            User user = User.findById(bid);
            if (user == null || user.userStatus != UserStatus.ANONNYMOUS) {
                Logger.warn("Bad basket id: %s", bid);
                session.remove(ANONYMOUS_BASKET_ID);
                ok();
                return;
            }
            Order order = Order.find("orderStatus = ? and orderOwner = ?",
                    OrderStatus.OPEN, user).first();
            createOrAddOrderItem(id, order, user, count);
        } else {
            Logger.debug("No basket found/user connected!");
            User anon = createAnonymous();
            session.put(ANONYMOUS_BASKET_ID, anon.id);
            Order order = createNewOpenOrder(anon);
            createOrAddOrderItem(id, order, anon, count);
        }

    }

    public static void delOrderItem(@Required Long id, @Required Integer count) {
        Logger.debug("Delitinging items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.debug("Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            MenuItem menuItem = MenuItem.findById(id);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.debug("no order found, sending ok response");
                ok();
                return;
            }
            OrderItem orderitem = OrderItem.find(
                    "orderId = ? and menuItemId =? ", order, menuItem).first();
            if (orderitem == null) {
                Logger.debug("no such item found, sending ok response");
                ok();
                return;
            }
            orderitem.deleted = true;
            orderitem.save();
            Logger.debug("Order item deleted, sending ok response");
        } else if (session.contains(ANONYMOUS_BASKET_ID)) {
            Logger.debug("Annonymous basket id: %s",
                    session.get(ANONYMOUS_BASKET_ID));

        } else {
            Logger.debug("No basket found/user connected!");
        }
        ok();

    }

    static void createOrAddOrderItem(Long menuItemId,  Order order,  User user,
            Integer count) {
        
        count=normalizeCount(count);
        MenuItem menuItem = MenuItem.findById(menuItemId);
        if (menuItem == null) {
            Logger.warn("MenuItem not found:  %s", menuItemId.toString());
            return;
        }
        OrderItem orderItem = null;
        List<OrderItem> list = order.items;
        for (OrderItem i : list) {
            Logger.debug("Checking %s vs %s (%s)", menuItem, i.menuItem, menuItem == i.menuItem);
            if (menuItem == i.menuItem) {
                orderItem = i;
                break;
            }
        }
        if (orderItem == null) {
            Logger.debug("Item not found, creating new");
            orderItem = new OrderItem(menuItem, order, user);
            orderItem.count = count;
            orderItem.orderItemUserPrice = menuItem.price;
            orderItem.orderItemPrice = menuItem.price;
            orderItem.create();
        } else {
            Logger.debug("Item found, adding count");
            if (orderItem.count+count>MAX_ITEM_COUNT_PER_ORDER){
                orderItem.count = MAX_ITEM_COUNT_PER_ORDER;
            } else {
                orderItem.count+=count;
            }
            orderItem.save();
        }
        Logger.debug("Created order item %s ", orderItem.id.toString());
    }

    /**
     * @param count
     * @return value within range [1;64]
     */
    private static Integer normalizeCount(Integer count) {
        count = Math.abs(count);
        if (count>MAX_ITEM_COUNT_PER_ORDER){
            return MAX_ITEM_COUNT_PER_ORDER;
        } else if (count == 0){
            return 1;
        }
        return count;
    }

    static User createAnonymous() {
        User user = new User();
        user.role = UserRoles.USER;
        user.userStatus = UserStatus.ANONNYMOUS;
        user.create();
        Logger.debug("Created anon with id: %s", user.id);
        return user;
    }

    /**
     * intended for local use so no 'public' modifier
     * */
    static Order createNewOpenOrder(User user) {
        Logger.debug("Creating new order for user: %s", user.id);
        Order o = new Order();
        o.orderStatus = OrderStatus.OPEN;
        o.orderOwner = user;
        o.deleted = false;
        o.create();
        Logger.debug("Created new order: %s", o.toString());
        return o;
    }

}