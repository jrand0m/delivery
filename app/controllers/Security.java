/**
 * 
 */
package controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import enumerations.UserRoles;

import models.Order;
import models.User;
import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.utils.Java;

/**
 * @author Mike
 * 
 */
public class Security extends Controller {

    /**
     * @Deprecated
     * 
     * @param username
     * @param password
     * @return
     */
    static boolean authentify(String username, String password) {
	throw new UnsupportedOperationException();
    }

    /**
     * This method is called during the authentication process. This is where
     * you check if the user is allowed to log in into the system. This is the
     * actual authentication process against a third party system (most of the
     * time a DB).
     * 
     * @param username
     * @param password
     * @return true if the authentication process succeeded
     */
    static boolean authenticate(String username, String password) {
	Logger.debug("Trying to login as %s", username);
	User user = User.find(User.HQL.BY_LOGIN_OR_EMAIL, username, username)
		.first();
	if (user != null && user.password.equals(password)) {
	    Logger.debug("Login succesful for %s[%s]", user.login,
		    user.role.toString());
	    String bid = session.getId();
	    List<Order> orders = Order.find(Order.HQL.BY_ANONSID, bid).fetch();
	    Logger.debug(
		    "Found %s anonymous basket(s) bound to unlogined user %s",
		    orders.size());
	    for (Order order : orders) {
		// TODO check case when user has logined on foregin pc (
		// move this conversion to be on-demand only. Ask user
		// first! )
		if (order.orderOwner != null) {
		    Logger.warn(
			    "Order #%s already is assigned to user, but session uuid is same!",
			    order.getShortHandId());
		    continue;
		}
		order.anonSID = null;
		order.orderOwner = User.find(User.HQL.BY_LOGIN, username)
			.first();
		order.save();

	    }
	    Logger.debug("Anonymous basket conversion complete!");

	    return true;
	}
	Logger.debug("Login failed.");
	return false;
    }

    /**
     * This method checks that a profile is allowed to view this page/method.
     * This method is called prior to the method's controller annotated with the @Check
     * method.
     * 
     * @param profile
     * @return true if you are allowed to execute this controller method.
     */
    public static boolean check(UserRoles role) {
	if (isConnected()) {
	    User user = User.find(User.HQL.BY_LOGIN, connected()).first();
	    if (user.role == role) {
		return true;
	    }
	}
	return false;
    }

    /**
     * This method returns the current connected username
     * 
     * @return
     */
    static String connected() {
	return session.get("username");
    }

    /**
     * Indicate if a user is currently connected
     * 
     * @return true if the user is connected
     */
    static boolean isConnected() {
	return session.contains("username");
    }

    /**
     * This method is called after a successful authentication. You need to
     * override this method if you with to perform specific actions (eg. Record
     * the time the user signed in)
     */
    static void onAuthenticated() {
    }

    /**
     * This method is called before a user tries to sign off. You need to
     * override this method if you wish to perform specific actions (eg. Record
     * the name of the user who signed off)
     */
    static void onDisconnect() {
    }

    /**
     * This method is called after a successful sign off. You need to override
     * this method if you wish to perform specific actions (eg. Record the time
     * the user signed off)
     */
    static void onDisconnected() {
    }

    /**
     * This method is called if a check does not succeed. By default it shows
     * the not allowed page (the controller forbidden method).
     * 
     * @param profile
     */
    static void onCheckFailed(String profile) {
	forbidden();
    }

    static Object invoke(String m, Object... args) throws Throwable {
	Class security = null;
	List<Class> classes = Play.classloader
		.getAssignableClasses(Security.class);
	if (classes.size() == 0) {
	    security = Security.class;
	} else {
	    security = classes.get(0);
	}
	try {
	    return Java.invokeStaticOrParent(security, m, args);
	} catch (InvocationTargetException e) {
	    throw e.getTargetException();
	}
    }

}