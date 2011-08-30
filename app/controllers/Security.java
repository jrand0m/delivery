package controllers;

import java.util.List;

import play.Logger;

import models.Order;
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
        Logger.debug("Trying to login as %s",username);
        User user = User.find("login = ? or email = ?", username, username).first();
        if (user != null && user.password.equals(password)) {
            Logger.debug("Login succesful for %s[%s]", user.login, user.role.toString());
            if (session.contains(Application.ANONYMOUS_BASKET_ID)){
                String bid = session.get(Application.ANONYMOUS_BASKET_ID);
                Logger.debug("Detected BID: %s;", bid);
                session.remove(Application.ANONYMOUS_BASKET_ID);
                List<Order> orders = Order.find("anonBasketId = ? ", bid ).fetch();
                Logger.debug("Found %s anonymous basket(s)", orders.size());
                for (Order order :orders){
                    //TODO check case when user has logined on foregin pc ( move this conversion to be on-demand only. Ask user first! )
                    if (order.orderOwner!=null){
                        Logger.warn("Order #%s already is assigned to user, but session uuid is same!", order.shortHandId());
                        continue;
                    }
                    order.anonBasketId = null;
                    order.orderOwner = User.find("login = ?",  username ).first();
                    order.save();
                    
                }
                Logger.debug("Anonymous basket conversion complete!");
                
            }
            
            return true;
        }
        Logger.debug("Login failed.");
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
