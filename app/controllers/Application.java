package controllers;

import java.util.Date;
import java.util.List;

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
import siena.Model;

public class Application extends Controller {

    public static void loadFix() {
	if (Play.mode.isDev() && Model.all(User.class).fetch().size() == 0) {

	    VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
	    Play.javaPath.add(0, appRoot.child("test"));
	    try {
		// SienaFixutre.deleteAll();
		SienaFixutre.load("dev_data.yml");
		renderText("fixtures loaded");
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		renderText("fixtures load failed: " + e.getMessage());
	    }
	    return;
	} else if (Play.mode.isDev()) {
	    renderText("fixtures cund be loaded: clean database first!");
	} else
	    ok();

    }

    @Before
    public static void _prepare() {
	User user = getCurrentUser();
	renderArgs.put("user", user);
	if (user != null) {
	    Order order = Order.all(Order.class)
		    .filter("deleted", Boolean.FALSE)
		    .filter("orderStatus", Order.OrderStatus.OPEN).get();
	    if (order != null) {

		renderArgs.put("basketCount", order.getCount());
	    }

	}

    }

    public static void index() {

	List<Client> clients = Model.all(Client.class).fetch();
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
	    List<User> users = User.all(User.class)
		    .filter("login", Security.connected()).fetch();
	    if (users.size() != 0) {
		user = users.get(0);
	    }
	    // forbidden();

	}
	return user;
    }

    public static void showMenu(Long id) {
	Client client = Model.getByKey(Client.class, id);
	List<MenuItem> menuItems = Model.all(MenuItem.class)
		.filter("client", client).fetch();
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
	user.insert();
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
	    User user = Model.all(User.class).filter("login", userLogin).get();
	    MenuItem menuItem = Model.getByKey(MenuItem.class, id);
	    Order order = Model.all(Order.class).filter("orderOwner", user)
		    .filter("orderStatus", Order.OrderStatus.OPEN).get();
	    if (order == null) {
		order = createNewOpenOrder(user);// TODO [Mike] Add constructor
		// for this
		// case
		//order.client = menuItem.client;
		order.orderOwner = user;

	    }
	    OrderItem orderitem = new OrderItem(menuItem, order, user);
	    orderitem.count = count;
	    orderitem.orderItemUserPrice = menuItem.price;
	    orderitem.orderItemPrice = menuItem.price;
	    orderitem.insert();

	}else if (session.contains("chartId")) {
	    
	}
	
	
    }

    public static void delOrderItem(Long id) {
	if (Security.isConnected()) {
	    String userLogin = Security.connected();
	    User user = Model.all(User.class).filter("login", userLogin).get();
	    MenuItem menuItem = Model.getByKey(MenuItem.class, id);
	    Order order = Model.all(Order.class).filter("orderOwner", user)
		    .filter("orderStatus", Order.OrderStatus.OPEN).get();
	    if (order == null) {

		ok();
		return;
	    }
	    // FIXME [Mike] what if there will be more than one OrderItem ? add
	    // check to addOrderItem ?
	    OrderItem orderitem = Model.all(OrderItem.class)
		    .filter("orderId", order).filter("deleted", false)
		    .filter("menuItemId", menuItem).get();
	    if (orderitem == null) {
		ok();
		return;
	    }
	    orderitem.deleted = true;
	    orderitem.update();
	    // OrderItem orderitem = new OrderItem(menuItem,order, user) ;
	    // orderitem.insert();

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
	o.save();
	return o;
    }

}