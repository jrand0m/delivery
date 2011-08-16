package models.user;

import java.util.Date;
import java.util.List;

import models.Address;
import models.clients.Client;
import siena.Filter;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Query;

public class Order extends Model {
    private static final double GURANTEE_PROFIT_RATE = 1.3;
    @Id
    public Long id;
    /**
     * 
     * @deprecated we can extract client from OrderItem.MenuItemId.client
     * */
    @NotNull
    public Client client;
    @NotNull
    public OrderStatus orderStatus;
    public Date orderDate;
    public Date clientRecieved;
    public Date clientPlanedFinish;
    public Date clientRealFinish;
    public Date courierPlanedDeliveryTime;
    public Date courierOrderRecieved;
    /**
     * Courier delivery time;
     * */
    public Date courierOrderClosed;
    /**
     * User order close date/time
     * */
    public Date orderClosed;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */
    @NotNull
    public Integer deliveryPrice;
    @NotNull
    public User orderOwner;
    @NotNull
    public Address deliveryAddress;
    @Filter("orderId")
    public Query<OrderItem> items;
    public Boolean deleted = false;

    public static enum OrderStatus {
	OPEN, ACCEPTED, COOKED, DELIVERING, DELIVERED, DECLINED
    }

    public List<OrderItem> getItems() {
	return items.filter("deleted", false).fetch();
    }

    public Client getClient() {
	return this.client;
    }

    public User getOwner() {
	return this.orderOwner;
    }

    public int getCount() {
	return items.filter("deleted", false).count();
    }

    public Integer getTotalPrice() {
	List<OrderItem> items = getItems();

	Integer totalPrice = new Integer(0);
	for (OrderItem item : items) {

	    totalPrice += item.orderItemUserPrice == null ? item.orderItemUserPrice = 0
		    : item.orderItemUserPrice * item.count;
	}
	return totalPrice;
    }

    public Integer getTotalCost() {
	List<OrderItem> items = getItems();
	Integer totalPrice = new Integer(0);
	for (OrderItem item : items) {
	    totalPrice += item.orderItemUserPrice == null ? item.orderItemUserPrice = 0
		    : item.orderItemUserPrice * item.count;
	}
	return totalPrice;
    }

    private Integer getDeliveryPrice() {
	int cost = getDeliveryCost() - (getTotalPrice() - getTotalCost());

	return cost < 0 ? 0 : getDeliveryCost();
    }

    private Integer getDeliveryCost() {
	return 20;
    }

}
