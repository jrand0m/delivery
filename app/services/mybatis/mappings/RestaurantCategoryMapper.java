package services.mybatis.mappings;

import models.RestaurantCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public interface RestaurantCategoryMapper {

    @Select("select * from vd_restaurants_categories")
    List<RestaurantCategory> selectAllCategories();

}
