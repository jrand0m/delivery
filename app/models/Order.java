package models;

import java.util.Date;

import siena.Id;
import siena.Model;

public class Order extends Model {
    @Id
    public Long id;
    public Client client;
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
    public Integer deliveryPrice;
    public User orderOwner;
    public Adress deliveryAddress;

    public static enum OrderStatus {
	OPEN, ACCEPTED, COOKED, DELIVERING, DELIVERED, DECLINED
    }
}
