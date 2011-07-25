package models;

import java.util.Date;

import siena.Id;
import siena.Model;
import siena.NotNull;

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

    public static enum OrderStatus {
	OPEN, ACCEPTED, COOKED, DELIVERING, DELIVERED, DECLINED
    }

    public Boolean deleted = false;
}
