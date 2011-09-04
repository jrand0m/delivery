package controllers;

import java.util.List;

import controllers.Secure.Security;

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
        Logger.debug(">>> Accesing locker %s ? %s", Security.connected() ,session.getId());
        if (!Security.isConnected()) {
            Logger.debug(">>> Anonymous locker -> redirecting to basket()");
            if (!Security.isConnected()){Application.basket();return;}
            
        }
        String userName = Security.connected();
        User user = User.find("login = ?", userName).first();
        if (user == null) {
            forbidden();
            return;
        }
        renderArgs.put(Application.USER_RENDER_KEY, user);
    }

    /**
     * Shows user's personal page
     */
    public static void index() {
        Logger.debug(">>> Entering index");
        User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        
        List<Address> addressList = user.addressBook;

        List<Order> orderList = Order.find("orderOwner = ?", user).fetch();
        render(user, addressList, orderList);
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
