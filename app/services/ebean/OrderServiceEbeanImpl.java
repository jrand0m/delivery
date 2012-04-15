package services.ebean;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.User;
import services.OrderService;

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
    public Order getCurrentOrderFor(User user, Restaurant restaurant) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Order createNewOpenOrderFor(User user, Restaurant restaurant) {
        throw new UnsupportedOperationException("Implement Me");
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
        throw new UnsupportedOperationException("Implement Me");
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
