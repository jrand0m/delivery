package controllers;

import models.Restaurant;
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
		
	}
	public static void addCity(City city){
		
	}
	public static void editCity(Long id, City city){
		
	}
	public static void deleteCity(){
		
		flash.put("result", "cannot delete city '' because there is some restaurants assigned");
		index();
	}
	public static void showCategories(){}
	
	public static void showRestaurants(Long cityid){
		
	}
	public static void addRestaurant(Restaurant restaurant, Long catid, Long cityid){
		
	}
	public static void editRestaurant(Long id, Restaurant restaurant){
		
	}
	public static void deleteRestaurant(){
		
		flash.put("result", "cannot delete restaurant '' because there is some orders assigned");
		index();
	}
	
}
