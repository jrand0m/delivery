/**
 * 
 */
package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.api.Job;
import models.api.MenuItem;
import models.api.PushMessage;
import play.Logger;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enumerations.OrderStatus;
import enumerations.PushStatus;

/**
 * @author Mike
 * 
 */
public class API extends Controller {
	/**
     * 
     */
	private static final String BY_RESTAURANT_AND_ORDER_STATUS = Order.FIELDS.RESTAURANT
			+ " = ? and " + Order.FIELDS.ORDER_STATUS + " = ? ";

	public static void g(@Required Integer id) {
		Logger.debug("g in id = %s", id);
		if (id == null) {
			notFound();
			return;
		}
		Restaurant restaurant = Restaurant.findById(new Long(id));
		List<Order> orders = Order.find(BY_RESTAURANT_AND_ORDER_STATUS,
				restaurant, OrderStatus.SENT).fetch();
		Logger.info("Found %d orders", orders.size());
		List<Job> jobs = new ArrayList<Job>(orders.size());
		for (Order order : orders) {
			Job job = new Job();
			job.id = order.getShortHandId();
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
		if (p.id==null || p.id.length() <8){
			notFound();
		}
		
		Order order = Order.find(Order.HQL.BY_SHORT_ID_OR_LIKE_FULL_ID, p.id, p.id + "%").first();
		notFoundIfNull(order);
		switch (PushStatus.convert(p.status)){
		case READY:
			order.orderStatus = OrderStatus.COOKED;
			order.orderCooked = new Date();
			break;
		case INPROGRESS:
			order.orderStatus = OrderStatus.ACCEPTED;
			order.orderDate = new Date();
			order.orderPlanedCooked = new Date(System.currentTimeMillis()+p.time*60*1000);
			break;
		case REJECTED:
			order.orderStatus = OrderStatus.DECLINED;
			order.orderClosed = new Date();
			//FIXME see how long message can be 
			order.declineMessage = p.comment; //!= null ? p.comment.substring(0, 250) : "" ;
			break;
		case TAKEN:
			order.orderStatus = OrderStatus.DELIVERING;
			order.courierOrderRecieved = new Date();
			break;
		default:
			notFound();
		}
		order.save();
		ok();
	}
	
}
