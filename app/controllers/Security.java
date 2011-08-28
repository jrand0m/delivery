package controllers;

import java.util.List;

import models.User;

/**
 * Overriden security model
 * 
 * @see http://www.playframework.org/documentation/1.2.2/guide8
 * @see http://www.playframework.org/community/snippets/2
 * @author mike
 * */

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {
        User user = User.find("login = ?", username).first();
        if (user != null && user.password.equals(password)) {
            return true;
        }
        return false;
    }

    public static boolean check(String role) {
        if (isConnected()) {
            User user = User.find("login = ?", connected()).first();
            if (user.role.toString().equals(role)) {
                return true;
            }
        }
        return false;
    }

}
