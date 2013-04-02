package services.ebean;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.InvalidValue;
import enumerations.OrderStatus;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.Address;
import models.geo.City;
import models.geo.Street;
import models.users.User;
import play.Logger;
import services.OrderService;
import enumerations.OrderStatus;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderServiceEbeanImpl implements OrderService {

    @Override
    public Order getOrderBySIDAndOwner(Long id, User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public City getOrdersCity(Order order) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Order getCurrentOrderFor(final UUID userId, final Integer restaurantId) {
        Order order;
        User u = Ebean.find(User.class).where().eq("id",userId).findUnique();
        Restaurant restaurant = Ebean.find(Restaurant.class).where().eq("id",restaurantId).findUnique();
        if (u == null || restaurant == null){
            return null;
        }
        List<Order> list = Ebean.find(Order.class)
                .where().eq("order_owner_id", u.id).eq("restaurant_id",restaurantId).eq("orderStatus", OrderStatus.OPEN).orderBy().asc("orderCreated").setMaxRows(1).findList();
        if (list.size()==0){
            order = createNewOpenOrderFor(userId,restaurantId);
            if (Logger.isDebugEnabled()){
                Logger.debug(String.format("created new OPEN order for [user=%s, restaurant=%s, order=%s]",userId,restaurantId,order));
            }
            order = insertOrder(order);Logger.warn("there must be tes for me !");
        } else {
            order = list.get(0);
        }
        return order;
    }

    @Override
    public Order createNewOpenOrderFor(final UUID userId, final Integer restaurantId) {
        
        User u = Ebean.find(User.class).where().eq("id",userId).findUnique();
        Restaurant restaurant = Ebean.find(Restaurant.class).where().eq("id",restaurantId).findUnique();
        if (u == null || restaurant == null){
            return null;
        }
        List<Order> openList = Ebean.find(Order.class).where().eq("order_owner_id", u.id)
        .eq("orderStatus", OrderStatus.OPEN)
        .eq("restaurant_id",restaurantId)
        .eq("deleted", false).findList();
        for(Order o: openList){
        //closing all open orders for this customer and restaurant
            o.deleted = true;
            update(o);
        }
        Order o = new Order();
        o.orderStatus = OrderStatus.OPEN;
        o.restaurant_id = restaurantId;
        o.order_owner_id = u.id;
        Address delivery_address_stub  = new Address();
        delivery_address_stub.city_id= City.NO_CITY_ID;
        delivery_address_stub.street_id= Street.NO_STREET_ID;
        Ebean.save(delivery_address_stub);
        o.delivery_address_id = delivery_address_stub.id;
        o = insertOrder(o);
        return o;

    }

    @Override
    public void deleteOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void updateOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public OrderItem getOrderItemByIdAndOwner(Long id, User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void update(Order order) {
        Ebean.update(order);
    }

    @Override
    public OrderItem insertOrderItem(OrderItem oi) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Restaurant getRestaurantFromOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<OrderItem> getItems(Long orderId) {
        Logger.warn("implement tests for me!!!");
        List<OrderItem> items = Ebean.find(OrderItem.class).where().eq("deleted", false).eq("orderId", orderId).findList();
        return items;
    }

    @Override
    public boolean isEmptyOrder(Order order) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getOrdersForCourier(User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getOrdersForCourier(User user, Long from) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getAllOrdersOrderedByCreationDate() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getOrdersOrderedByCreationDateFor(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Order getById(Long id) {
        Logger.warn("implement tests for me !");
        return Ebean.find(Order.class).where().eq("id",id).findUnique();
    }

    @Override
    public Order insertOrder(Order o) {
        Logger.warn("Implement tests for me plz!!!");
        Ebean.save(o);
        return o;
    }
}
