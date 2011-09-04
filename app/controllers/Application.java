package controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import controllers.Secure.Security;

import models.Client;
import models.MenuItem;
import models.Order;
import models.Order.OrderStatus;
import models.Order.PaymentStatus;
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
    // TODO Make more flexible
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;

    public static void loadFix() {
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
    }

    @Before
    public static void _prepare() {
        User user = getCurrentUser();
        renderArgs.put(USER_RENDER_KEY, user);
        Order order = null;
        if (user != null) {
            order = Order.find("orderStatus = ? and user =?",
                    Order.OrderStatus.OPEN, user).first();
        } else {
            order = Order.find("orderStatus = ? and anonSID = ?",
                    Order.OrderStatus.OPEN, session.getId())
                    .first();
        }

        if (order != null) {
            renderArgs.put("basketCount", order.items.size());
        } else {
            renderArgs.put("basketCount", 0);
        }

    }
    public static void deliveryAndPaymentMethod(){
        render("/Application/prepareOrder.html");
    }
    public static void index() {

        List<Client> clients = Client.findAll();
        

        render(clients);
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
    
    public static void checkAndSend(Integer id){
        Order o = Order.findById(id);
        o.orderStatus = OrderStatus.SENT;
        
        ok();
    }

    public static void addOrderItem(@Required Long id, @Required Integer count) {
        Logger.debug(">>> Adding items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.debug(">>> Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.debug(">>> No open order found, creating one..");
                order = createNewOpenOrder(user);
            }
            createOrAddOrderItem(id, order, count);
            Logger.debug(">>> Order item added, sending ok response");
            renderJSON("{}");
        } else  {
            String bid = session.getId();
            Logger.debug(">>> Annonymous sid: %s", bid);
            Order order = Order.find("orderStatus = ? and anonSID = ?",
                    OrderStatus.OPEN, bid).first();
            if (order == null){
                order = createNewOpenOrder(null);
            }
            createOrAddOrderItem(id, order, count);
        }
    }

    public static void delOrderItem(@Required Long id, @Required Integer count) {
        Logger.debug(">>> Delitinging items with id %s in count %s", id, count);
        if (Security.isConnected()) {
            Logger.debug(">>> Connected user login: %s", Security.connected());
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                Logger.debug(">>> no order found, sending ok response");
                ok();
                return;
            }
            deleteOrRemOrderItem(id, order, count);
            Logger.debug(">>> Order item deleted, sending ok response");
        } else  {
            String bid = session.getId();
            Logger.debug(">>> Annonymous basket id: %s", bid);
            Order order = Order.find("orderStatus = ? and anonSID = ?",
                    OrderStatus.OPEN, bid).first();
            if (order == null){
                order = createNewOpenOrder(null);
            }
            deleteOrRemOrderItem(id, order, count);
        }
        ok();

    }

    /**
     * @param id
     * @param order
     */
    private static void deleteOrRemOrderItem(Long id, Order order, Integer count) {
        Logger.debug(">>> deleting or decreasinf item %s for order #%s, count %s", id, order.id,count);
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
            Logger.debug(">>> Deleting id = %s, from order #%s", id, order.shortHandId());
            orderitem.deleted = true;
        } else {
            Logger.debug(">>> Decreased count for id = %s, to = %s, order #%s", id, remain, order.shortHandId());
            orderitem.count = remain;
        }
        orderitem.save();
    }

    private static void createOrAddOrderItem(Long menuItemId, Order order, Integer count) {

        count = normalizeCount(count);
        MenuItem menuItem = MenuItem.findById(menuItemId);
        if (menuItem == null) {
            Logger.warn("MenuItem not found:  %s", menuItemId.toString());
            return;
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
                    session.getId(),
                    OrderStatus.OPEN).first();
        }
        if (order == null){
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
        if (user == null){
            o.anonSID = session.getId();
        }
        o.deleted = false;
        o.orderCreated = new Date();
        o.create();
        Logger.debug(">>> Created new order: %s", o.toString());
        return o;
    }

}