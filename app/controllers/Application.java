package controllers;

import java.util.Date;
import java.util.List;

import models.Client;
import models.MenuItem;
import models.Order;
import models.OrderItem;
import models.User;
import models.User.UserStatus;
import play.mvc.Controller;
import play.mvc.Router;
import siena.Model;

public class Application extends Controller {

    public static void index() {

	List<Client> clients = Model.all(Client.class).fetch();
	// List<WorkHours> whorkHours = Model.all(WorkHours.class)
	// .filter("client", clients).fetch();
	// List<Day> days = Model.all(Day.class).filter("workDay", whorkHours)
	// .fetch();
	//TODO Get Work hours
	User user = getCurrentUser();
	render(clients, /* whorkHours, days, */user);
    }

    private static User getCurrentUser() {
	User user = null;
	if (Security.isConnected()) {
	    List<User> users = User.all(User.class)
		    .filter("login", Security.connected()).fetch();
	    if (users.size() != 0) {
		user = users.get(0);
	    }
//		forbidden();
	    
	}
	return user;
    }

    public static void showMenu(Long id) {
	Client client = Model.getByKey(Client.class, id);
	List<MenuItem> menuItems = Model.all(MenuItem.class)
		.filter("client", client).fetch();
	User user = getCurrentUser();
	render(menuItems, user);
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
	redirect(Router.getFullUrl("Application.index"));
    }
    
    public static void addOrderItem(Long id, Integer count){
	
	if(Security.isConnected()){
	    String userLogin = Security.connected();
	    User user = Model.all(User.class).filter("login", userLogin).get();
	    MenuItem menuItem = Model.getByKey(MenuItem.class, id);
	    Order order = Model.all(Order.class).filter("orderOwner", user).filter("orderStatus", Order.OrderStatus.OPEN).get();
	    if (order == null){
		order = new Order(); //TODO [Mike] Add constructor for this case
		order.client = menuItem.client;
		order.orderOwner = user;
		
	    }
	    OrderItem orderitem = new OrderItem(menuItem,order, user) ;
	    orderitem.insert();
	    
	}
	ok();
    }
    
    public static void delOrderItem(Long id){
	if(Security.isConnected()){
	    String userLogin = Security.connected();
	    User user = Model.all(User.class).filter("login", userLogin).get();
	    MenuItem menuItem = Model.getByKey(MenuItem.class, id);
	    Order order = Model.all(Order.class).filter("orderOwner", user).filter("orderStatus", Order.OrderStatus.OPEN).get();
	    if (order == null){
		ok();
		return;
	    }
	    //FIXME [Mike] what if there will be more than one OrderItem ? add check to addOrderItem ?
	    OrderItem orderitem = Model.all(OrderItem.class).filter("orderId", order).filter("deleted", false).filter("menuItemId", menuItem).get();
	    if (orderitem == null){
		ok();
		return;
	    }
	    orderitem.deleted = true;
	    orderitem.update();
//	    OrderItem orderitem = new OrderItem(menuItem,order, user) ;
//	    orderitem.insert();
	    
	}
	ok();
    }

}