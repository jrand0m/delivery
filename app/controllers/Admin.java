package controllers;

import annotations.Check;
import enumerations.UserType;
import models.*;
import models.geo.City;
import models.settings.SystemSetting;
import models.time.WorkHours;
import org.joda.time.LocalDateTime;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.With;
import services.GeoService;
import services.OrderService;
import services.RestaurantService;
import services.SystemService;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static helpers.OrderUtils.convertCentsToMoney;

@With(Secure.class)
@Check(UserType.VD_ADMIN)
@InjectSupport
public class Admin extends Controller {
<<<<<<< HEAD
	public static void index() {
		render();
	}

	public static void showCities() {
		List<City> cities = City.findAll();
		render(cities);
	}

	public static void showTypes() {
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		render(types);
	}

	public static void showCategories() {
		List<MenuItemGroup> categories = MenuItemGroup.findAll();
		render(categories);
	}

	public static void showMenuItems(Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			id = restaurants.get(0).id;
		}
		List<MenuItem> items = MenuItem.find("restaurant.id = ?", id).fetch();
		renderArgs.put("items", items);
		render();
	}

	public static void showOrders(Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			List<Order> orders = Order.find("order by orderCreated desc")
					.fetch();
			renderArgs.put("orders", orders);
		} else {
			List<Order> orders = Order.find(
					"restaurant.id = ? order by orderCreated desc", id).fetch();
			renderArgs.put("orders", orders);
		}
		render();
	}

	public static void showRestaurants(Long id) {
		List<Restaurant> restaurants = null;
		if (id == null) {
			restaurants = Restaurant.findAll();
		} else {
			restaurants = Restaurant.find("city.id = ?", id).fetch();
		}
		renderArgs.put("restaurants", restaurants);
		renderArgs.put("cities", City.find("display = ?", true).fetch());
		render();
	}

	public static void showUsers() {

        render();
	}

	public static void showSettings() {
		List<SystemSetting> settings = SystemSetting.findAll();
		renderArgs.put("settings", settings);
		render();
	}

	public static void addType(RestaurantCategory cat) {
		System.out.println("cat >>>> " + cat.id);
		if (cat.categoryDisplayNameUA == null) {
			renderArgs.put("thankmessage", "Please create new one.");
			render();
		}
		cat.create();
		renderArgs.put("thankmessage", "Sucessfuly added : "
				+ cat.categoryDisplayNameUA);
		render();
	}

	public static void addCategory(MenuItemGroup group, Long id) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null) {
			renderArgs.put("message", "Please create new one.");
			render();
		}

		Restaurant r = Restaurant.findById(id);
		group.restaurant = r;//do something
		group.save();        //
		renderArgs.put("message", "Sucessfuly added : " + group.name);
		render();
	}

	public static void editCategory(Long id) {
		notFoundIfNull(id);
		MenuItemGroup group = MenuItemGroup.findById(id);
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("group", group);
		renderArgs.put("restaurants", restaurants);
		renderTemplate("Admin/addCategory.html");
	}

	public static void addMenuItem(MenuItem item, Long id,
			Long groupid) {
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		List<MenuItemGroup> groups = MenuItemGroup.findAll();
		renderArgs.put("groups", groups);
		if (item == null || id == null || groupid == null) {
			renderArgs.put("message", "Please create new one.");
			render();
		}
		item.menuItemCreated = new Date();
		item.restaurant = Restaurant.findById(id);
		item.menuItemGroup = MenuItemGroup.findById(groupid);
		item.create();
		renderArgs.put("message", "Created!");
		render();
	}
	public static void editMenuItem(Long id){
		MenuItem item = MenuItem.findById(id);

		renderArgs.put("group", item.menuItemGroup.id);
		renderArgs.put("id", item.restaurant.id);
		renderArgs.put("item", item);
		render("Admin/addMenuItem.html");
	}

	public static void addCity(City city) {
		System.out.println("name =>" + city.cityNameUA + "; display =>"
				+ request.headers.toString());
		if (city.cityNameUA == null) {
			render();
		}
		city.save();
		render();
	}

	public static void editCity(Long id) {
		notFoundIfNull(id);
		City city = City.findById(id);
		notFoundIfNull(city);
		renderArgs.put("city", city);
		renderTemplate("Admin/addCity.html");
	}

	public static void editType(Long id) {
		notFoundIfNull(id);
		RestaurantCategory cat = RestaurantCategory.findById(id);
		notFoundIfNull(id);
		renderArgs.put("cat", cat);
		renderTemplate("Admin/addType.html");
	}

	public static void addRestaurant(Restaurant restaurant, Long catid,
			Long cityid, String openfrom, String opento, String barmanlogin,
			String barmanpwd) {
		
		List<City> cities = City.findAll();
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		renderArgs.put("cities", cities);
		renderArgs.put("types", types);
		if (cityid == null) {
			renderArgs.put("errormessage", "no cityid");
			render();
		}
		City city = City.findById(cityid);
		// notFoundIfNull(city);
		RestaurantCategory type = RestaurantCategory.findById(catid);
		RestaurantBarman b = null;
		if (restaurant.id != null) {
			Restaurant r = Restaurant.findById(restaurant.id);
			r.desc = restaurant.desc;
			r.showOnIndex = restaurant.showOnIndex;
			r.title = restaurant.title;
			r.twoLetters = restaurant.twoLetters;
			restaurant = r;
			b = RestaurantBarman.find("restaurant = ?", restaurant).first();
			
		}else {
			b = new RestaurantBarman();
			restaurant.device = new RestaurantDevice();
			restaurant.device.deviceActivatedDate = new Date();
			restaurant.device.status = DeviceStatus.ACTIVE;
			restaurant.device.lastPing = new Date(0);
			restaurant.device.save();
			b.joinDate = new Date();
		}
		b.login = barmanlogin;
		b.usr_name = barmanlogin;
		b.password = barmanpwd;
		b.restaurant = restaurant;
		
		restaurant.city = city;
		restaurant.category = type;
		List<Model> instanses = new ArrayList<Model>();
		if (restaurant.workHours.regularDays.isEmpty()) {
			instanses.add(restaurant.workHours);
			for (DayType dtype : DayType.values()){
				RegularDay day = new RegularDay();
				day.from = openfrom;
				day.to = opento;
				day.dayType = dtype;
				day.root = restaurant.workHours;
				instanses.add(day);
			}
		} else {
			for (RegularDay day : restaurant.workHours.regularDays) {
				day.from = openfrom;
				day.to = opento;
				day.save();
			}
		}
		for (Model i : instanses){
			i.save();
		}
		restaurant.save();
		b.save();
		render();

	}

	public static void deleteCity() {

		index();
	}

	public static void editRestaurant(Long id) {
		notFoundIfNull(id);
		Restaurant restaurant = Restaurant.findById(id);
		renderArgs.put("restaurant", restaurant);
		renderArgs.put("catid", restaurant.category.id);
		renderArgs.put("cityid", restaurant.city.id);
		//renderArgs.put("openfrom", restaurant.workHours.regularDays.iterator().next().from);
		//renderArgs.put("opento", restaurant.workHours.regularDays.iterator().next().to);
		RestaurantBarman b = RestaurantBarman.find("restaurant = ?",restaurant).first();
		renderArgs.put("barmanlogin", b.login);
		renderArgs.put("barmanpwd", b.password);
		List<City> cities = City.findAll();
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		renderArgs.put("cities", cities);
		renderArgs.put("types", types);
		renderTemplate("Admin/addRestaurant.html");
	}

	public static void deleteRestaurant(Long id) {
		notFoundIfNull(id, "missing argument");
		Restaurant restaurant = Restaurant.findById(id);
		notFoundIfNull(restaurant, "restaraunt not found");
		flash.put(
				"errormessage",
				String.format(
						"cannot delete restaurant '%s[%s]' because there is some orders assigned",
						restaurant.title, restaurant.id));
		index();
	}
	public static void addComp(Long id, String name, String descr, Integer price){
		MenuItem item = MenuItem.findById(id);
		MenuItemComponent component = new MenuItemComponent();
		component.itm_name = name;
		component.itm_price = price;
		component.itm_desc = descr;
		component.itm_root = item;
		if (item.showComponents == false)
		{
			item.showComponents = true;
			item.save();
		}
		component.save();
		editMenuItem(item.id);
		
	}
	public static void deleteComponent(Long id){
		MenuItemComponent component = MenuItemComponent.findById(id);
		component.delete();
	}
	public static void uploadLogo(Long id, File logo) throws FileNotFoundException{
		List<Restaurant>restaurants= Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id == null){
			render();
		}
		Restaurant restaurant = Restaurant.findById(id);

		restaurant.logo = new Blob();
		restaurant.logo.set(new FileInputStream(logo), MimeTypes.getContentType(logo.getName()));
		restaurant.save();
		
	}
    public static void showLastPing(){
        List<Restaurant> allRestaurants = Restaurant.find("deleted = ? order by showOnIndex asc", false).fetch();
        renderArgs.put("restaurants",allRestaurants );
        render();
=======

    @Inject
    private static GeoService geoService;
    @Inject
    private static RestaurantService restaurantService;
    @Inject
    private static SystemService sysService;
    @Inject
    private static OrderService orderService;


    public static void index() {
        render();
    }

    public static void showCities() {
        List<City> cities = geoService.getAllCities();
        render(cities);
    }

    public static void showTypes() {
        List<RestaurantCategory> types = restaurantService.getAllCategories();
        render(types);
    }

    public static void showCategories() {
        List<MenuItemGroup> categories = restaurantService.getAllMenuItemGroups();
        render(categories);
    }

    public static void showMenuItems(Integer id) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("restaurants", restaurants);
        if (id == null) {
            id = restaurants.get(0).id;
        }
        List<MenuItem> items = restaurantService.getAllMenuItemsFor(id);
        renderArgs.put("items", items);
        render();
    }

    public static void showOrders(Long id) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("restaurants", restaurants);
        if (id == null) {
            List<Order> orders = orderService.getAllOrdersOrderedByCreationDate();
            renderArgs.put("orders", orders);
        } else {
            List<Order> orders = orderService.getOrdersOrderedByCreationDateFor(id);
            renderArgs.put("orders", orders);
        }
        render();
    }

    public static void showRestaurants(Long id) {
        List<Restaurant> restaurants = null;
        if (id == null) {
            restaurants = restaurantService.getAllRestaurants();
        } else {
            restaurants = geoService.getRestsByCity(id);
        }
        renderArgs.put("restaurants", restaurants);
        renderArgs.put("cities", geoService.getAllCities());
        render();
    }

    public static void showUsers() {

    }

    public static void showSettings() {
        List<SystemSetting> settings = sysService.findAllSystemSettings();
        renderArgs.put("settings", settings);
        render();
    }

    public static void addType(RestaurantCategory cat) {
        if (cat.categoryDisplayNameUA == null) {
            renderArgs.put("thankmessage", "Please create new one.");
            render();
        }
        restaurantService.insertCategory(cat);
        renderArgs.put("thankmessage", "Sucessfuly added : "
                + cat.categoryDisplayNameUA);
        render();
    }

    public static void addCategory(MenuItemGroup group, Integer id) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("restaurants", restaurants);
        if (id == null) {
            renderArgs.put("message", "Please create new one.");
            render();
        }
        group.restaurant_Id = id;
        restaurantService.insertMenuGroup(group);
        renderArgs.put("message", "Sucessfuly added : " + group.name);
        render();
    }

    public static void editCategory(Long id) {
        notFoundIfNull(id);
        MenuItemGroup group = restaurantService.getMenuGroupById(id);
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("group", group);
        renderArgs.put("restaurants", restaurants);
        renderTemplate("Admin/addCategory.html");
    }

    public static void addMenuItem(MenuItem item, Integer id, Integer groupid) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("restaurants", restaurants);
        List<MenuItemGroup> groups = restaurantService.getAllMenuItemGroups();
        renderArgs.put("groups", groups);
        if (item == null || id == null || groupid == null) {
            renderArgs.put("message", "Please create new one.");
            render();
        }
        item.menuItemCreated = new LocalDateTime();
        item.restaurantId = id;
        item.menuItemGroupId = groupid;
        restaurantService.insertMenuItem(item);
        renderArgs.put("message", "Created!");
        render();
    }

    public static void editMenuItem(Long id) {
        MenuItem item = restaurantService.getMenuItemById(id);
        renderArgs.put("group", item.menuItemGroupId);
        renderArgs.put("id", item.restaurantId);
        renderArgs.put("item", item);
        render("Admin/addMenuItem.html");
    }

    public static void addCity(City city) {
        if (city == null || city.cityNameKey == null) {
            render();
        }
        if (city.city_id !=null){
            geoService.updateCity(city);
        } else {
            geoService.insertCity(city);
        }
        render();
    }

    public static void editCity(Long id) {
        notFoundIfNull(id);
        City city = geoService.getCityById(id);
        notFoundIfNull(city);
        renderArgs.put("city", city);
        renderTemplate("Admin/addCity.html");
    }

    public static void editType(Long id) {
        notFoundIfNull(id);
        RestaurantCategory cat = restaurantService.getRestaurantCategoryById(id);
        notFoundIfNull(id);
        renderArgs.put("cat", cat);
        renderTemplate("Admin/addType.html");
    }

    public static void addRestaurant(Restaurant restaurant, Integer catid,
                                     Long cityid, String openfrom, String opento, String barmanlogin,
                                     String barmanpwd) {

        List<City> cities = geoService.getVisibleCities();
        List<RestaurantCategory> types = restaurantService.getAllCategories();
        renderArgs.put("cities", cities);
        renderArgs.put("types", types);
        if (cityid == null) {
            renderArgs.put("errormessage", "no cityid");
            render();
        }
        // notFoundIfNull(city);

        restaurant.deviceLogin = barmanlogin.trim();
        restaurant.devicePassword = barmanpwd.trim();

        restaurant.city_id = cityid;
        restaurant.category_id = catid;
        WorkHours workHours = restaurantService.getWorkHours(restaurant);
        if (workHours == null) {
            workHours = new WorkHours(openfrom, opento);
        } else {
            workHours.updateAll(openfrom, opento);
        }
        workHours = restaurantService.insertWorkHours(workHours);
        restaurantService.setWorkHoursFor(restaurant, workHours);
        restaurantService.insertRestaurant(restaurant);
        render();
    }

    public static void deleteCity() {

        index();
    }

    public static void editRestaurant(Long id) {
        notFoundIfNull(id);
        Restaurant restaurant = restaurantService.getById(id);
        renderArgs.put("restaurant", restaurant);
        renderArgs.put("catid", restaurant.category.id);
        renderArgs.put("cityid", restaurant.city.city_id);
        // renderArgs.put("openfrom",
        // restaurant.workHours.regularDays.iterator().next().from);
        // renderArgs.put("opento",
        // restaurant.workHours.regularDays.iterator().next().to);
        renderArgs.put("barmanlogin", restaurant.deviceLogin);
        renderArgs.put("barmanpwd", restaurant.devicePassword);
        List<City> cities = geoService.getVisibleCities();
        List<RestaurantCategory> types = restaurantService.getAllCategories();
        renderArgs.put("cities", cities);
        renderArgs.put("types", types);
        renderTemplate("Admin/addRestaurant.html");
    }

    public static void deleteRestaurant(Long id) {
        notFoundIfNull(id, "missing argument");
        restaurantService.deleteRestaurant(id);
        index();
    }

    public static void addComp(Long id, String name, String descr, Integer price) {
        MenuItem item = restaurantService.getMenuItemById(id);
        MenuItemComponent component = new MenuItemComponent();
        component.name = name;
        component.price = convertCentsToMoney(price);
        component.description = descr;
        component.menuItemId = item.id;
        restaurantService.insertItemComponent(component);
        editMenuItem(item.id);

    }

    public static void deleteComponent(Long id) {
        restaurantService.deleteMenuItemComponent(id);

    }

    public static void uploadLogo(Long id, File logo)
            throws FileNotFoundException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        renderArgs.put("restaurants", restaurants);
        if (id == null) {
            render();
        }
        restaurantService.setNewLogo(id, logo);
>>>>>>> master

    }

}
