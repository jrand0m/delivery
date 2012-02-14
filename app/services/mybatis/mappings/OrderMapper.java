package services.mybatis.mappings;

import enumerations.OrderStatus;
import models.Order;
import org.apache.ibatis.annotations.Select;

/**
 * User: Mike Stetsyshyn
 */
public interface OrderMapper {

    @Select("select * from vd_order where order_owner_id = #{1} and restaurant_id = #{2} and orderStatus = 'OPEN'" )
    Order getOpenOrderForUserAndRestaurant(Long userId, Integer restaurantId);
}
