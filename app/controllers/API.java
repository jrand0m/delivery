package controllers;

import com.google.gson.Gson;
import enumerations.OrderStatus;
import enumerations.UserType;
import helpers.OrderUtils;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.dto.intern.CaffeJobsList;
import models.dto.intern.MenuItem;
import models.dto.intern.PushMessage;
import models.users.User;
import play.Logger;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.With;
import services.OrderService;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static helpers.OrderUtils.convertMoneyToCents;

/**
 * @author Mike
 */
@InjectSupport
@With(Secure.class)
public class API extends Controller {
    @Inject
    private static RestaurantService restaurantService;
    @Inject
    private static OrderService orderService;

    public static void g(Long from) {

        if (session.contains("_id")) {
            processGetAsRestaurant(session.get("_id"), from);
        }
        User user = (User) renderArgs.get("user");

        if (UserType.COURIER.equals(user.userType)) {
            processGet(user, from);
        }
        notFound();
    }

    private static void processGetAsRestaurant(String device, Long from) {

        List<Order> orders;
        if (from != null) {
            orders = restaurantService.getLastOrdersForDeviceFrom(device, from);
        } else {
            orders = restaurantService.getLastOrdersForDevice(device);
        }

        Logger.info("Found %d orders for agent %s", orders.size(), Security.connected());
        List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
        for (Order order : orders) {
            CaffeJobsList job = new CaffeJobsList();
            job.id = order.id;
            job.status = order.orderStatus.toString();

            job.price = convertMoneyToCents(order.getMenuTotal());
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
        restaurantService.updateDevicePing(device);
        renderJSON(jobs);
    }

    static private void processGet(User user, Long from) {
        List<Order> orders;
        if (from != null) {
            orders = orderService.getOrdersForCourier(user, from);
        } else {
            orders = orderService.getOrdersForCourier(user);
        }

        Logger.info("Found %d orders", orders.size());
        List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
        for (Order order : orders) {
            CaffeJobsList job = new CaffeJobsList();
            job.id = order.id;

            if (order.orderStatus.equals(OrderStatus.CONFIRMED)
                    && !(order.confirmedCourier_id == (user.id))) {
                job.status = "ALREADY_CONFIRMED";
            } else {
                job.status = order.orderStatus.toString();
            }

            job.price = convertMoneyToCents(order.getMenuTotal());
            job.customerPrice = convertMoneyToCents(order.getGrandTotal());
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
        if (session.contains("_id")) {
            processPushAsRestaurant(session.get("_id"), p);
        }
        if (UserType.COURIER.equals(user.userType)) {
            processPushAsCourier(user, p);
        }
        error();
    }

    static private void processPushAsCourier(User user, PushMessage message) {
        Order order = orderService.getById(message.id);
        notFoundIfNull(order);
        Logger.debug("p found order id = %s", order.id);
        switch (OrderStatus.convert(message.status)) {
            case ACCEPTED:
            case CONFIRMED:
                order.orderStatus = OrderStatus.CONFIRMED;
                order.orderConfirmed = new Date();
                order.confirmedCourier_id = user.id;
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
        orderService.update(order);
        ok();
    }

    private static void processPushAsRestaurant(String deviceId, PushMessage message) {
        Order order = orderService.getById(message.id);
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
        orderService.update(order);
        restaurantService.updateDevicePing(deviceId);
        ok();
    }
}