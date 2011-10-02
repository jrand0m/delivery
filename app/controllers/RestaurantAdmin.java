package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Comment;
import models.Order;
import models.Restaurant;
import models.users.RestaurantAdministration;
import models.users.RestaurantUser;
import models.users.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import enumerations.OrderStatus;
import enumerations.UserRoles;

@With(Secure.class)
@Check(UserRoles.RESTAURANT_ADMIN)
public class RestaurantAdmin extends Controller {

	@Before
	public static void _prepare(){
		String login = Security.connected();
		RestaurantAdministration user = RestaurantAdministration.find(User.HQL.BY_LOGIN, login).first();
		notFoundIfNull(user);
		renderArgs.put("user", user);
		renderArgs.put("login", login);
	}
	
	
	public static void summary() {
		
		List<Order> lastOrders = null;
		List<Comment> lastComments = null;
		
		renderArgs.put("lastComments", lastComments);
		renderArgs.put("lastOrders", lastOrders);
		render();
	}

	public static void showMenu() {
		render();
	}

	public static void showReports() {
		render();
	}

	public static void showProfile() {
		render();
	}

	public static void showEvents() {
		render();
	}

	public static void showInvoices() {
		render();
	}

	public static void showShop() {
		render();
	}
	/*UTIL METHODS*/
	public static void getSummaryChartData(){
		
		List<ChartDataHolder> chartData = new ArrayList<ChartDataHolder>();
		List<Order> orders = Order.find(Order.HQL.BY_RESTAURANT_AND_STATUS, ((RestaurantAdministration)renderArgs.get("user")).restaurant, OrderStatus.DELIVERED).fetch();
		for (Order order:orders){
			ChartDataHolder holder = new ChartDataHolder(order.orderCooked, String.valueOf(order.totalMenuPrice - (order.totalMenuPrice* order.restaurantDiscount)));
			chartData.add(holder);
		}
		renderTemplate("RestaurantAdmin/summaryChartData.js", chartData);
	}
	/*helper classes */
	public static class ChartDataHolder {
		public String date;
		public String value;
		ChartDataHolder(Date date, String value){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd");
			this.date = sdf.format(date);
			this.value = value;
		}
	}

}
