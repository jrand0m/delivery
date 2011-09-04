/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Order;
import models.OrderItem;

import apimodels.Job;
import apimodels.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import play.mvc.Controller;

/**
 * @author Mike
 *
 */
public class API extends Controller {
        public static void fetchJobs(Integer id){
            List<Order> orders = Order.find("client = ?", id).fetch();
            List<Job> jobs = new ArrayList<Job>(orders.size());
            for (Order order : orders){
                Job job = new Job();
                job.id = order.shortHandId();
                job.status="GOOD";
                for (OrderItem oi : order.items){
                  //TODO get item details repacked to job
                  //TODO add menu ingradients subitems
                }
            }
            renderJSON(jobs);
        }
        public static void push(String message){
            
            
        }
}
