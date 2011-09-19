package models;

import helpers.SystemCalc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.users.User;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.db.jpa.GenericModel;
import enumerations.OrderStatus;
import enumerations.PaymentStatus;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "Orders")
public class Order extends GenericModel {

    public static final class HQL {
	public static final String BY_ORDER_OWNER_AND_ORDER_STATUS = Order.FIELDS.ORDER_OWNER
		+ " = ? and " + Order.FIELDS.ORDER_STATUS + " = ?";
	public static final String BY_ORDER_STATUS_AND_ANON_SID = Order.FIELDS.ORDER_STATUS
		+ " = ? and " + Order.FIELDS.ANONSID + " = ?";
	public static final String BY_OWNER = Order.FIELDS.ORDER_OWNER + " = ?";
	public static final String BY_ANONSID = Order.FIELDS.ANONSID + " = ? ";
    }

    public static final class FIELDS {
	public static final String ID = "id";
	public static final String COURIER_ORDERCLOSED = "courierOrderClosed";
	public static final String COURIER_ORDER_RECIEVED = "courierOrderRecieved";
	public static final String COURIER_PLANED_DELIVERY_TIME = "courierPlanedDeliveryTime";
	public static final String DELETED = "deleted";
	public static final String DELIVERY_ADDRESS = "deliveryAddress";
	public static final String DELIVERY_PRICE = "deliveryPrice";
	public static final String ORDER_CREATED = "orderCreated";
	public static final String ITEMS = "items";
	public static final String ORDER_CLOSED = "orderClosed";
	public static final String ORDER_DATE = "orderDate";
	public static final String ORDER_OWNER = "orderOwner";
	public static final String RESTAURANT = "restaurant";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String ANONSID = "anonSID";
	public static final String SHORTHAND_ID = "shortHandId";

    }

    // private static final double GURANTEE_PROFIT_RATE = 1.3;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    /**
     * Courier delivered order;
     * */
    public Date courierOrderClosed;
    /**
     * Courier picked up bundle
     * */
    public Date courierOrderRecieved;
    /**
     * Estimated time of delivery to restaurant
     * */
    public Date courierPlanedDeliveryTime;
    public boolean deleted = false;
    @ManyToOne
    public Address deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */
    public Integer deliveryPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Where(clause = "deleted = 0")
    public List<OrderItem> items = new ArrayList<OrderItem>();

    /**
     * Order Created
     * */
    public Date orderCreated;
    /**
     * User order close date/time
     * */
    public Date orderClosed;

    /**
     * Time and date of order is accepted
     * */
    public Date orderDate;
    /**
     * User who made this order
     * */
    @ManyToOne
    public User orderOwner;

    @ManyToOne
    public Restaurant restaurant;

    public OrderStatus orderStatus = OrderStatus.OPEN;
    public PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    /**
     * anonymous user id generated by Codec.UUID();
     * */
    public String anonSID;
    public String shortHandId;

    /**
     * Function for getting short id
     * */
    public String getShortHandId() {
	if (id == null) {
	    throw new UnsupportedOperationException(
		    "Taking shorthand on null id!");
	}
	if (shortHandId == null) {
	    shortHandId = id.substring(0, 8);
	}
	return shortHandId;
    }

    public void setShortHandId(String id) {
	shortHandId = id;
    }

    /**
     * Just sum of all items without discount
     * */
    public Integer getMenuTotal() {
	Integer i = 0;
	for (OrderItem item : items) {
	    i += item.orderItemUserPrice * item.count;
	}
	return i;
    }

    /**
     * calculated discount for entire order
     * */
    public Integer getUserDiscount() {

	return SystemCalc.getUserDiscount(this);
    }

    /**
     * calculated delivery price for this order
     * */
    public Integer getDeliveryPrice() {
	return SystemCalc.getDeliveryPrice(this);
    }

    /**
     * Grand total including delivery price and excluding user discount
     * */
    public Integer getGrandTotal() {

	Integer menuTotal = getMenuTotal();
	return menuTotal + getDeliveryPrice() - getUserDiscount() * menuTotal;
    }

    public static Order findByShortId(String shortID) {
	return Order.find("shortHandId = ? or id like ?", shortID,
		shortID + "%").first();
    }
    /*
     * private Integer getDeliveryCost() { return 20; }
     * 
     * private Integer getDeliveryPrice() { int cost = getDeliveryCost() -
     * (getTotalPrice() - getTotalCost());
     * 
     * return cost < 0 ? 0 : getDeliveryCost(); }
     * 
     * public Integer getTotalCost() { Integer totalPrice = new Integer(0); for
     * (OrderItem item : items) { totalPrice += item.orderItemUserPrice == null
     * ? item.orderItemUserPrice = 0 : item.orderItemUserPrice * item.count; }
     * return totalPrice; }
     * 
     * public Integer getTotalPrice() { Integer totalPrice = new Integer(0); for
     * (OrderItem item : items) {
     * 
     * totalPrice += item.orderItemUserPrice == null ? item.orderItemUserPrice =
     * 0 : item.orderItemUserPrice * item.count; } return totalPrice; }
     */

}
