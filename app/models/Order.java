package models;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import siena.Filter;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Query;

public class Order extends Model {
    @Id
    public Long id;
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
    public static enum OrderStatus {
	OPEN, ACCEPTED, COOKED, DELIVERING, DELIVERED, DECLINED
    }

    public Boolean deleted = false;
    
    public List<OrderItem> getItems(){
	return Model.all(OrderItem.class).filter("orderId", this/*.id*/).filter("deleted", false).fetch();
    }
    public Client getClient(){
	return this.client;
    }
    public User getOwner(){
	return this.orderOwner;
    }
    public int getCount(){
	return Model.all(OrderItem.class).filter("orderId", this/*.id*/).filter("deleted", false).count();
    }
    public Integer getTotalUserPrice(){
	List<OrderItem> items = getItems();
	Integer totalPrice = new Integer(0);
	for (OrderItem item : items) {
	    totalPrice +=  item.orderItemUserPrice * item.count;
	}
	return totalPrice;
    }
    public Integer getTotalPrice(){
	List<OrderItem> items = getItems();
	Integer totalPrice = new Integer(0);
	for (OrderItem item : items) {
	    totalPrice +=  item.orderItemPrice * item.count;
	}
	return totalPrice;
    }
}
