package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Address;
import models.Order;
import models.OrderItem;
import models.User;
import models.Order.OrderStatus;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;
import siena.Model;

/**
 * User Personal Page Controller
 * 
 * @author mike
 * */
@With(Secure.class)
public class Locker extends Controller {

    /**
     * Shows user's personal page
     */
    public static void index() {
	if (!Security.isConnected()) {
	    try {
		flash.put("url", Router.getFullUrl("Locker.index"));
		Secure.login();
	    } catch (Throwable e) {
		// TODO checkout how to redirect back here !
		forbidden();
		e.printStackTrace();
	    }
	}
	String userName = Security.connected();
	List<User> users = Model.all(User.class).filter("login", userName)
		.fetch();
	if (users.size() == 0) {
	    forbidden();
	}
	User user = users.get(0);
	List<Address> addressList = Model.all(Address.class)
		.filter("deleted", false).filter("userId", user).fetch();
	List<Order> orderList = Model.all(Order.class)
		.filter("orderOwner", user).fetch();
	render(user, addressList, orderList);
    }

    public static void basket() {
	if (!Security.isConnected()) {
	    try {
		flash.put("url", Router.getFullUrl("Locker.basket"));
		Secure.login();
	    } catch (Throwable e) {
		// TODO checkout how to redirect back here !
		forbidden();
		e.printStackTrace();
	    }
	}
	String userName = Security.connected();
	List<User> users = Model.all(User.class).filter("login", userName)
		.fetch();
	if (users.size() == 0) {
	    forbidden();
	}
	User user = users.get(0);
	Order order = Model.all(Order.class).filter("orderOwner", user)
		.filter("orderStatus", OrderStatus.OPEN).get();
	List<OrderItem> items = new ArrayList<OrderItem>();
	if (order != null) {
	    items = order.getItems();
	} else {
	    order = Application.createNewOpenOrder(user);

	}
	renderArgs.put("order", order);
	renderArgs.put("orderItems", items);
	render(user);

    }

    public static void addAddress(Address address) {
	if (address == null) {
	    redirect(Router.getFullUrl("Locker.index"));
	}
	if (!Security.isConnected()) {
	    try {
		flash.put("url", Router.getFullUrl("Locker.index"));
		Secure.login();
	    } catch (Throwable e) {
		forbidden();
		e.printStackTrace();
	    }
	}
	String userName = Security.connected();
	List<User> user = Model.all(User.class).filter("login", userName)
		.fetch(1);
	if (user.size() != 1) {
	    forbidden();
	}
	address.userId = user.get(0);
	address.insert();
	// TODO in future do it asynchronously!
	index();
    }

    public static void editAddress(Address address) {

	String userName = Security.connected();
	if (userName == null) {
	    try {
		flash.put("url", Router.getFullUrl("Locker.index"));
		Secure.login();
	    } catch (Throwable e) {
		forbidden();
		e.printStackTrace();
	    }
	}
	if (address.id == null) {
	    error("Data inconsistency detected");
	}
	List<User> user = Model.all(User.class).filter("login", userName)
		.fetch(1);

	if (user.size() != 1) {
	    forbidden();
	}
	address.userId = user.get(0);
	Address base = Model.all(Address.class).getByKey(address.id);
	if (!address.equals(base)) {
	    // TODO Make logging
	    // LogItem.log(Address.class.getName(), field, newValue, oldValue,
	    // address.id, modifiedBy, modifiedOn)
	    address.update();
	}

	// TODO in future do it asynchronously!
	index();
    }

    public static void deleteAddress(Long id) {

	String userName = Security.connected();
	if (userName == null) {
	    try {
		flash.put("url", Router.getFullUrl("Locker.index"));
		Secure.login();
	    } catch (Throwable e) {
		forbidden();
		e.printStackTrace();
	    }
	}
	if (id != null) {
	    // TODO add logging
	    Address address = Model.getByKey(Address.class, id);
	    address.deleted = true;
	    address.save();
	}
	index();
    }

}
