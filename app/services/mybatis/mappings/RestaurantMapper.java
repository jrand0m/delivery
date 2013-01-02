package services.mybatis.mappings;

import models.MenuItem;
import models.MenuItemGroup;
import models.Restaurant;
import models.StoredFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public interface RestaurantMapper {
    @Select("select * from \"vd_restaurant\" rest where rest.\"city_id\" = #{id} and rest.\"showOnIndex\" = #{showOnIndex}")
    List<Restaurant> selectByCityIdAndShowOnIndex(@Param("id") Long i, @Param("showOnIndex") boolean b);

    @Select("select * from \"vd_restaurant\" rest where rest.\"id\" = #{1}")
    Restaurant selectById(Long id);

    @Select("select * from vd_menu_items_groups where restaurant_Id = 1")
    List<MenuItemGroup> getMenuGroupsFor(Long id);

    @Select("select * from vd_menu_items itmz join (select c.menu_item_id as id, count(c.id)>0 as showComponents from vd_menu_item_components c group by c.menu_item_id) comp on comp.id = itmz.id where itmz.menu_item_group_id = #{1}")
    List<MenuItem> getMenuItemsFromGroup(Integer id);

    @Select("select * from vd_attachments attach join vd_restaurant rest on rest.logo_id = attach.id where rest.id = #{1}")
    StoredFile selectLogoFileFor(long id);

    @Select("select * from vd_restaurant")
    List<Restaurant> selectAllRestaurants();
}
