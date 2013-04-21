package controllers;

import enumerations.OrderStatus;
import enumerations.UserType;
import models.MenuItemComponent;
import models.Order;
import models.OrderItem;
import models.dto.intern.CaffeJobsList;
import models.dto.intern.MenuItem;
import models.dto.intern.PushMessage;
import models.users.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.CalculationsService;
import services.OrderService;
import services.RestaurantService;
import services.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static helpers.OrderUtils.convertMoneyToCents;

/**
 * @author Mike
 */

//TODO: @With(Secure.class)
public class API extends Controller {
    @Inject
    private static RestaurantService restaurantService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;
    @Inject
    private static CalculationsService calculationsService;


    public static Result g(Long from) {

        if (session().containsKey("_id")) {
            processGetAsRestaurant(session("_id"), from);
        }
        User user = userService.getUserByLogin(session("user"));

        if (UserType.COURIER.equals(user.userType)) {
            return processGet(user, from);
        }
        return notFound();
    }

    private static Result processGetAsRestaurant(String device, Long from) {

        List<Order> orders;
        if (from != null) {
            orders = restaurantService.getLastOrdersForDeviceFrom(device, from);
        } else {
            orders = restaurantService.getLastOrdersForDevice(device);
        }

        Logger.info(String.format("Found %d orders for agent %s", orders.size(),""));
        ArrayNode jobs = Json.newObject().putArray("array");
        for (Order order : orders) {
            ObjectNode job = Json.newObject();
            job.put("id", String.valueOf(order.id));
            job.put("status" , order.orderStatus.toString());
            job.put("price" , calculationsService.getMenuTotalFor(order.id).getAmountMinorInt());
            job.put("paymentStatus",  order.paymentStatus.toString());
            job.put("time", order.orderConfirmed.toDateTime().toDate().getTime());
            job.put("timeToFinish", order.orderStatus == OrderStatus.ACCEPTED ? new Period(order.orderAccepted.plus(order.orderPlanedCooked), new LocalDateTime(), PeriodType.minutes())
                    .toStandardMinutes().getMinutes()
                    : null);
            ArrayNode list = job.putArray("list");
            for (OrderItem oi : orderService.getItems(order.id)) {
                ObjectNode item = Json.newObject();
                item.put("count",oi.count );
                item.put("name", oi.menuItem.name);
                item.put("pricePerItem", convertMoneyToCents(oi.menuItem.price));
                if (oi.selectedComponents != null && !oi.selectedComponents.isEmpty()) {

                    ArrayNode components = item.putArray("components");
                    for (MenuItemComponent mic : oi.selectedComponents) {
                        ObjectNode component= Json.newObject();
                        component.put("count", 1);
                        component.put("name", mic.name());
                        component.put("pricePerItem", convertMoneyToCents(mic.price()));
                        components.add(component);
                    }

                }
                list.add(item);
            }
            jobs.add(job);
        }
        restaurantService.updateDevicePing(device);
        return ok(jobs);
    }

    static private Result processGet(User user, Long from) {
        List<Order> orders;
        if (from != null) {
            orders = orderService.getOrdersForCourier(user, from);
        } else {
            orders = orderService.getOrdersForCourier(user);
        }

        Logger.info(String.format("Found %d orders", orders.size()));
        List<CaffeJobsList> jobs = new ArrayList<CaffeJobsList>(orders.size());
        for (Order order : orders) {
            CaffeJobsList job = new CaffeJobsList();
            job.id = String.valueOf(order.id);

            if (order.orderStatus.equals(OrderStatus.CONFIRMED)
                    && !(order.confirmed_courier_id == (user.id))) {
                job.status = "ALREADY_CONFIRMED";
            } else {
                job.status = order.orderStatus.toString();
            }

            job.price = calculationsService.getMenuTotalFor(order.id).getAmountMinorInt();
            job.customerPrice = calculationsService.getOrderPriceTotalForUser(order.id).getAmountMinorInt();
            job.paymentStatus = order.paymentStatus.toString();
            job.time = order.updatedAt.toDate().getTime();
            job.timeToFinish = order.orderStatus == OrderStatus.ACCEPTED ?
                    new Period(order.orderAccepted.plus(order.orderPlanedCooked), new LocalDateTime(), PeriodType.minutes())
                            .toStandardMinutes().getMinutes()
                    : null;
            if (order.deliveryAddress == null) {
                Logger.warn(String.format("No delivery address for order id %s",
                        order.id));
                job.additionalInfo = null;
            } else {
                job.additionalInfo = order.deliveryAddress.additionalInfo;
            }
            job.from = order.restaurant.getAddress().toString();
            job.to = order.deliveryAddress == null ? "none"
                    : order.deliveryAddress.toString();
            job.timeToDelivered = order.orderStatus.equals(OrderStatus.DELIVERING) || order.orderStatus.equals(OrderStatus.COOKED) ?
                    order.orderCooked.plus(order.orderPlanedDeliveryTime).toDateTime().toDate().getTime() : 0;
            for (OrderItem oi : orderService.getItems(order.id)) {
                job.list.add(new MenuItem(oi));
            }
            job.phone = order.orderOwner.phoneNumber;
            jobs.add(job);
        }
        return ok(Json.toJson(jobs));
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result p() {
        JsonNode message = request().body().asJson();

        Logger.debug(String.format("p in message = %s", Json.stringify(message)));
        PushMessage p = Json.fromJson(message, PushMessage.class);
        if (p.id == null) {
            return notFound();
        }
        User user = (User) userService.getUserByLogin(session("login"));
        if (session().containsKey("_id")) {
            return processPushAsRestaurant(session("_id"), p);
        }
        if (UserType.COURIER.equals(user.userType)) {
            return processPushAsCourier(user, p);
        }
        return notFound();
    }

    static private Result processPushAsCourier(User user, PushMessage message) {
        Order order = orderService.getById(message.id);
        if (order == null ) return notFound();
        Logger.debug(String.format("p found order id = %s", order.id));
        switch (OrderStatus.convert(message.status)) {
            case ACCEPTED:
            case CONFIRMED:
                order.orderStatus = OrderStatus.CONFIRMED;
                order.orderConfirmed = new LocalDateTime();
                order.confirmed_courier_id = user.id;
                order.orderPlanedDeliveryTime = new Period(message.time, PeriodType.minutes());
                break;
            case DELIVERED:
                order.orderStatus = OrderStatus.DELIVERED;
                order.orderDelivered = new LocalDateTime();
                break;
            case DECLINED:
                order.orderStatus = OrderStatus.DECLINED;
                order.orderClosed = new LocalDateTime();
                // FIXME see how long message can be
                order.declineMessage = message.comment; // != null ?
                // p.comment.substring(0, 250) :
                // "" ;
                break;
            default:
                Logger.debug("p not found corresponding status ");
                return notFound();
        }
        orderService.update(order);
        return ok();
    }

    private static Result processPushAsRestaurant(String deviceId, PushMessage message) {
        Order order = orderService.getById(message.id);
        if(order == null ) return notFound();
        switch (OrderStatus.convert(message.status)) {
            case COOKED:
                order.orderStatus = OrderStatus.COOKED;
                order.orderCooked = new LocalDateTime();
                break;
            case ACCEPTED:
                order.orderStatus = OrderStatus.ACCEPTED;
                order.orderAccepted = new LocalDateTime();
                order.orderPlanedCooked = new Period(message.time, PeriodType.minutes());
                /*order.orderPlanedDeliveryTime = new Date(
                        order.orderPlanedCooked.getTime()
                                + order.orderPlanedDeliveryTime.getTime());*/
                break;
            case DECLINED:
                order.orderStatus = OrderStatus.DECLINED;
                order.orderClosed = new LocalDateTime();
                // FIXME see how long message can be
                order.declineMessage = message.comment; // != null ?
                break;
            case DELIVERING:
                order.orderStatus = OrderStatus.DELIVERING;
                order.orderTaken = new LocalDateTime();
                break;
            default:
                Logger.debug("p not found corresponding status ");
                return notFound();
        }
        orderService.update(order);
        restaurantService.updateDevicePing(deviceId);
        return ok();
    }
}