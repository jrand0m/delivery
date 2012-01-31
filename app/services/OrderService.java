package services;

import models.Order;
import models.OrderItem;
import models.Restaurant;
import models.geo.City;
import models.users.EndUser;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 11:23 PM
 */
public interface OrderService {

    Order getOrderBySIDAndOwner(String id, EndUser user);

    City getOrdersCity(Order order);

    Order getCurrentOrderFor(EndUser user, Restaurant restaurant);

    Order createNewOpenOrderFor(EndUser user, Restaurant restaurant);

    void deleteOrderItem(OrderItem itm);

    void updateOrderItem(OrderItem itm);

    OrderItem getOrderItemByIdAndOwner(Long id, EndUser user);

    void update(Order order);

    OrderItem insertOrderItem(OrderItem oi);

    Restaurant getRestaurantFromOrderItem(OrderItem itm);

    List<OrderItem> getItems(Order o);

    boolean isEmptyOrder(Order order);
}
