/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Client;
import models.Order;
import models.OrderItem;
import models.api.Job;
import models.api.MenuItem;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import play.mvc.Controller;

/**
 * @author Mike
 *
 */
public class API extends Controller {
        public static void g(Integer id){
            Client client = Client.findById(new Long(id));
            List<Order> orders = Order.find("client = ?", client).fetch();
            List<Job> jobs = new ArrayList<Job>(orders.size());
            for (Order order : orders){
                Job job = new Job();
                job.id = order.shortHandId();
                job.status=order.orderStatus.toString();
                for (OrderItem oi : order.items){
                    job.list.add(new MenuItem(oi));
                }
            }
            renderJSON(jobs);
        }
        public static void p(String message){
            
            
        }
}
