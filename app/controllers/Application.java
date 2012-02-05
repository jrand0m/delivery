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
import models.users.User;
import play.Logger;
import play.data.validation.Email;
import play.data.validation.Phone;
import play.i18n.Lang;
import play.mvc.Before;
import play.mvc.Controller;
import services.*;

import javax.inject.Inject;
import java.util.*;

import static helpers.OrderUtils.convertMoneyToCents;

public class Application extends Controller {

    // TODO Make more flexible(extract to SystemSetting)
    public static final Integer MAX_ITEM_COUNT_PER_ORDER = 64;
    @Inject
    private static GeoService geoService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static RestaurantService restaurantService;
    @Inject
    private static LiveService liveService;
    @Inject
    private static BasketService basketService;
    @Inject
    private static MailService mailService;
    @Inject
    private static UserService userService;


    public static class RENDER_KEYS {
        public static final String USER = "user";
        public static final String INDEX_RESTAURANTS = "restaurants";
        public static final String AVALIABLE_CITIES = "cities";
        public static final String SHOW_MENU_RESTAURANTS = "restaurants";
        public static final String RESTAURANTS_CATEGORIES = "categories";
    }

    public static class SESSION_KEYS {
        public static final String CITY_ID = "city";
    }

    @Before(unless = {"getLastOrders", "serveLogo", "loadFix", "comps",
            "changeCity", "changeLang"}/*
										 * unless =
										 * {"getCurrentUser","guessCity"
										 * ,"deleteOrRemOrderItem"
										 * ,"createNewOpenOrder",
										 * "createOrAddOrderItem", }
										 */)
    public static void _pre() {
        User user = getCurrentUser();
        renderArgs.put(RENDER_KEYS.USER, user);
        if (!session.contains(SESSION_KEYS.CITY_ID)) {
            Logger.debug("No city defined in cookies");
            City city = geoService.getCityByRemoteAddress(request.remoteAddress);
            if (city != null) {
                session.put(SESSION_KEYS.CITY_ID, city.id);
            } else {
                Logger.warn("No visible city found");
            }
        }
        flash.put("url", request.url);
    }

    public static void order(String id) {
        notFoundIfNull(id);
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        notFoundIfNull(user);
        Order order = orderService.getOrderBySIDAndOwner(id, user);
        notFoundIfNull(order);
        renderArgs.put("order", order);
        render("/Application/order.html");
    }

    public static void checkout(String id) {
        // FIXME move to locker ?
        notFoundIfNull(id);
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        notFoundIfNull(user);
        Order order = orderService.getOrderBySIDAndOwner(id, user);
        notFoundIfNull(order);
        if (order.orderStatus != OrderStatus.OPEN) {
            redirect("Application.order", id);
        }
        if (orderService.isEmptyOrder(order)) {
            redirect("Application.showMenu", order.restaurant.id);
        }

        City city = orderService.getOrdersCity(order);
        List<Street> streets = geoService.getStreetsOf(city);
        renderArgs.put("streets", streets);
        renderArgs.put("order", order);
        render("/Application/prepareOrder.html");
    }

    public static void index() {
        String cityId = session.get(SESSION_KEYS.CITY_ID);
        if (cityId == null || !cityId.matches("([1-9])|([1-9][0-9])")) {
            badRequest();
        }
        List<Restaurant> restaurants = geoService.getIndexPageRestsByCity(Long.parseLong(cityId));
        List<City> cityList = geoService.getVisibleCities();
        renderArgs.put(RENDER_KEYS.INDEX_RESTAURANTS, restaurants);
        renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
        render();
    }

    private static User getCurrentUser() {
        User user = null;
        if (Security.isConnected()) {
            user = userService.getUserByLogin(Security.connected());
        }
        return user;
    }

    public static void showRestaurants() {
        List<City> cityList = geoService.getVisibleCities();
        String cityId = session.get(SESSION_KEYS.CITY_ID);
        if (cityId == null || !cityId.matches("([1-9])|([1-9][0-9])")) {
            badRequest();
        }

        List<Restaurant> restaurants = geoService.getRestsByCity(Long.parseLong(cityId));
        // categories
        Set<RestaurantCategory> categories = new HashSet<RestaurantCategory>();
        for (Restaurant rest : restaurants) {
            RestaurantCategory category = rest.category;//XXX ??? must be deep get !
            if (category != null) {
                categories.add(category);
            }
        }
        renderArgs.put(RENDER_KEYS.RESTAURANTS_CATEGORIES, categories);
        renderArgs.put(RENDER_KEYS.AVALIABLE_CITIES, cityList);
        renderArgs.put(RENDER_KEYS.SHOW_MENU_RESTAURANTS, restaurants);
        render();
    }

    public static void showMenu(Long id) {
        notFoundIfNull(id);
        Restaurant restaurant = restaurantService.getById(id);
        notFoundIfNull(restaurant);
        List<MenuItemGroup> menuItems = restaurantService.getMenuBookFor(id);
        renderArgs.put("restaurant", restaurant);
        // FIXME Cache for 5 hours or it will die.
        render(menuItems);
    }

    public static void newUser() {
        render();
    }

    public static void registerNewUser(User user) {
        Logger.debug(">>> Registering new user %s", user.toString());
        user.createdDate = new Date();
        user.lastLoginDate = new Date();
        userService.insertUser(user);
        Logger.debug(">>> TODO: Try converting order history.");
        try {
            Secure.authenticate(user.login, user.password, false);
        } catch (Throwable e) {
            Logger.error(e, "Failed to login in App.registerNewUser()");
            error();
        }

        index();
    }

    /**
     * id:ff808181333095ab0133309ed4e90005 name:sda city:1 surname:asd
     * street:asd email:asd@cacc.ccom apartment:ds phone:asd oplata:on
     * TODO get orderCity one time(extract to varialble)
     */
    public static void checkAndSend(String id, Long aid, String name,
                                    Integer city, String sname, Long streetid, String street,
                                    @Email String email, String app, @Phone String phone,
                                    String oplata, String addinfo) {
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        if (user == null)
            Logger.error("User is null");
        badRequest();
        checkAuthenticity();
        if (id == null || id.isEmpty()) {
            Logger.error("Order id is null or empty");
            badRequest();
        }

        Order o = orderService.getOrderBySIDAndOwner(id, user);
        if (o == null) {
            Logger.error("No such order for current owner");
            badRequest();
        }
        // TODO 'FROZEN' STATUS
        if (!o.orderStatus.equals(OrderStatus.OPEN)) {
            Logger.error("Bad order state");
            badRequest();
        }
        Restaurant r = restaurantService.getByOrder(o);
        List<OrderItem> items = orderService.getItems(o);
        for (OrderItem itm : items) {
            if (!orderService.getRestaurantFromOrderItem(itm).equals(r)) {
                o.orderStatus = OrderStatus.DECLINED;
                orderService.update(o);
                Logger.error(
                        "Different restaturants in order items. order will be declined. IP: %s, user id: %s ",
                        request.remoteAddress, user.id);
                error("Consistency error, root node mismatch. Order declined");
            }
        }
        Address address = null;
        if (userService.isUserInRole(user, UserType.ANONYMOUS)) {
            if (user.name.isEmpty()) {
                user.name = name;
                if (!validation.valid(user.name).ok) {
                    validation.addError("name", "name.required");
                }
            }
            if (user.phoneNumber == null || !user.phoneNumber.isEmpty()) {
                user.phoneNumber = phone;
                validation.required(phone);
            }

            address = new Address();
            Street str = null;
            if (streetid != null) {
                str = geoService.getStreetById(streetid);
            }
            if (str != null && orderService.getOrdersCity(o).id == str.city_id) {
                address.street = str;
            } else {
                Street streetObj = new Street();
                streetObj.city_id = orderService.getOrdersCity(o).id;
                streetObj.title_ua = street;
                streetObj.title_en = street;
                geoService.insertStreet(streetObj);
                address.street = streetObj;
                // validation.addError("address.street",
                // "street.notacceptable");
            }
            address.apartmentsNumber = app;
            address.buildingNumber = "???";//TODO add later
            address.additionalInfo = addinfo;
            // TODO do proper validation
            address = geoService.validateAndInsertAddress(address, validation);
            if (validation.hasErrors()) {
                params.flash();
                validation.keep();
                checkout(o.id);
            } else {
                userService.addAddressToUserAddressBook(address, user);
                userService.update(user);
            }

        } else {
            if (aid != null) {
                address = geoService.getAddressById(aid);
                if (!userService.addressIsAssociatedWithUser(address, user)) {
                    address = null;
                }
            }
            if (address == null) {
                address = new Address();
                Street str = null;
                if (streetid != null) {
                    str = geoService.getStreetById(streetid);
                }
                if (str != null && orderService.getOrdersCity(o).id == str.city_id) {
                    address.street_id = str.id;
                    //address.street = str
                } else {
                    validation.addError("address.street",
                            "street.notacceptable");
                }
                address.apartmentsNumber = app;
                address.buildingNumber = "???";
                address.additionalInfo = addinfo;
                geoService.validateAndInsertAddress(address, validation);
                if (validation.hasErrors()) {
                    params.flash();
                    validation.keep();
                    checkout(o.id);
                }
                userService.addAddressToUserAddressBook(address, user);
            }
        }
        o.deliveryAddress_id = address.id;

        o.orderStatus = OrderStatus.SENT;
        o.deliveryPrice = convertMoneyToCents(o.getDeliveryPrice());

        orderService.update(o);
        /*try {
              SimpleEmail simpleEmail = new SimpleEmail();
              simpleEmail.setFrom("no-reply <robot@vdoma.com.ua>");
              simpleEmail.addTo("380964831310@sms.kyivstar.net");
              simpleEmail.addTo("380673864501@sms.kyivstar.net");
              simpleEmail.setSubject(Transliterator
                      .transliterate(o.restaurant.title));
              simpleEmail.setMsg("tel:"
                      + (phone == null ? "-" : Transliterator.transliterate(phone))
                      + ";price:"
                      + (new BigDecimal(o.getGrandTotal()).setScale(2,
                              RoundingMode.HALF_EVEN).divide(new BigDecimal(100)
                              .setScale(2, RoundingMode.HALF_EVEN))) + ";to:"
                      + Transliterator
                      .transliterate(street) +"," +
                      Transliterator
                      .transliterate(app)
                      );
              Mail.send(simpleEmail);
          } catch (Exception e) {
              Logger.error("Failed to send notification email! " + e.getMessage(), e);
          }*/
        mailService.sendNewOrderNotification(o);
        order(o.id);
    }

    /* ------------ UTIL Pages -------------- */

    /**
     * Change Language
     */
    public static void changeLang(String lang) {
        Lang.change(lang);
        String url = flash.get("url");
        if (url == null || url.isEmpty()) {
            url = "/";
        }
        redirect(url);
    }

    public static void changeCity(Long id) {
        Logger.debug("changing city to id = %s", id);
        City city = geoService.getCityById(id);
        String url = flash.get("url");
        if (url == null || url.isEmpty()) {
            url = "/";
        }
        redirect(url);
    }

    /* ------------ AJAX ---------------- */

    public static void getLastOrders(boolean top) {
        ArrayList<LastOrdersJSON> o = new ArrayList<LastOrdersJSON>();
        if (session.contains(SESSION_KEYS.CITY_ID)) {
            City city = geoService.getCityById(Long.parseLong(session.get(SESSION_KEYS.CITY_ID)));
            List<Order> recent = null;
            o = liveService.getLastOrdersForCity(city);
        } else {
            Logger.debug("No city in sesion, waiting to prevent ddos/dos");
            await(20000);
        }
        renderJSON(o);
    }

    public static void comps(final Long id) {
        notFoundIfNull(id);
        await(1000);
        /*MenuItem mi = MenuItem.findById(id);
          List<MenuItemComponent> comps = MenuItemComponent.find(
                  MenuItemComponent.FIELDS.ITM_ROOT + "= ?", mi).fetch();
          List<MenuComponentsJSON> asJsons = new ArrayList<MenuComponentsJSON>();
          for (MenuItemComponent i : comps) {
              asJsons.add(new MenuComponentsJSON(i));
          }
          MenuCompWrapJson wrap = new MenuCompWrapJson();
          wrap.no = id.toString();
          wrap.dc = mi.description();
          wrap.nm = mi.name();
          wrap.pc = mi.price;
          wrap.items = asJsons; */
        MenuCompWrapJson wrap = basketService.getComponentsForMenuItem(id)/*.wrapInJson()*/;
        renderJSON(wrap);
    }

    public static void addOrderItem(Long id, Long... component) {
        if (!Security.isConnected() || id == null) {
            await(15000);
            notFound("Order not found");
        }
        MenuItem itm = restaurantService.getMenuItemById(id);
        // FIXME problems if we dynamicly delete order and user will refresh
        // page. add to js basket failure to refresh by updateurl
        notFoundIfNull(itm);
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        notFoundIfNull(user);
        // TODO add safe caching
        Order order = orderService.getCurrentOrderFor(user, itm.restaurant);
        notFoundIfNull(order);
        OrderItem oi = new OrderItem(itm, order, component);
        oi = orderService.insertOrderItem(oi);
        order.items.add(oi);// TODO add recursive save, or add this line to services
        orderService.update(order);
        renderJSON(new BasketJSON(order));
    }

    public static void cngOrderItem(Long id, Integer count) {
        if (!Security.isConnected() || id == null || count == null) {
            await(15000);
            notFound("Order not found");
        }
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        notFoundIfNull(user);
        OrderItem itm = orderService.getOrderItemByIdAndOwner(id, user);
        notFoundIfNull(itm);
        if (itm.order.orderStatus == OrderStatus.OPEN) {
            if (count < 1) {
                orderService.deleteOrderItem(itm);
            } else {
                itm.count = count;
                orderService.updateOrderItem(itm);
            }
            BasketJSON json = basketService.getBasketAsJSON(itm.order);
            renderJSON(json);
        }
        notFound();
    }

    public static void basket(Long chart) {
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
        notFoundIfNull(chart);
        Order order = null;
        User user = (User) renderArgs.get(RENDER_KEYS.USER);
        if (user == null) {
            user = userService.createAnonymousUser(); //TODO userService.createNewAnonymousUser();
        }
        Restaurant restaurant = restaurantService.getById(chart);
        notFoundIfNull(restaurant);
        order = orderService.getCurrentOrderFor(user, restaurant);
        if (order == null) {
            order = orderService.createNewOpenOrderFor(user, restaurant);
        }
        BasketJSON json = basketService.getBasketAsJSON(order);
        renderJSON(json);
    }

    public static void serveLogo(long id) {


        /*notFoundIfNull(restaurant);
          InputStream is;
          if (restaurant.logo.exists()) {
              response.setContentTypeIfNotSet(restaurant.logo.type());
              is = restaurant.logo.get();
          } else {
              is = new FileInputStream(Play.applicationPath
                      + "/public/images/no_image.jpg");
          }*/
        //renderBinary(is);
        String url = restaurantService.getLogoPathFor(id);
        redirect(url);
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

    /**
     * intended for local use so no 'public' modifier
     * TODO move to service
     *
     * @param jpaBase
     */
    private static Order createNewOpenOrder(final User user,
                                            Restaurant jpaBase) {
        Logger.debug(">>> Creating new order for user: %s", user);
        Order o = new Order();
        o.orderStatus = OrderStatus.OPEN;
        o.orderOwner = user;
        o.deleted = false;
        o.orderCreated = new Date();
        o.restaurant = jpaBase;
        o = orderService.insertOrder(o);
        Logger.debug(">>> Created new order: %s", o.toString());
        return o;
    }

}