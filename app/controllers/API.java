/**
 * 
 */
package controllers;

import helpers.OrderUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
@Check({ RestaurantBarman.class, CourierUser.class })
public class API extends Controller {
	// TODO extract to Order HQL
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN = Order.FIELDS.RESTAURANT
			+ " = ? and " + Order.FIELDS.ORDER_STATUS + " in (?,?,?) ";
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN_FROM = Order.FIELDS.RESTAURANT
			+ " = ? and "
			+ Order.FIELDS.ORDER_STATUS
			+ " in (?) and "
			+ Order.FIELDS.ORDER_CONFIRMED + " > ?";
	private static final String JPA_BY_CITY_AND_ORDER_STATUS_IN = Order.FIELDS.RESTAURANT
			+ "."
			+ Restaurant.FIELDS.RESTAURANT_CITY
			+ " = ? AND "
			+ "(("
			+ Order.FIELDS.ORDER_STATUS
			+ " = ?) "
			+ "OR ("
			+ Order.FIELDS.ORDER_STATUS
			+ " in (?,?,?,?) AND "
			+ Order.FIELDS.CONFIRMED_COURIER + " = ?))";
	private static final String JPA_BY_CITY_AND_ORDER_STATUS_IN_FROM = Order.FIELDS.RESTAURANT
			+ "."
			+ Restaurant.FIELDS.RESTAURANT_CITY
			+ " = ? AND "
			+ "(("
			+ Order.FIELDS.ORDER_STATUS
			+ " in (?,?)) "
			+ "OR ("
			+ Order.FIELDS.ORDER_STATUS
			+ " in (?,?,?,?) AND "
			+ Order.FIELDS.CONFIRMED_COURIER
			+ " = ?)) "
			+ " and "
			+ Order.FIELDS.UPDATED + " > ?";

	public static void g(Long from) {
		User user = (User) renderArgs.get("user");
		if (user instanceof RestaurantBarman) {
			processGet((RestaurantBarman) user, from);
		}
		if (user instanceof CourierUser) {
			processGet((CourierUser) user, from);
		}
		error();
	}

	private static void processGet(RestaurantBarman user, Long from) {
		Restaurant restaurant = user.restaurant;
		List<Order> orders;
		if (from != null) {
			Date date = new Date(from);
			orders = Order.find(JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN_FROM,
					restaurant, OrderStatus.CONFIRMED, date).fetch();
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
			job.price = order.getMenuTotal();
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

	static private void processGet(CourierUser user, Long from) {
		City city = user.city;
		List<Order> orders;
		if (from != null) {
			Date date = new Date(from);
			orders = Order.find(JPA_BY_CITY_AND_ORDER_STATUS_IN_FROM, city,
					OrderStatus.SENT, OrderStatus.CONFIRMED,
					OrderStatus.ACCEPTED, OrderStatus.DELIVERING,
					OrderStatus.COOKED, OrderStatus.DELIVERED, user,
					date).fetch();
		} else {
			orders = Order.find(JPA_BY_CITY_AND_ORDER_STATUS_IN, city,
					OrderStatus.SENT, OrderStatus.CONFIRMED,
					OrderStatus.ACCEPTED, OrderStatus.DELIVERING,
					OrderStatus.COOKED, user).fetch();
		}

		Logger.info("Found %d orders", orders.size());
		List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
		for (Order order : orders) {
			CaffeJobsList job = new CaffeJobsList();
			job.id = order.getShortHandId();
			
			if (order.orderStatus.equals(OrderStatus.CONFIRMED)
					&& (order.confirmedCourier != null)
					&& !order.confirmedCourier.getId().equals(user.getId())) {
				job.status = "ALREADY_CONFIRMED";
			} else {
				job.status = order.orderStatus.toString();
			}

			job.price = order.getMenuTotal();
			job.customerPrice = order.getGrandTotal();
			job.paymentStatus = order.paymentStatus.toString();
			job.time = order.updated.getTime();
			job.timeToFinish = order.orderStatus == OrderStatus.ACCEPTED ? order.orderPlanedCooked
					.getTime() - System.currentTimeMillis()
					: null;
			if (order.deliveryAddress == null) {
				Logger.warn("No delivery address for order id %s",
						order.shortHandId);
				job.additionalInfo = null;
			} else {
				job.additionalInfo = order.deliveryAddress.additionalInfo;
			}
			job.from = order.restaurant.addressToString();
			job.to = order.deliveryAddress == null ? "none"
					: order.deliveryAddress.toString();
			job.timeToDelivered = order.orderPlanedDeliveryTime == null ? 0
					: order.orderPlanedDeliveryTime.getTime();
			for (OrderItem oi : order.items) {
				job.list.add(new MenuItem(oi));
			}
			job.phone = order.orderOwner.phoneNumber;
			jobs.add(job);
		}
		renderJSON(jobs);
	}

	public static void p(String message) {
		notFoundIfNull(message);
		Logger.debug("p in message = %s", message);
		Gson g = new Gson();
		PushMessage p = g.fromJson(message, PushMessage.class);
		if (p.id == null || p.id.length() < 8) {
			notFound();
		}
		User user = (User) renderArgs.get("user");
		if (user instanceof RestaurantBarman) {
			processPush((RestaurantBarman) user, p);
		}
		if (user instanceof CourierUser) {
			processPush((CourierUser) user, p);
		}
		error();
	}

	static private void processPush(CourierUser user, PushMessage message) {
		Order order = Order.find(Order.HQL.BY_SHORT_ID, message.id).first();
		notFoundIfNull(order);
		Logger.debug("p found order id = %s", order.id);
		switch (OrderStatus.convert(message.status)) {
		case ACCEPTED:
		case CONFIRMED:
			order.orderStatus = OrderStatus.CONFIRMED;
			order.orderConfirmed = new Date();
			order.confirmedCourier = user;
			order.orderPlanedDeliveryTime = new Date(message.time * 1000 * 60);
			break;
		case DELIVERED:
			order.orderStatus = OrderStatus.DELIVERED;
			order.orderDelivered = new Date();
			break;
		case DECLINED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			// FIXME see how long message can be
			order.declineMessage = message.comment; // != null ?
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

	private static void processPush(RestaurantBarman user, PushMessage message) {
		Order order = Order.find(Order.HQL.BY_SHORT_ID, message.id).first();
		notFoundIfNull(order);
		switch (OrderStatus.convert(message.status)) {
		case COOKED:
			order.orderStatus = OrderStatus.COOKED;
			order.orderCooked = new Date();
			break;
		case ACCEPTED:
			order.orderStatus = OrderStatus.ACCEPTED;
			order.orderAccepted = new Date();
			order.orderPlanedCooked = new Date(System.currentTimeMillis()
					+ OrderUtils.convertToMinutes(message.time));
			order.orderPlanedDeliveryTime = new Date(
					order.orderPlanedCooked.getTime()
							+ order.orderPlanedDeliveryTime.getTime());
			break;
		case DECLINED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			// FIXME see how long message can be
			order.declineMessage = message.comment; // != null ?
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