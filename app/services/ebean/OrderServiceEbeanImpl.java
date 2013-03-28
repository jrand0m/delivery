package services.ebean;

import com.avaje.ebean.Ebean;
import enumerations.OrderStatus;
import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.User;
import services.OrderService;
import enumerations.OrderStatus;

import java.util.List;

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
    public Order getCurrentOrderFor(final String userId, final Integer restaurantId) {
        Order order;
        User u = Ebean.find(User.class).where().eq(User.GET_USER_ID_FIELD_NAME(),userId).findUnique();
        if(u == null){
            return null;
        }
        List<Order> list = Ebean.find(Order.class)
                .where().eq("order_owner_id", u.id).eq("restaurant_id",restaurantId).eq("orderStatus", OrderStatus.OPEN).orderBy().asc("orderCreated").setMaxRows(1).findList();
        if (list.size()==0){

            order = createNewOpenOrderFor(userId,restaurantId);
        } else {
            order = list.get(0);
        }
        return order;
    }

    @Override
    public Order createNewOpenOrderFor(final String userId, final Integer restaurantId) {
        
        User u = Ebean.find(User.class).where().eq(User.GET_USER_ID_FIELD_NAME(),userId).findUnique();
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
    public List<OrderItem> getItems(Order o) {
        throw new UnsupportedOperationException("Implement Me");
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
    public Order getById(String id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Order insertOrder(Order o) {
        throw new UnsupportedOperationException("Implement Me");
    }
}
