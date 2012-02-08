package services.mybatis;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.User;
import services.OrderService;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public class OrderServiceMyBatisImpl implements OrderService {
    @Override
    public Order getOrderBySIDAndOwner(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public City getOrdersCity(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order getCurrentOrderFor(User user, Restaurant restaurant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order createNewOpenOrderFor(User user, Restaurant restaurant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderItem getOrderItemByIdAndOwner(Long id, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderItem insertOrderItem(OrderItem oi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Restaurant getRestaurantFromOrderItem(OrderItem itm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderItem> getItems(Order o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmptyOrder(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrdersForCourier(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrdersForCourier(User user, Long from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAllOrdersOrderedByCreationDate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrdersOrderedByCreationDateFor(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order getById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order insertOrder(Order o) {
        throw new UnsupportedOperationException();
    }
}
