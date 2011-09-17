/**
 * 
 */
package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.api.Job;
import models.api.MenuItem;
import play.Logger;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enumerations.OrderStatus;

/**
 * @author Mike
 * 
 */
public class API extends Controller {
    public static void g(@Required Integer id) {
        if (id == null) {
            notFound();
            return;
        }
        Restaurant restaurant = Restaurant.findById(new Long(id));
        List<Order> orders = Order.find("restaurant = ? and orderStatus = ? ",
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
        renderJSON(jobs);
    }

    public static void p(String message) {

    }

    public static void c(String m) {
        Logger.info("Recieved message <%s>", m);

        Gson g = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Map<String, String> h = g.fromJson(m.trim(), type);
        List<String> list = new ArrayList<String>();
        for (String key : h.keySet()) {
            String val = h.get(key);
            Order o = Order.findByShortId(key);
            if (o == null) {
                Logger.error("Uexisting obj");
                
                //TODO what to do when unexisting object ? skip
                continue;
                // error();
                // return ;
            }

            Job job = new Job();
            job.id = o.getShortHandId();
            for (OrderItem oi : o.items) {
                job.list.add(new MenuItem(oi));
            }
            String jsonedObj = g.toJson(job);
            Logger.info("key: %s;  jsoned repr: %s; md5: %s", key, jsonedObj,
                    Codec.hexMD5(jsonedObj));
            if (!Codec.hexMD5(jsonedObj).equalsIgnoreCase(val)) {
                list.add(key);
                continue;
            }
            o.orderStatus = OrderStatus.RECIEVED;
            o.save();
            // TODO maby we need to postprocess orders wich we recived. logging to devices log ! change order status to recieved
        }
        renderJSON(list);

    }

}
