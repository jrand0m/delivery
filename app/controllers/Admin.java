package controllers;

import java.util.Date;
import java.util.List;

import models.MenuItem;
import models.MenuItemGroup;
import models.Order;
import models.Restaurant;
import models.RestaurantCategory;
import models.geo.City;
import models.users.SystemAdministrator;
import annotations.Check;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Check(SystemAdministrator.class)
public class Admin extends Controller {
	public static void index(){
		render();
	}
	public static void showCities(){
		List<City> cities = City.findAll();
		render(cities);
	}
	public static void showTypes       (){
		List<RestaurantCategory> types = RestaurantCategory.findAll();
		render(types);
	}
	public static void showCategories  (){
		List<MenuItemGroup> categories = MenuItemGroup.findAll();
		render(categories);
	}
	public static void showMenuItems   (Long id){
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id==null){
			id = restaurants.get(0).id;
		}
		List<MenuItem> items = MenuItem.find("restaurant.id = ?", id).fetch();
		renderArgs.put("items", items);
		render();
	}
	public static void showOrders      (Long id){
		List<Restaurant> restaurants = Restaurant.findAll();
		renderArgs.put("restaurants", restaurants);
		if (id==null){
			List<Order> orders = Order.find("order by orderCreated desc").fetch();
			renderArgs.put("orders", orders);
		}else{
			List<Order> orders = Order.find("restaurant.id = ? order by orderCreated desc", id).fetch();
			renderArgs.put("orders", orders);
		}
		render();
	}
	public static void showRestaurants(Long id){
		List<Restaurant> restaurants = null;
		if (id == null ){
			restaurants = Restaurant.findAll();
		} else {
			restaurants = Restaurant.find("city.id = ?",id).fetch();
		}
		renderArgs.put("restaurants", restaurants);
		renderArgs.put("cities", City.find("display = ?", true).fetch());
		render();
	}
	public static void showUsers       (){
		
	}
	
	public static void addType         (RestaurantCategory cat){
		if (cat == null){
			renderArgs.put("message", "Please create new one.");
			render();
		}
		cat.create();
		renderArgs.put("message", "Sucessfuly added : "+ cat.categoryDisplayNameUA);
		render();
	}
	public static void addCategory     (MenuItemGroup group, Restaurant id){
		List<Restaurant>restaurants = Restaurant.findAll();
		renderArgs.put("restaurants",restaurants);
		if (group==null || id ==null){
			renderArgs.put("message", "Please create new one.");
			render();
		}
		Restaurant r = Restaurant.findById(id);
		group.restaurant = r;
		group.create();
		renderArgs.put("message", "Sucessfuly added : "+ group.name);
		render();
	}
	public static void addMenuItem     (MenuItem item, Restaurant id,MenuItemGroup groupid){
		List<Restaurant>restaurants = Restaurant.findAll();
		renderArgs.put("restaurants",restaurants);
		List<MenuItemGroup> groups = MenuItemGroup.findAll();
		renderArgs.put("groups",groups);
		if (item == null || id == null || groupid == null){
			renderArgs.put("message", "Please create new one.");
			render();
		}
		item.menuItemCreated = new Date();
		item.restaurant = Restaurant.findById(id);
		item.menuItemGroup = MenuItemGroup.findById(groupid);
		item.create();
		renderArgs.put("message", "Please create new one.");
		render();
	}
	public static void addCity(City city){
		if (city == null ){
			render();
		}
		
		
	}
	public static void addRestaurant(Restaurant restaurant, Long catid, Long cityid){	
	
	}
	
	public static void editCity(Long id, City city){
		
	}
	public static void deleteCity(){
		
		index();
	}
	public static void editRestaurant(Long id, Restaurant restaurant){
		
	}
	public static void deleteRestaurant(){
		
		flash.put("result", "cannot delete restaurant '' because there is some orders assigned");
		index();
	}
	
}
