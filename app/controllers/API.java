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

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.api.CaffeJobsList;
import models.api.MenuItem;
import models.api.PushMessage;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;

import com.google.gson.Gson;

import enumerations.OrderStatus;

/**
 * @author Mike
 * 
 */
public class API extends Controller {
	// TODO extract to Order HQL
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN = Order.FIELDS.RESTAURANT
			+ " = ? and " + Order.FIELDS.ORDER_STATUS + " in (?,?,?) ";
	private static final String JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN_FROM = JPA_BY_RESTAURANT_AND_ORDER_STATUS_IN
			+ " and " + Order.FIELDS.ORDER_CONFIRMED + " > ?";

	public static void g(@Required Integer id, Long from) {
		Logger.debug("g in id = %s", id);
		if (id == null) {
			notFound();
			return;
		}
		Restaurant restaurant = Restaurant.findById(new Long(id));
		if (restaurant == null) {
			notFound();
		}
		List<Order> orders;

		// XXX Collection<Enum<OrderStatus>> statuses = new
		// ArrayList<OrderStatus>(){{
		// add();
		// add(OrderStatus.COOKED);
		// add();
		// }};

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

	public static void p(String message) {

		Logger.debug("p in message = %s", message);
		Gson g = new Gson();
		PushMessage p = g.fromJson(message, PushMessage.class);
		if (p.id == null || p.id.length() < 8) {
			notFound();
		}

		Order order = Order.find(Order.HQL.BY_SHORT_ID_OR_LIKE_FULL_ID, p.id,
				p.id + "%").first();
		notFoundIfNull(order);
		switch (OrderStatus.convert(p.status)) {
		case COOKED:
			order.orderStatus = OrderStatus.COOKED;
			order.orderCooked = new Date();
			break;
		case ACCEPTED:
			order.orderStatus = OrderStatus.ACCEPTED;
			order.orderAccepted = new Date();
			order.orderPlanedCooked = new Date(System.currentTimeMillis()
					+ p.time * 60 * 1000);
			break;
		case DECLINED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			// FIXME see how long message can be
			order.declineMessage = p.comment; // != null ?
			// p.comment.substring(0, 250) :
			// "" ;
			break;
		case DELIVERING:
			order.orderStatus = OrderStatus.DELIVERING;
			order.orderTaken = new Date();
			break;
		default:
			notFound();
		}
		order.save();
		ok();
	}

}
