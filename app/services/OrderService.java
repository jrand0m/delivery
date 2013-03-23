package services;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.User;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 11:23 PM
 */
public interface OrderService {

    Order getOrderBySIDAndOwner(Long id, User user);

    City getOrdersCity(Order order);

    Order getCurrentOrderFor(String userId, Integer restaurantId);

    /**
     * Will close all open orders before creating new one
     * */
    Order createNewOpenOrderFor(String userId, Integer restaurantId);

    void deleteOrderItem(OrderItem itm);

    void updateOrderItem(OrderItem itm);

    OrderItem getOrderItemByIdAndOwner(Long id, User user);

    void update(Order order);

    OrderItem insertOrderItem(OrderItem oi);

    Restaurant getRestaurantFromOrderItem(OrderItem itm);

    List<OrderItem> getItems(Order o);

    boolean isEmptyOrder(Order order);

    List<Order> getOrdersForCourier(User user);

    List<Order> getOrdersForCourier(User user, Long from);

    List<Order> getAllOrdersOrderedByCreationDate();

    List<Order> getOrdersOrderedByCreationDateFor(Long id);

    Order getById(String id);

    Order insertOrder(Order o);

}
