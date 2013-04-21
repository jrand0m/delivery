package controllers;

import models.Order;
import models.geo.Address;
import play.mvc.Controller;
import play.mvc.Result;
import services.GeoService;
import services.UserService;

import javax.inject.Inject;

/**
 * User Personal Page Controller
 *
 * @author mike
 */
//todo: @With(Secure.class)@Check(UserType.REGISTERED)
public class Locker extends Controller {
    @Inject
    private static GeoService geoService;
    @Inject
    private static UserService userService;

    public static final class RENDER_KEYS {

        public static final String USER = "user";

    }

    //TODO:@Before
//    public static Result __prepare() {
//        Logger.debug(">>> Accesing locker %s ? %s", Security.connected(),
//                session.getId());
//        if (!Security.isConnected()) {
//            Logger.debug(">>> Anonymous locker -> redirecting to basket()");
//            notFound();
//
//        }
//        String userName = Security.connected();
//        User user = userService.getUserByLogin(userName);
//        if (user == null) {
//            Logger.debug(">>> locker -> user is null -> forbidden");
//            forbidden();
//        }
//        renderArgs.put(RENDER_KEYS.USER, user);
//    }

    public static Result index() {
        // FIXME move to locker
        Order order = null;
        //renderArgs.put("order", order);
        return TODO;//render("/Locker/cabinetLastOrders.html");
    }

    public static Result cabinetProfile() {
        // FIXME move to locker
        Order order = null;
        //renderArgs.put("order", order);
        return TODO; //render("/Locker/cabinetProfile.html");
    }


    public static Result addAddress(Address address) {

        if (address == null) {
            return TODO;//return redirect(controllers.routes.Locker.index());
        }

        geoService.insertAddress(address);
        //todo: User user = (User) renderArgs.get(RENDER_KEYS.USER);
        // todo: userService.addAddressToUserAddressBook(address, user);
        // TODO in future do it asynchronously!
        return TODO;
    }

    public static Result editAddress(Address address) {

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
        return TODO;
    }

    public static Result deleteAddress(Long id) {
        if (id != null) {
            /*// TODO add logging
               UserAddress address = geoService.get
               address.deleted = true;
               address.save();*/
        }
        return TODO;
    }

}
