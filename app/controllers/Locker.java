package controllers;

import annotations.Check;
import enumerations.UserType;
import models.Order;
import models.geo.Address;
import models.users.User;
import play.Logger;
import play.modules.guice.InjectSupport;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;
import services.GeoService;
import services.UserService;

import javax.inject.Inject;

/**
 * User Personal Page Controller
 *
 * @author mike
 */
@With(Secure.class)
@Check(UserType.REGISTERED)
@InjectSupport
public class Locker extends Controller {
    @Inject
    private static GeoService geoService;
    @Inject
    private static UserService userService;

    public static final class RENDER_KEYS {

        public static final String USER = "user";

    }

    @Before
    public static void __prepare() {
        Logger.debug(">>> Accesing locker %s ? %s", Security.connected(),
                session.getId());
        if (!Security.isConnected()) {
            Logger.debug(">>> Anonymous locker -> redirecting to basket()");
            notFound();

        }
        String userName = Security.connected();
        User user = userService.getUserByLogin(userName);
        if (user == null) {
            Logger.debug(">>> locker -> user is null -> forbidden");
            forbidden();
        }
        renderArgs.put(RENDER_KEYS.USER, user);
    }

    public static void index() {
        // FIXME move to locker
        Order order = null;
        renderArgs.put("order", order);
        render("/Locker/cabinetLastOrders.html");
    }

    public static void cabinetProfile() {
        // FIXME move to locker
        Order order = null;
        renderArgs.put("order", order);
        render("/Locker/cabinetProfile.html");
    }


    public static void addAddress(Address address) {

        if (address == null) {
            redirect(Router.getFullUrl("Locker.index"));
        }

        geoService.insertAddress(address);
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        userService.addAddressToUserAddressBook(address, user);
        // TODO in future do it asynchronously!
        todo();
    }

    public static void editAddress(Address address) {

        if (address.id == null) {
            badRequest();
        }

        /*address.user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
          UserAddress base = UserAddress.findById(address.id);
          if (!address.equals(base)) {
              // TODO Make logging
              // LogItem.log(Address.class.getName(), field, newValue, oldValue,
              // address.id, modifiedBy, modifiedOn)
              address.save();
          }*/
        todo();
    }

    public static void deleteAddress(Long id) {
        if (id != null) {
            /*// TODO add logging
               UserAddress address = geoService.get
               address.deleted = true;
               address.save();*/
        }
        todo();
    }

}
