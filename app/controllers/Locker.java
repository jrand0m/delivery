package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import models.Address;
import models.Order;
import models.OrderItem;
import models.User;
import models.Order.OrderStatus;
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
        if (!Security.isConnected()) {
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
        User user = (User) renderArgs.get(Application.USER_RENDER_KEY);

        List<Address> addressList = user.addressBook;

        List<Order> orderList = Order.find("orderOwner = ?", user).fetch();
        render(user, addressList, orderList);
    }

    public static void basket() {
        User user = (User) renderArgs.get(Application.USER_RENDER_KEY);
        Order order = Order.find("orderOwner = ? and orderStatus = ?", user,
                OrderStatus.OPEN).first();
        List<OrderItem> items = new ArrayList<OrderItem>();
        if (order != null) {
            items = order.items;
        } else {
            order = Application.createNewOpenOrder(user);

        }
        renderArgs.put("order", order);
        renderArgs.put("orderItems", items);
        render();

    }

    public static void addAddress(Address address) {
        if (address == null) {
            redirect(Router.getFullUrl("Locker.index"));
        }
        address.userId = (User) renderArgs.get(Application.USER_RENDER_KEY);
        address.create();
        // TODO in future do it asynchronously!
        todo();
    }

    public static void editAddress(Address address) {

        if (address.id == null) {
            error("Data inconsistency detected");
        }

        address.userId = (User) renderArgs.get(Application.USER_RENDER_KEY);
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
