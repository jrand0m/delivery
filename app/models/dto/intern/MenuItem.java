/**
 *
 */
package models.dto.intern;

import models.MenuItemComponent;
import models.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike
 */
public class MenuItem {
    /**
     * @param oi
     */
    public MenuItem(OrderItem oi) {
        count = oi.count;
        name = oi.menuItem.name;
        pricePerItem = oi.menuItem.price;
        if (oi.selectedComponents != null && !oi.selectedComponents.isEmpty()) {
            components = new ArrayList<MenuItem>();
            for (MenuItemComponent mic : oi.selectedComponents) {
                components.add(new MenuItem(mic));
            }
        }
    }

    public MenuItem(MenuItemComponent mic) {
        count = 1;
        name = mic.name();
        pricePerItem = mic.price();
    }

    public Long mi;
    public String name;
    public String descr;
    public Integer count;
    public Integer pricePerItem;
    public List<MenuItem> components;
}
