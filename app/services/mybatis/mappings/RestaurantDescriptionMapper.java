package services.mybatis.mappings;

import models.RestaurantDescription;
import models.time.WorkHours;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * User: Mike Stetsyshyn
 */
public interface RestaurantDescriptionMapper {
    @Select("SELECT * FROM vd_restaurant_descriptions WHERE lang = #{lang} and restaurant_id = ANY(#{ids}::int4[])")
    List<RestaurantDescription> selectDescriptionsFor(@Param("lang")String lang,@Param("ids") String ids);

    @Select("SELECT * FROM vd_restaurant_workhours WHERE id = ANY(#{1}::int4[])")
    @MapKey("id")
    Map<Integer,WorkHours> selectWorkHoursFor(String integers);
}
