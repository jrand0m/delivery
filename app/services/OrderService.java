package services;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.User;

import java.util.List;
import java.util.UUID;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 11:23 PM
 */
public interface OrderService {

    Order getOrderBySIDAndOwner(Long id, User user);

    City getOrdersCity(Order order);

    /**
     *  gets current order for specified user and restaurant
     *  if no order found will call <b>createNewOpenOrderFor</b>
     *  @param userId - user provided id (see models.User.GET_USER_ID_FIELD_NAME())
     *  @param restaurantId - internal id of restaurant
     * */
    Order getCurrentOrderFor(UUID userId, Integer restaurantId);

    /**
     * Will close all open orders before creating new one
     *  @param userId - user provided id (see models.User.GET_USER_ID_FIELD_NAME())
     *  @param restaurantId - internal id of restaurant
     * */
    Order createNewOpenOrderFor(UUID userId, Integer restaurantId);

    void deleteOrderItem(OrderItem itm);

    void updateOrderItem(OrderItem itm);

    OrderItem getOrderItemByIdAndOwner(Long id, User user);

    void update(Order order);

    OrderItem insertOrderItem(OrderItem oi);

    Restaurant getRestaurantFromOrderItem(OrderItem itm);

    List<OrderItem> getItems(Long orderId);

    boolean isEmptyOrder(Order order);

    List<Order> getOrdersForCourier(User user);

    List<Order> getOrdersForCourier(User user, Long from);

    List<Order> getAllOrdersOrderedByCreationDate();

    List<Order> getOrdersOrderedByCreationDateFor(Long id);

    /**
     * @return order with specified id
     * */
    Order getById(Long id);

    Order insertOrder(Order o);

}
