/**
 * 
 */
package models.api;

import java.util.List;

import models.OrderItem;

/**
 * @author Mike
 *
 */
public class MenuItem {
    /**
     * @param oi
     */
    public MenuItem(OrderItem oi) {
        count  = oi.count;
        name = oi.menuItem.name;
        pricePerItem = oi.menuItem.price;
        //check for components
    }
    
    /**
     * default eblja 
     */
    public MenuItem() {
        // TODO Auto-generated constructor stub
    }
    public String name;
    public Integer count;
    public Integer pricePerItem;
    public List<MenuItem> components;
}
