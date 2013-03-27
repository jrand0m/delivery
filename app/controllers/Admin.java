package controllers;

import models.*;
import models.geo.City;
import models.settings.SystemSetting;
import models.time.WorkHours;
import org.joda.time.LocalDateTime;
import play.mvc.Controller;
import play.mvc.Result;
import services.GeoService;
import services.OrderService;
import services.RestaurantService;
import services.SystemService;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static helpers.OrderUtils.convertCentsToMoney;

//todo:@With(Secure.class)@Check(UserType.VD_ADMIN)
public class Admin extends Controller {

    @Inject
    private static GeoService geoService;
    @Inject
    private static RestaurantService restaurantService;
    @Inject
    private static SystemService sysService;
    @Inject
    private static OrderService orderService;


    public static Result index() {
        //render();
        return TODO;
    }

    public static Result showCities() {
//        List<City> cities = geoService.getAllCities();
//        render(cities);
        return TODO;
    }

    public static Result showTypes() {
//        List<RestaurantCategory> types = restaurantService.getAllCategories();
//        render(types);
        return TODO;
    }

    public static Result showCategories() {
//        List<MenuItemGroup> categories = restaurantService.getAllMenuItemGroups();
//        render(categories);
        return TODO;
    }

    public static Result showMenuItems(Integer id) {
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("restaurants", restaurants);
//        if (id == null) {
//            id = restaurants.get(0).id;
//        }
//        List<MenuItem> items = restaurantService.getAllMenuItemsFor(id);
//        renderArgs.put("items", items);
//        render();
        return TODO;
    }

    public static Result showOrders(Long id) {
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("restaurants", restaurants);
//        if (id == null) {
//            List<Order> orders = orderService.getAllOrdersOrderedByCreationDate();
//            renderArgs.put("orders", orders);
//        } else {
//            List<Order> orders = orderService.getOrdersOrderedByCreationDateFor(id);
//            renderArgs.put("orders", orders);
//        }
//        render();
        return TODO;
    }

    public static Result showRestaurants(Long id) {
//        List<Restaurant> restaurants = null;
//        if (id == null) {
//            restaurants = restaurantService.getAllRestaurants();
//        } else {
//            restaurants = geoService.getRestsByCity(id);
//        }
//        renderArgs.put("restaurants", restaurants);
//        renderArgs.put("cities", geoService.getAllCities());
//        render();
        return TODO;
    }

    public static Result showUsers() {
        return TODO;
    }

    public static Result showSettings() {
//        List<SystemSetting> settings = sysService.findAllSystemSettings();
//        renderArgs.put("settings", settings);
//        render();
        return TODO;
    }

    public static Result addType(RestaurantCategory cat) {
//        if (cat.categoryDisplayNameUA == null) {
//            renderArgs.put("thankmessage", "Please create new one.");
//            render();
//        }
//        restaurantService.insertCategory(cat);
//        renderArgs.put("thankmessage", "Sucessfuly added : "
//                + cat.categoryDisplayNameUA);
//        render();
        return TODO;
    }

    public static Result addCategory(MenuItemGroup group, Integer id) {
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("restaurants", restaurants);
//        if (id == null) {
//            renderArgs.put("message", "Please create new one.");
//            render();
//        }
//        group.restaurant_Id = id;
//        restaurantService.insertMenuGroup(group);
//        renderArgs.put("message", "Sucessfuly added : " + group.name);
//        render();
        return TODO;
    }

    public static Result editCategory(Long id) {
//        notFoundIfNull(id);
//        MenuItemGroup group = restaurantService.getMenuGroupById(id);
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("group", group);
//        renderArgs.put("restaurants", restaurants);
//        renderTemplate("Admin/addCategory.html");
        return TODO;
    }

    public static Result addMenuItem(MenuItem item, Integer id, Integer groupid) {
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("restaurants", restaurants);
//        List<MenuItemGroup> groups = restaurantService.getAllMenuItemGroups();
//        renderArgs.put("groups", groups);
//        if (item == null || id == null || groupid == null) {
//            renderArgs.put("message", "Please create new one.");
//            render();
//        }
//        item.menuItemCreated = new LocalDateTime();
//        item.restaurantId = id;
//        item.menuItemGroupId = groupid;
//        restaurantService.insertMenuItem(item);
//        renderArgs.put("message", "Created!");
//        render();
        return TODO;
    }

    public static Result editMenuItem(Long id) {
//        MenuItem item = restaurantService.getMenuItemById(id);
//        renderArgs.put("group", item.menuItemGroupId);
//        renderArgs.put("id", item.restaurantId);
//        renderArgs.put("item", item);
//        render("Admin/addMenuItem.html");
        return TODO;
    }

    public static Result addCity(City city) {
//        if (city == null || city.cityNameKey == null) {
//            render();
//        }
//        if (city.city_id != null) {
//            geoService.updateCity(city);
//        } else {
//            geoService.insertCity(city);
//        }
//        render();
        return TODO;
    }

    public static Result editCity(Long id) {
//        notFoundIfNull(id);
//        City city = geoService.getCityById(id);
//        notFoundIfNull(city);
//        renderArgs.put("city", city);
//        renderTemplate("Admin/addCity.html");
        return TODO;
    }

    public static Result editType(Long id) {
//        notFoundIfNull(id);
//        RestaurantCategory cat = restaurantService.getRestaurantCategoryById(id);
//        notFoundIfNull(id);
//        renderArgs.put("cat", cat);
//        renderTemplate("Admin/addType.html");
        return TODO;
    }

    public static Result addRestaurant(Restaurant restaurant, Integer catid,
                                     Long cityid, String openfrom, String opento, String barmanlogin,
                                     String barmanpwd) {

//        List<City> cities = geoService.getVisibleCities();
//        List<RestaurantCategory> types = restaurantService.getAllCategories();
//        renderArgs.put("cities", cities);
//        renderArgs.put("types", types);
//        if (cityid == null) {
//            renderArgs.put("errormessage", "no cityid");
//            render();
//        }
//        // notFoundIfNull(city);
//
//        restaurant.deviceLogin = barmanlogin.trim();
//        restaurant.devicePassword = barmanpwd.trim();
//
//        restaurant.city_id = cityid;
//        restaurant.category_id = catid;
//        WorkHours workHours = restaurantService.getWorkHours(restaurant);
//        if (workHours == null) {
//            workHours = new WorkHours(openfrom, opento);
//        } else {
//            workHours.updateAll(openfrom, opento);
//        }
//        workHours = restaurantService.insertWorkHours(workHours);
//        restaurantService.setWorkHoursFor(restaurant, workHours);
//        restaurantService.insertRestaurant(restaurant);
//        render();
        return TODO;
    }

    public static Result deleteCity() {

//        index();
        return TODO;
    }

    public static Result editRestaurant(Long id) {
//        notFoundIfNull(id);
//        Restaurant restaurant = restaurantService.getById(id);
//        renderArgs.put("restaurant", restaurant);
//        renderArgs.put("catid", restaurant.category.id);
//        renderArgs.put("cityid", restaurant.city.city_id);
//        // renderArgs.put("openfrom",
//        // restaurant.workHours.regularDays.iterator().next().from);
//        // renderArgs.put("opento",
//        // restaurant.workHours.regularDays.iterator().next().to);
//        renderArgs.put("barmanlogin", restaurant.deviceLogin);
//        renderArgs.put("barmanpwd", restaurant.devicePassword);
//        List<City> cities = geoService.getVisibleCities();
//        List<RestaurantCategory> types = restaurantService.getAllCategories();
//        renderArgs.put("cities", cities);
//        renderArgs.put("types", types);
//        renderTemplate("Admin/addRestaurant.html");
        return TODO;
    }

    public static Result deleteRestaurant(Long id) {
//        notFoundIfNull(id, "missing argument");
//        restaurantService.deleteRestaurant(id);
//        index();
        return TODO;
    }

    public static Result addComp(Long id, String name, String descr, Integer price) {
//        MenuItem item = restaurantService.getMenuItemById(id);
//        MenuItemComponent component = new MenuItemComponent();
//        component.name = name;
//        component.price = convertCentsToMoney(price);
//        component.description = descr;
//        component.menuItemId = item.id;
//        restaurantService.insertItemComponent(component);
//        editMenuItem(item.id);
        return TODO;
    }

    public static Result deleteComponent(Long id) {
//        restaurantService.deleteMenuItemComponent(id);
        return TODO;
    }

    public static Result uploadLogo(Long id, File logo)
            throws FileNotFoundException {
//        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
//        renderArgs.put("restaurants", restaurants);
//        if (id == null) {
//            render();
//        }
//        restaurantService.setNewLogo(id, logo);
        return TODO;
    }

}
