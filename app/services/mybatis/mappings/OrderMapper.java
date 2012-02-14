package services.mybatis.mappings;

import enumerations.OrderStatus;
import models.Order;
import models.OrderItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public interface OrderMapper {

    @Select("select * from vd_order o  where o.\"orderStatus\" = 'OPEN' and  o.\"order_owner_id\" = #{1} and o.\"restaurant_id\" = #{2}" )
    Order getOpenOrderForUserAndRestaurant(Long userId, Integer restaurantId);

    @Select("insert into vd_order orders (confirmed_courier_id, delivery_address_id, order_owner_id, restaurant_id, declineMessage, deleted," +
            " deliveryPrice,  orderAccepted,  orderClosed,  orderConfirmed,  orderCooked,  orderCreated, orderDelivered," +
            " orderPlanedCooked, orderPlanedDeliveryTime, orderStatus, orderTaken, paymentStatus, totalMenuPrice) values " +
            " (#{confirmed_courier_id}, #{delivery_address_id}, #{order_owner_id}, #{restaurant_id}, #{declineMessage}, #{deleted}," +
            "  #{deliveryPrice},  #{orderAccepted},  #{orderClosed},  #{orderConfirmed},  #{orderCooked},  #{orderCreated}, #{orderDelivered}," +
            "  #{orderPlanedCooked}, #{orderPlanedDeliveryTime}, #{orderStatus}, #{orderTaken}, #{paymentStatus}, #{totalMenuPrice})" +
            " returning orders.*, regexp_replace(deliveryPrice::text, '[$]', deliveryPrice_currency||' ' , 'g') as deliveryPrice, " +
            "regexp_replace(totalMenuPrice::text, '[$]', totalMenuPrice_currency||' ' , 'g') as totalMenuPrice")
    Order insertOrder(Order newOrder);

    @Select("select * from vd_order_items where order_id = #{1}")
    List<OrderItem> selectOrderItems(Long id);
}
