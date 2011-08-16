package models.user;

import models.clients.MenuItem;
import siena.Id;
import siena.Model;
import siena.NotNull;

public class OrderItem extends Model {
    @Id
    public Long id;
    @NotNull
    public Integer count;
    /**
     * Archived real price from client (That was calculated in moment, when
     * order was approved).
     * */
    @NotNull
    public Integer orderItemUserPrice;
    /**
     * Archived price that should be paid by user (That was calculated in
     * moment, when order was approved).
     * */
    @NotNull
    public Integer orderItemPrice;
    @NotNull
    public Order orderId;
    public MenuItem menuItemId;
    public boolean deleted = false;

    public OrderItem() {

    }

    public OrderItem(Integer count, Integer orderItemUserPrice,
	    Integer orderItemPrice, Order orderId, MenuItem menuitem) {
	this.menuItemId = menuitem;
	this.count = count;
	this.orderItemUserPrice = orderItemUserPrice;
	this.orderItemPrice = orderItemPrice;
	this.orderId = orderId;
	this.deleted = false;
    }

    public OrderItem(MenuItem menuItem, Order order, User user) {
	// TODO [Mike] (add calculations of a price here )
	menuItemId = menuItem;
	orderId = order;
    }

    public Order getOrder() {

	return orderId;// Model.all(Order.class).getByKey(orderId);
    }

    public MenuItem getMenuItem() {
	return menuItemId;// Model.all(MenuItem.class).getByKey(menuItemId);
    }

}
