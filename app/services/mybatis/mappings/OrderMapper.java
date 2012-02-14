package services.mybatis.mappings;

import enumerations.OrderStatus;
import models.Order;
import org.apache.ibatis.annotations.Select;

/**
 * User: Mike Stetsyshyn
 */
public interface OrderMapper {

    @Select("select * from vd_order o  where o.\"orderStatus\" = 'OPEN' and  o.\"order_owner_id\" = #{1} and o.\"restaurant_id\" = #{2}" )
    Order getOpenOrderForUserAndRestaurant(Long userId, Integer restaurantId);
}
