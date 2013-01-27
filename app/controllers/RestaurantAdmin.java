package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//todo: @With(Secure.class)//@Check(UserRoles.RESTAURANT_ADMIN)
public class RestaurantAdmin extends Controller {

    //TODO:@Before
    public static Result _prepare() {
/*		String login = Security.connected();
		RestaurantAdministration user = RestaurantAdministration.find(User.HQL.BY_LOGIN, login).first();
		notFoundIfNull(user);
		renderArgs.put("user", user);
		renderArgs.put("login", login);*/
        return TODO;
    }


    public static Result summary() {
        /*List<Order> lastOrders = null;
          List<Comment> lastComments = null;
          Restaurant restaurant = ((RestaurantAdministration)renderArgs.get("user")).restaurant;
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.HOUR_OF_DAY, 0);
          cal.set(Calendar.MINUTE, 0);
          List<Order> orders = Order.find(Order.HQL.BY_RESTAURANT_AND_STATUS_AND_AFTER_DATE, restaurant, OrderStatus.COOKED, cal.getTime()).fetch();
          renderArgs.put("ordersCount", orders.size());
          BigDecimal todaysRevenue = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
          for (Order o : orders){
              todaysRevenue = todaysRevenue.add(new  BigDecimal (String.valueOf(o.totalMenuPrice - o.totalMenuPrice * o.restaurantDiscount)));
          }
           Query q  =Order.find(Order.HQL.BY_RESTAURANT_AND_STATUS_ORDERBY_ACCEPTED_DESC).query;
          lastOrders = q.setMaxResults(5).setParameter(0, restaurant).setParameter(1, new OrderStatus[]{OrderStatus.COOKED, OrderStatus.ACCEPTED, OrderStatus.DELIVERING,OrderStatus.DELIVERED}).getResultList();
          lastComments = Comment.find(Comment.HQL.BY_RESTAURANT_ORDERBY_DATE_DESC, params).fetch(5);
          renderArgs.put("todaysRevenue", todaysRevenue.toString());
          renderArgs.put("lastComments", lastComments);
          renderArgs.put("lastOrders", lastOrders);*/
        return TODO;
    }

    public static Result showMenu() {
        return TODO;
    }

    public static Result showReports() {
        return TODO;
    }

    public static Result showProfile() {
        return TODO;
    }

    /*---- PROFILE API ---- */
    public static Result editProfile() {
              return TODO;
    }

    /*------ PROFIE API END ------*/
    public static Result showEvents() {
        return TODO;
    }

    public static Result showInvoices() {
        return TODO;
    }

    public static Result showShop() {
        return TODO;
    }


    /*UTIL METHODS*/
    public static Result getSummaryChartData() {

        List<ChartDataHolder> chartData = new ArrayList<ChartDataHolder>(); /*
		List<Order> orders = Order.find(Order.HQL.BY_RESTAURANT_AND_STATUS, ((RestaurantAdministration)renderArgs.get("user")).restaurant, OrderStatus.DELIVERED).fetch();
		for (Order order:orders){
			ChartDataHolder holder = new ChartDataHolder(order.orderCooked, String.valueOf(order.totalMenuPrice - (order.totalMenuPrice* order.restaurantDiscount)));
			chartData.add(holder);
		}*/
        return TODO;//renderTemplate("RestaurantAdmin/summaryChartData.js", chartData);
    }

    /*helper classes */
    public static class ChartDataHolder {
        public String date;
        public String value;

        ChartDataHolder(Date date, String value) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy,MM,dd");
            this.date = sdf.format(date);
            this.value = value;
        }
    }

}
