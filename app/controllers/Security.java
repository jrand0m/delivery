package controllers;

import java.util.List;

import models.User;
import siena.Model;

/**
 * Overriden security model
 * 
 * @see http://www.playframework.org/documentation/1.2.2/guide8
 * @see http://www.playframework.org/community/snippets/2
 * @author mike
 * */

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
	List<User> users = Model.all(User.class).filter("login", username)
		.fetch();
	if (!users.isEmpty() && users.get(0).password.equals(password)) {
	    return true;
	}
	return false;
    }

    public static boolean check(String role) {
	if (isConnected()) {
	    List<User> users = Model.all(User.class)
		    .filter("login", connected()).fetch();
	    if (users.get(0).role.toString().equals(role)) {
		return true;
	    }
	}
	return false;
    }

}
