package models;

import java.util.Date;
import java.util.List;

import siena.Id;
import siena.Model;

public class Order extends Model {
    @Id
    Long id;
    Client client;
    OrderStatus orderStatus;
    List<OrderItem> items;
    Date orderDate;
    Date clientRecieved;
    Date clientPlanedFinish;
    Date clientRealFinish;
    Date courierPlanedDeliveryTime;
    Date courierOrderRecieved;
    /**
     * Courier delivery time;
     * */
    Date courierOrderClosed;
    /**
     * User order close date/time
     * */
    Date orderClosed;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */
    Integer deliveryPrice;

    Adress deliveryAddress;

    public static enum OrderStatus {

    }
}
