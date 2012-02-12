package services.mybatis.mappings;

import models.Restaurant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public interface RestaurantMapper {
    @Select("select * from \"vd_restaurant\" rest where rest.\"city_id\" = #{id} and rest.\"showOnIndex\" = #{showOnIndex}")
    List<Restaurant> selectByCityIdAndShowOnIndex(@Param("id") Long i, @Param("showOnIndex") boolean b);
}
