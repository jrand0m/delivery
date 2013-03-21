package controllers;

import enumerations.OrderStatus;
import enumerations.UserType;
import models.*;
import models.dto.extern.BasketJSON;
import models.dto.extern.LastOrdersJSON;
import models.dto.extern.MenuCompWrapJson;
import models.geo.Address;
import models.geo.City;
import models.geo.Street;
import models.time.WorkHours;
import models.users.User;
import play.Logger;
import play.i18n.Lang;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.*;
import views.html.Application.index;

import javax.inject.Inject;
import java.util.*;
import static controllers.Security.*;

@Authenticated(LoggedInOrCreateAnonymous.class)
public class Application extends Controller {

    // TODO Make more flexible(extract to SystemSetting)
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;
    @Inject
    private GeoService geoService;
    @Inject
    private OrderService orderService;
    @Inject
    private RestaurantService restaurantService;
    @Inject
    private LiveService liveService;
    @Inject
    private BasketService basketService;
    @Inject
    private MailService mailService;
    @Inject
    private UserService userService;



    public static class SESSION_KEYS {
        public static final String CITY_ID = "city";
    }

    //todo:@Before(unless = {"getLastOrders", "serveLogo", "loadFix", "comps","changeCity", "changeLng"}
                                         /*
										 * unless =
										 * {"getCurrentUser","guessCity"
										 * ,"deleteOrRemOrderItem"
										 * ,"createNewOpenOrder",
										 * "createOrAddOrderItem", }
										 */
    public static void _pre() {
//       Logger.debug("start");
//        User user = getCurrentUser();
//        renderArgs.put(RENDER_KEYS.USER, user);
//        if (!session.contains(SESSION_KEYS.CITY_ID)) {
//            Logger.debug("No city defined in cookies");
//            City city = geoService.getCityByRemoteAddress(request.remoteAddress);
//            if (city != null) {
//                Logger.debug("Putting city_id = %d to session", city.city_id);
//                session.put(SESSION_KEYS.CITY_ID, city.city_id);
//            } else {
//                Logger.warn("No visible city found");
//            }
//        }
//        flash.put("url", request.url);
//        Logger.debug("done");
    }

    public static Result order(Long id) {
//        notFoundIfNull(id);
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        notFoundIfNull(user);
//        Order order = orderService.getOrderBySIDAndOwner(id, user);
//        notFoundIfNull(order);
//        renderArgs.put("order", order);
//        render("/Application/order.html");
        return TODO;
    }

    public static Result checkout(Long id) {
//        // FIXME move to locker ?
//        notFoundIfNull(id);
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        notFoundIfNull(user);
//        Order order = orderService.getOrderBySIDAndOwner(id, user);
//        notFoundIfNull(order);
//        if (order.orderStatus != OrderStatus.OPEN) {
//            redirect("Application.order", id);
//        }
//        if (orderService.isEmptyOrder(order)) {
//            redirect("Application.showMenu", order.restaurant.id);
//        }
//
//        City city = orderService.getOrdersCity(order);
//        List<Street> streets = geoService.getStreetsOf(city);
//        renderArgs.put("streets", streets);
//        renderArgs.put("order", order);
//        render("/Application/prepareOrder.html");
        return TODO;
    }

    public Result index() {

        String cityId = session(SESSION_KEYS.CITY_ID);
        if (Logger.isDebugEnabled()) {
            Logger.debug(String.format("Restaurant service is initialized with -> %s", restaurantService));
            Logger.debug(String.format("Geo service is initialized with -> %s", geoService));
            Logger.debug(String.format("Got city_id = %s from session", cityId));
        }

        if (cityId == null || !cityId.matches("([1-9])|([1-9][0-9])")) {
            cityId = "1"; //TODO create test that checks that new user gets default id
                          //TODO get default id from service
                          //todo get city id based on ip
            session(SESSION_KEYS.CITY_ID, cityId);
        }
        List<Restaurant> restaurants = geoService.getIndexPageRestsByCity(Long.parseLong(cityId));
        Map<Integer, WorkHours> workHours = restaurantService.getWorkHoursMap(restaurants);
        Map<Integer,String>descriptions = restaurantService.getDescriptionsMapFor(restaurants);
        return ok(index.render(restaurants,descriptions,workHours));
    }


    public static Result showRestaurants() {
//        List<City> cityList = geoService.getVisibleCities();
//        String cityId = session.get(SESSION_KEYS.CITY_ID);
//        if (cityId == null || !cityId.matches("([1-9])|([1-9][0-9])")) {
//            badRequest();
//        }
//
//        List<Restaurant> restaurants = geoService.getRestsByCity(Long.parseLong(cityId));
//        // categories
//        Set<RestaurantCategory> categories = new HashSet<RestaurantCategory>();
//        for (Restaurant rest : restaurants) {
//            RestaurantCategory category = rest.category;//XXX ??? must be deep get !
//            if (category != null) {
//                categories.add(category);
//            }
//        }
//        renderArgs.put(RENDER_KEYS.RESTAURANTS_CATEGORIES, categories);
//        renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
//        renderArgs.put(RENDER_KEYS.SHOW_MENU_RESTAURANTS, restaurants);
//        render();
        return TODO;
    }

    public Result showMenu(Integer id) {
        List<MenuItemGroup> menuItems = restaurantService.getMenuBookFor(id);
        Restaurant restaurant = restaurantService.getById(id);
        if (restaurant == null/*todo remove with NOT_FOUND*/ || menuItems.size() == 0){
            return redirect("/");
        }
        return ok(showMenu.render());
    }

    public static Result newUser() {
//        render();
        return TODO;
    }

    public Result registerNewUser(User user) {
//        Logger.debug(">>> Registering new user %s", user.toString());
//        userService.insertUser(user);
//        Logger.debug(">>> TODO: Try converting order history.");
//        try {
//            Secure.authenticate(user.login, user.password, false);
//        } catch (Throwable e) {
//            Logger.error(e, "Failed to login in App.registerNewUser()");
//            error();
//        }

        return index();
    }

    /**
     * id:ff808181333095ab0133309ed4e90005 name:sda city:1 surname:asd
     * street:asd email:asd@cacc.ccom apartment:ds phone:asd oplata:on
     * TODO get orderCity one time(extract to varialble)
     */
    public static Result checkAndSend(Long id, Long aid, String name,
                                    Integer city, String sname, Long streetid, String street,
                                    String email, String app, String phone,
                                    String oplata, String addinfo) {
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        if (user == null)
//            Logger.error("User is null");
//        badRequest();
//        checkAuthenticity();
//        if (id == null) {
//            Logger.error("Order id is null or empty");
//            badRequest();
//        }
//
//        Order o = orderService.getOrderBySIDAndOwner(id, user);
//        if (o == null) {
//            Logger.error("No such order for current owner");
//            badRequest();
//        }
//        // TODO 'FROZEN' STATUS
//        if (!o.orderStatus.equals(OrderStatus.OPEN)) {
//            Logger.error("Bad order state");
//            badRequest();
//        }
//        Restaurant r = restaurantService.getByOrder(o);
//        List<OrderItem> items = orderService.getItems(o);
//        for (OrderItem itm : items) {
//            if (!orderService.getRestaurantFromOrderItem(itm).equals(r)) {
//                o.orderStatus = OrderStatus.DECLINED;
//                orderService.update(o);
//                Logger.error(
//                        "Different restaturants in order items. order will be declined. IP: %s, user id: %s ",
//                        request.remoteAddress, user.id);
//                error("Consistency error, root node mismatch. Order declined");
//            }
//        }
//        Address address = null;
//        if (userService.isUserInRole(user, UserType.ANONYMOUS)) {
//            if (user.name.isEmpty()) {
//                user.name = name;
//                if (!validation.valid(user.name).ok) {
//                    validation.addError("name", "name.required");
//                }
//            }
//            if (user.phoneNumber == null || !user.phoneNumber.isEmpty()) {
//                user.phoneNumber = phone;
//                validation.required(phone);
//            }
//
//            address = new Address();
//            Street str = null;
//            if (streetid != null) {
//                str = geoService.getStreetById(streetid);
//            }
//            if (str != null && orderService.getOrdersCity(o).city_id == str.city_id) {
//                address.street = str;
//            } else {
//                Street streetObj = new Street();
//                streetObj.city_id = orderService.getOrdersCity(o).city_id;
//                streetObj.title_ua = street;
//                streetObj.title_en = street;
//                geoService.insertStreet(streetObj);
//                address.street = streetObj;
//                // validation.addError("address.street",
//                // "street.notacceptable");
//            }
//            address.apartmentsNumber = app;
//            address.buildingNumber = "???";//TODO add later
//            address.additionalInfo = addinfo;
//            // TODO do proper validation
//            address = geoService.validateAndInsertAddress(address, validation);
//            if (validation.hasErrors()) {
//                params.flash();
//                validation.keep();
//                checkout(o.id);
//            } else {
//                userService.addAddressToUserAddressBook(address, user);
//                userService.update(user);
//            }
//
//        } else {
//            if (aid != null) {
//                address = geoService.getAddressById(aid);
//                if (!userService.addressIsAssociatedWithUser(address, user)) {
//                    address = null;
//                }
//            }
//            if (address == null) {
//                address = new Address();
//                Street str = null;
//                if (streetid != null) {
//                    str = geoService.getStreetById(streetid);
//                }
//                if (str != null && orderService.getOrdersCity(o).city_id == str.city_id) {
//                    address.street_id = str.id;
//                    //address.street = str
//                } else {
//                    validation.addError("address.street",
//                            "street.notacceptable");
//                }
//                address.apartmentsNumber = app;
//                address.buildingNumber = "???";
//                address.additionalInfo = addinfo;
//                geoService.validateAndInsertAddress(address, validation);
//                if (validation.hasErrors()) {
//                    params.flash();
//                    validation.keep();
//                    checkout(o.id);
//                }
//                userService.addAddressToUserAddressBook(address, user);
//            }
//        }
//        o.delivery_address_id = address.id;
//
//        o.orderStatus = OrderStatus.SENT;
//        o.deliveryPrice = o.getDeliveryPrice();
//
//        orderService.update(o);
//        /*try {
//              SimpleEmail simpleEmail = new SimpleEmail();
//              simpleEmail.setFrom("no-reply <robot@vdoma.com.ua>");
//              simpleEmail.addTo("380964831310@sms.kyivstar.net");
//              simpleEmail.addTo("380673864501@sms.kyivstar.net");
//              simpleEmail.setSubject(Transliterator
//                      .transliterate(o.restaurant.title));
//              simpleEmail.setMsg("tel:"
//                      + (phone == null ? "-" : Transliterator.transliterate(phone))
//                      + ";price:"
//                      + (new BigDecimal(o.getGrandTotal()).setScale(2,
//                              RoundingMode.HALF_EVEN).divide(new BigDecimal(100)
//                              .setScale(2, RoundingMode.HALF_EVEN))) + ";to:"
//                      + Transliterator
//                      .transliterate(street) +"," +
//                      Transliterator
//                      .transliterate(app)
//                      );
//              Mail.send(simpleEmail);
//          } catch (Exception e) {
//              Logger.error("Failed to send notification email! " + e.getMessage(), e);
//          }*/
//        mailService.sendNewOrderNotification(o);
//        return order(o.id);
           return TODO;
    }

    /* ------------ UTIL Pages -------------- */

    /**
     * Change Language
     */
    public static Result changeLng(String lang) {
//        Lang.change(lang);
//        String url = flash.get("url");
//        if (url == null || url.isEmpty()) {
//            url = "/";
//        }
//        redirect(url);
        return TODO;
    }

    public static Result changeCity(Long id) {
//        Logger.debug("changing city to city_id = %s", id);
//        City city = geoService.getCityById(id);
//        String url = flash.get("url");
//        if (url == null || url.isEmpty()) {
//            url = "/";
//        }
//        redirect(url);
        return TODO;
    }

    /* ------------ AJAX ---------------- */

    public static Result getLastOrders(boolean top) {
//        ArrayList<LastOrdersJSON> o = new ArrayList<LastOrdersJSON>();
//        if (session.contains(SESSION_KEYS.CITY_ID)) {
//            City city = geoService.getCityById(Long.parseLong(session.get(SESSION_KEYS.CITY_ID)));
//            List<Order> recent = null;
//            await(5000);
//            o = liveService.getLastOrdersForCity(city);
//        } else {
//            Logger.debug("No city in sesion, waiting to prevent ddos/dos");
//            await(20000);
//        }
//        renderJSON(o);
        return TODO;
    }

    public Result comps(final Long id) {
//        notFoundIfNull(id);
//        await(1000);
//        /*MenuItem mi = MenuItem.findById(id);
//          List<MenuItemComponent> comps = MenuItemComponent.find(
//                  MenuItemComponent.FIELDS.ITM_ROOT + "= ?", mi).fetch();
//          List<MenuComponentsJSON> asJsons = new ArrayList<MenuComponentsJSON>();
//          for (MenuItemComponent i : comps) {
//              asJsons.add(new MenuComponentsJSON(i));
//          }
//          MenuCompWrapJson wrap = new MenuCompWrapJson();
//          wrap.no = id.toString();
//          wrap.dc = mi.description();
//          wrap.nm = mi.name();
//          wrap.pc = mi.price;
//          wrap.items = asJsons; */
//        MenuCompWrapJson wrap = basketService.getComponentsForMenuItem(id)/*.wrapInJson()*/;
//        renderJSON(wrap);
        return TODO;
    }

    public Result addOrderItem(Long id, String comps /*Long... component*/) {
//        if (!Security.isConnected() || id == null) {
//            await(15000);
//            notFound("Order not found");
//        }
//        MenuItem itm = restaurantService.getMenuItemById(id);
//        // FIXME problems if we dynamicly delete order and user will refresh
//        // page. add to js basket failure to refresh by updateurl
//        notFoundIfNull(itm);
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        notFoundIfNull(user);
//        // TODO add safe caching
//        Order order = orderService.getCurrentOrderFor(user, itm.restaurant);
//        notFoundIfNull(order);
//        notFoundIfNull(comps);
//        String c[] = comps.split(",");
//        Long component[] = new Long[c.length];
//        for (int i = 0 ; i<c.length; i++){
//            component[i] = Long.valueOf(c[i]);
//        }
//        OrderItem oi = new OrderItem(itm, order, component);
//        oi = orderService.insertOrderItem(oi);
//        orderService.update(order);
//        BasketJSON json = basketService.getBasketAsJSON(order);
//        renderJSON(json);
        return TODO;
    }

    public Result cngOrderItem(Long id, Integer count) {
//        if (!Security.isConnected() || id == null || count == null) {
//            await(15000);
//            notFound("Order not found");
//        }
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        notFoundIfNull(user);
//        OrderItem itm = orderService.getOrderItemByIdAndOwner(id, user);
//        notFoundIfNull(itm);
//        if (itm.order.orderStatus == OrderStatus.OPEN) {
//            if (count < 1) {
//                orderService.deleteOrderItem(itm);
//            } else {
//                itm.count = count;
//                orderService.updateOrderItem(itm);
//            }
//            BasketJSON json = basketService.getBasketAsJSON(itm.order);
//            renderJSON(json);
//        }
//        notFound();
        return TODO;
    }

    public Result basket(Long chart) {
        /*
           * FIXME best place to create new user when user reaches place with
           * basket create token basket checks if the user is logged in and if it
           * is not searches for token and if token found and correct then creates
           * new anon user and logs him in. name for the user will be
           * Anonymous_<num>
           *
           * if user chooses to register than we create new user for him and
           * transferring all orders, addresses from anon to new one and deleting
           * anon.
           *
           * also there is need to remove all anonymous that has last login date >
           * 30 days and have no orders in state sent.
           *
           * please notice that some far away time in future there will be a
           * moment when we will need to run batch job for deleting all anons with
           * last login date > 1 year(or other period, mb 3 month) which have
           * associated orders in above sent state
           */
//        notFoundIfNull(chart);
//        Order order = null;
//        User user = (User) renderArgs.get(RENDER_KEYS.USER);
//        if (user == null) {
//            user = userService.createAnonymousUser();
//            session.put("username", user.login);
//        }
//        Restaurant restaurant = restaurantService.getById(chart);
//        notFoundIfNull(restaurant);
//        order = orderService.getCurrentOrderFor(user, restaurant);
//        if (order == null) {
//            order = orderService.createNewOpenOrderFor(user, restaurant);
//        }
//        BasketJSON json = basketService.getBasketAsJSON(order);
//        renderJSON(json);
        return TODO;
    }

    public  Result serveLogo(Integer id) {
        String url = restaurantService.getLogoPathFor(id);
        if (url!=null) {
            return redirect(url);
        } else {
            return notFound();
        }
    }

    /* ----------- private -------------- */

    /**
     * FIXME fix guess system by moving to new MyJob.now()
     * @param ip - ip address of client
     * @return City to show
     */
/*	private static City guessCity(String ip) {
		Boolean guessByIp = (Boolean) Cache
				.get(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED);
		if (guessByIp == null) {
			String guessIpEnabled = PropertyVault
					.getSystemValueFor(SystemSetting.KEYS.GUESS_CITY_ENABLED);
			guessByIp = Boolean.valueOf(guessIpEnabled);
			Cache.set(CACHE_KEYS.GUESS_CITY_SYSOPT_ENABLED, guessByIp, "8h");
		}
		if (!guessByIp) {
			play.Logger.debug("Guessing disabled, returning default");
			return GeoDataHelper.getSystemDefaultCity();
		}
		play.Logger.debug("Guessing enabled, trying to guess city by ip");
		IpGeoData igdata = IpGeoData.find(IpGeoData.HQL.BY_IP, ip).first();
		if (igdata == null) {
			igdata = await(GeoDataHelper.requestFromExternalServer(ip));
		}
		if (igdata == null || igdata.city == null) {
			return GeoDataHelper.getSystemDefaultCity();
		}
		return igdata.city;
	}*/


}
