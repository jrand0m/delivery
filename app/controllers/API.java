/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Courier;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.dto.intern.CaffeJobsList;
import models.dto.intern.MenuItem;
import models.dto.intern.PushMessage;
import models.geo.City;
import models.users.CourierUser;
import models.users.RestaurantBarman;
import models.users.User;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;

import annotations.Check;

import com.google.gson.Gson;

import enumerations.OrderStatus;

/**
 * @author Mike
 * 
 */
@With(Secure.class)
public class API extends Controller {
	// TODO extract to Order HQL
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN = Order.FIELDS.RESTAURANT
			+ " = ? and " + Order.FIELDS.ORDER_STATUS + " in (?,?,?) ";
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN_FROM = JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN
			+ " and " + Order.FIELDS.UPDATED + " > ?";
	private static final String JPA_BY_CITY_AND_ORDER_STATUS_IN = Order.FIELDS.RESTAURANT + "."+Restaurant.FIELDS.RESTAURANT_CITY+" = ?";
	private static final String JPA_BY_CITY_AND_ORDER_STATUS_IN_FROM = JPA_BY_CITY_AND_ORDER_STATUS_IN +" and " + Order.FIELDS.UPDATED + " > ?";
	
	@Check(RestaurantBarman.class)
	public static void processCaffeGet ( Long from){
		RestaurantBarman user = (RestaurantBarman) renderArgs.get("user");
		Restaurant restaurant = user.restaurant;
		List<Order> orders;
		if (from != null) {
			orders = Order.find(JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN_FROM,
					restaurant, OrderStatus.ACCEPTED, OrderStatus.COOKED,
					OrderStatus.CONFIRMED, new Date(from)).fetch();
		} else {
			orders = Order.find(JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN,
					restaurant, OrderStatus.ACCEPTED, OrderStatus.COOKED,
					OrderStatus.CONFIRMED).fetch();
		}

		Logger.info("Found %d orders", orders.size());
		List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
		for (Order order : orders) {
			CaffeJobsList job = new CaffeJobsList();
			job.id = order.getShortHandId();
			job.status = order.orderStatus.toString();
			job.price = order.getGrandTotal();
			job.paymentStatus = order.paymentStatus.toString();
			job.time = order.orderConfirmed.getTime();
			job.timeToFinish = order.orderStatus == OrderStatus.ACCEPTED ? order.orderPlanedCooked
					.getTime() - System.currentTimeMillis()
					: null;
			for (OrderItem oi : order.items) {
				job.list.add(new MenuItem(oi));
			}
			jobs.add(job);
		}
		restaurant.device.lastPing = new Date();
		restaurant.device.save();
		renderJSON(jobs);
	}
	@Check(CourierUser.class)
	public static void processCourierGet (Long from){
		CourierUser user = (CourierUser) renderArgs.get("user");
		City city = user.city;
		List<Order> orders;
		if (from != null) {
			orders = Order.find(JPA_BY_CITY_AND_ORDER_STATUS_IN_FROM,
					city, OrderStatus.SENT, OrderStatus.DELIVERING,
					OrderStatus.COOKED, new Date(from)).fetch();
		} else {
			orders = Order.find(JPA_BY_CITY_AND_ORDER_STATUS_IN,
					city, OrderStatus.SENT, OrderStatus.DELIVERING,
					OrderStatus.COOKED).fetch();
		}

		Logger.info("Found %d orders", orders.size());
		List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
		for (Order order : orders) {
			CaffeJobsList job = new CaffeJobsList();
			job.id = order.getShortHandId();
			job.status = order.orderStatus.toString();
			job.price = order.getGrandTotal();
			job.paymentStatus = order.paymentStatus.toString();
			job.time = order.orderConfirmed.getTime();
			job.timeToFinish = order.orderStatus == OrderStatus.ACCEPTED ? order.orderPlanedCooked
					.getTime() - System.currentTimeMillis()
					: null;
			for (OrderItem oi : order.items) {
				job.list.add(new MenuItem(oi));
			}
			jobs.add(job);
		}
		renderJSON(jobs);
	}

	@Check(CourierUser.class)
	public static void processCourierPush( String message){
		CourierUser user = (CourierUser) renderArgs.get("user");
		Logger.debug("p in message = %s", message);
		Gson g = new Gson();
		PushMessage pushMessage = g.fromJson(message, PushMessage.class);
		if (pushMessage.id == null || pushMessage.id.length() < 8) {
			notFound();
		}
		Order order = Order.find(Order.HQL.BY_SHORT_ID, pushMessage.id).first();
		notFoundIfNull(order);
		Logger.debug("p found order id = %s", order.id);
		switch (OrderStatus.convert(pushMessage.status)) {
		case CONFIRMED:
			order.orderStatus = OrderStatus.CONFIRMED;
			order.orderConfirmed = new Date();
			break;
		case DELIVERED:
			order.orderStatus = OrderStatus.DELIVERED;
			order.orderDelivered = new Date();
			break;
		case DECLINED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			// FIXME see how long message can be
			order.declineMessage = pushMessage.comment; // != null ?
			// p.comment.substring(0, 250) :
			// "" ;
			break;
		default:
			Logger.debug("p not found corresponding status ");			
			notFound();
		}
		order.save();
		ok();
	}
	
	@Check(RestaurantBarman.class)
	public static void processCaffePush (String message){
		notFoundIfNull(message);
		RestaurantBarman user = (RestaurantBarman) renderArgs.get("user");
		Logger.debug("p in message = %s", message);
		Gson g = new Gson();
		PushMessage pushMessage = g.fromJson(message, PushMessage.class);
		if (pushMessage.id == null || pushMessage.id.length() < 8) {
			notFound();
		}
		Order order = Order.find(Order.HQL.BY_SHORT_ID, pushMessage.id).first();
		notFoundIfNull(order);
		switch (OrderStatus.convert(pushMessage.status)) {
		case COOKED:
			order.orderStatus = OrderStatus.COOKED;
			order.orderCooked = new Date();
			break;
		case ACCEPTED:
			order.orderStatus = OrderStatus.ACCEPTED;
			order.orderAccepted = new Date();
			order.orderPlanedCooked = new Date(System.currentTimeMillis()
					+ pushMessage.time * 60 * 1000);
			break;
		case DECLINED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			// FIXME see how long message can be
			order.declineMessage = pushMessage.comment; // != null ?
			// p.comment.substring(0, 250) :
			// "" ;
			break;
		case DELIVERING:
			order.orderStatus = OrderStatus.DELIVERING;
			order.orderTaken = new Date();
			break;
		default:
			Logger.debug("p not found corresponding status ");			
			notFound();
		}
		order.save();
		user.restaurant.device.lastPing = new Date();
		user.restaurant.device.save();
		ok();
	}
}