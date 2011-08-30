package controllers;

import java.util.List;

import models.Address;
import models.Order;
import models.Order.OrderStatus;
import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

/**
 * User Personal Page Controller
 * 
 * @author mike
 * */
@With(Secure.class)
public class Locker extends Controller {
    @Before
    public static void __prepare() {
        Logger.debug("Accesing locker");
        if (!Security.isConnected() && !session.contains(Application.ANONYMOUS_BASKET_ID)) {
                Logger.debug("No credentials found... %s",!Security.isConnected() && !session.contains(Application.ANONYMOUS_BASKET_ID) );
                flash.put("url", Router.getFullUrl("Locker.index"));
                try {
                    Secure.login();
                } catch (Throwable e) {
                    // TODO checkout how to redirect back here !
                    forbidden();
                    e.printStackTrace();
                    return;
                }
            
        }
        String userName = Security.connected();
        User user = User.find("login = ?", userName).first();
        if (user == null && !session.contains(Application.ANONYMOUS_BASKET_ID)) {
            forbidden();
            return;
        }
        renderArgs.put(Application.USER_RENDER_KEY, user);
    }
    @Before(unless="basket")
    public static void allRoadsLeadToBasket(){
        Logger.debug("Anonymous locker -> redirecting to basket()");
        if (!Security.isConnected()){basket();return;}
    }

    /**
     * Shows user's personal page
     */
    public static void index() {
        Logger.debug("Entering index");
        User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        
        List<Address> addressList = user.addressBook;

        List<Order> orderList = Order.find("orderOwner = ?", user).fetch();
        render(user, addressList, orderList);
    }

    public static void basket() {
        Logger.debug("Entering basket");
        Order order = null;
        User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        if (user == null) {
            order = Order.find("orderOwner = ? and orderStatus = ?", user,
                    OrderStatus.OPEN).first();
        } else if (session.contains(Application.ANONYMOUS_BASKET_ID)) {
            order = Order.find("anonBasketId = ? and orderStatus = ?",
                    session.get(Application.ANONYMOUS_BASKET_ID),
                    OrderStatus.OPEN).first();
        }
        renderArgs.put("order", order);
        render();

    }

    public static void addAddress(Address address) {
        
        if (address == null) {
            redirect(Router.getFullUrl("Locker.index"));
        }
        address.user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        address.create();
        // TODO in future do it asynchronously!
        todo();
    }

    public static void editAddress(Address address) {

        if (address.id == null) {
            error("Data inconsistency detected");
        }

        address.user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        Address base = Address.findById(address.id);
        if (!address.equals(base)) {
            // TODO Make logging
            // LogItem.log(Address.class.getName(), field, newValue, oldValue,
            // address.id, modifiedBy, modifiedOn)
            address.save();
        }

        // TODO in future do it asynchronously!
        index();
    }

    public static void deleteAddress(Long id) {
        if (id != null) {
            // TODO add logging
            Address address = Address.findById(id);
            address.deleted = true;
            address.save();
        }
        index();
    }

}
