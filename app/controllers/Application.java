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
import models.User.UserStatus;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.vfs.VirtualFile;
import play.test.Fixtures;

public class Application extends Controller {

    public static final String USER_RENDER_KEY = "user";
    public static final String ANONYMOUS_BASKET_ID = "BID";

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
        user.joinDate = new Date();
        user.lastLoginDate = new Date();
        user.userStatus = UserStatus.PENDING_APPROVEMENT;
        user.create();
        if (session.contains(ANONYMOUS_BASKET_ID) && session.get(ANONYMOUS_BASKET_ID) != null){
//            List<Orders>
        }
        try {
            Secure.authenticate(user.login, user.password, false);
        } catch (Throwable e) {
            e.printStackTrace();
            error();
        }
        index();
    }

    public static void addOrderItem(Long id, Integer count) {

        if (Security.isConnected()) {
            String userLogin = Security.connected();
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            MenuItem menuItem = MenuItem.findById(id);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {
                order = createNewOpenOrder(user);// TODO [Mike] Add constructor
                // for this
                // case
                // order.client = menuItem.client;
                order.orderOwner = user;

            }
            OrderItem orderitem = new OrderItem(menuItem, order, user);
            orderitem.count = count;
            orderitem.orderItemUserPrice = menuItem.price;
            orderitem.orderItemPrice = menuItem.price;
            orderitem.create();

        } else if (session.contains("chartId")) {

        }

    }

    public static void delOrderItem(Long id) {
        if (Security.isConnected()) {
            User user = (User) renderArgs.get(USER_RENDER_KEY);
            MenuItem menuItem = MenuItem.findById(id);
            Order order = Order.find("orderOwner = ?  and orderStatus = ?",
                    user, Order.OrderStatus.OPEN).first();
            if (order == null) {

                ok();
                return;
            }
            // FIXME [Mike] what if there will be more than one OrderItem ? add
            // check to addOrderItem ?
            OrderItem orderitem = OrderItem.find(
                    "orderId = ? and menuItemId =? ", order, menuItem).first();
            if (orderitem == null) {
                ok();
                return;
            }
            orderitem.deleted = true;
            orderitem.save();

        }
        ok();

    }

    /**
     * intended for local use so no 'public' modifier
     * */
    static Order createNewOpenOrder(User user) {
        Order o = new Order();
        o.orderStatus = OrderStatus.OPEN;
        o.orderOwner = user;
        o.deleted = false;
        o.create();
        return o;
    }

}