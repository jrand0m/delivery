package services.mybatis.mappings;

import models.MenuItemGroup;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public interface MenuItemGroupMapper {
    @Select("")
    List<MenuItemGroup> selectAllMenuItemGroups();
}
