package models;

import helpers.SystemCalc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.geo.UserAddress;
import models.users.EndUser;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.db.jpa.GenericModel;
import enumerations.OrderStatus;
import enumerations.PaymentStatus;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "Orders")
public class Order extends GenericModel {

    public static final class FIELDS {
	public static final String ANONSID = "anonSID";
	public static final String DECLINE_MESSAGE = "declineMessage";
	public static final String DELETED = "deleted";
	public static final String DELIVERY_ADDRESS = "deliveryAddress";
	public static final String DELIVERY_PRICE = "deliveryPrice";
	public static final String ID = "id";
	public static final String ITEMS = "items";
	public static final String ORDER_ACCEPTED = "orderAccepted";
	public static final String ORDER_CLOSED = "orderClosed";
	public static final String ORDER_COOKED = "orderCooked";
	public static final String ORDER_CONFIRMED = "orderConfirmed";
	public static final String ORDER_CREATED = "orderCreated";
	public static final String ORDER_DELIVERED = "orderDelivered";
	public static final String ORDER_OWNER = "orderOwner";
	public static final String ORDER_PLANED_COOKED = "orderPlanedCooked";
	public static final String ORDER_PLANED_DELIVERY_TIME = "orderPlanedDeliveryTime";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String ORDER_TAKEN = "orderTaken";
	public static final String RESTAURANT = "restaurant";
	public static final String SHORTHAND_ID = "shortHandId";
    }

    public static final class HQL {
	public static final String BY_ANONSID = Order.FIELDS.ANONSID + " = ? ";
	public static final String BY_ORDER_OWNER_AND_ORDER_STATUS = Order.FIELDS.ORDER_OWNER
		+ " = ? and " + Order.FIELDS.ORDER_STATUS + " = ?";
	public static final String BY_ORDER_STATUS_AND_ANON_SID = Order.FIELDS.ORDER_STATUS
		+ " = ? and " + Order.FIELDS.ANONSID + " = ?";
	public static final String BY_OWNER = Order.FIELDS.ORDER_OWNER + " = ?";
	public static final String BY_SHORT_ID = Order.FIELDS.SHORTHAND_ID + " = ?";
	public static final String BY_SHORT_ID_OR_LIKE_FULL_ID = Order.FIELDS.SHORTHAND_ID + " = ? or " + Order.FIELDS.ID + " like ?" ;
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
    /**
     * anonymous user id generated by Codec.UUID();
     * */
    public String anonSID;
    /**
     * Message on declined status
     * */
    public String declineMessage;
    public boolean deleted = false;
    @ManyToOne
    public UserAddress deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */
    public Integer deliveryPrice;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Where(clause = "deleted = 0")
    public List<OrderItem> items = new ArrayList<OrderItem>();

    /**
     * Time and date of order is accepted
     * */
    public Date orderAccepted;
    /**
     * User order close date/time
     * */
    public Date orderClosed;
    /**
     * Order cooked
     * */
    public Date orderCooked;
    /**
     * confirmed to be valid
     * */
    public Date orderConfirmed;
    /**
     * Order Created
     * */
    public Date orderCreated;
    /**
     * Courier delivered order;
     * */
    public Date orderDelivered;

    /**
     * User who made this order
     * */
    @ManyToOne
    public EndUser orderOwner;

    /**
     * is set + time told by client 
     * */
	public Date orderPlanedCooked;
    /**
     * Estimated time of delivery to restaurant
     * */
    public Date orderPlanedDeliveryTime;
    @Enumerated(value = EnumType.STRING)
    public OrderStatus orderStatus = OrderStatus.OPEN;
    /**
     * Courier picked up bundle
     * */
    public Date orderTaken;
    @Enumerated(value = EnumType.STRING)
    public PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    @ManyToOne
    public Restaurant restaurant;

    public String shortHandId;

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

    /**
     * calculated discount for entire order
     * */
    public Integer getUserDiscount() {

	return SystemCalc.getUserDiscount(this);
    }

    public void setShortHandId(String id) {
	shortHandId = id;
    }

}
