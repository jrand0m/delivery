package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class OrderItem extends Model {
    public Integer  count;

    public boolean  deleted = false;

    public MenuItem menuItemId;

    public Order    order;
    /**
     * Archived price that should be paid by user (That was calculated in
     * moment, when order was approved).
     * */
    public Integer  orderItemPrice;
    /**
     * Archived real price from client (That was calculated in moment, when
     * order was approved).
     * */
    public Integer  orderItemUserPrice;

    public OrderItem() {

    }

    public OrderItem(Integer count, Integer orderItemUserPrice,
            Integer orderItemPrice, Order orderId, MenuItem menuitem) {
        this.menuItemId = menuitem;
        this.count = count;
        this.orderItemUserPrice = orderItemUserPrice;
        this.orderItemPrice = orderItemPrice;
        this.order = orderId;
        this.deleted = false;
    }

    public OrderItem(MenuItem menuItem, Order order, User user) {
        // TODO [Mike] (add calculations of a price here )
        menuItemId = menuItem;
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItemId;// Model.all(MenuItem.class).getByKey(menuItemId);
    }

    public Order getOrder() {

        return order;// Model.all(Order.class).getByKey(orderId);
    }

}
