package models;

import helpers.SystemCalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import models.users.CourierUser;
import models.users.EndUser;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.db.jpa.GenericModel;
import play.db.jpa.JPABase;
import enumerations.OrderStatus;
import enumerations.PaymentStatus;

@Entity
//@Where(clause = "deleted = 0")
@Table(name = "Orders")
public class Order extends GenericModel {

	public static final class FIELDS {
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
		public static final String RESTAURANT_DISCOUNT = "restaurantDiscount";
		public static final String TOTAL_MENU_PRICE = "totalMenuPrice";
		public static final String UPDATED = "updated";
		public static final String CONFIRMED_COURIER = "confirmedCourier";
		
	}

	public static final class HQL {

		public static final String BY_ORDER_OWNER_AND_ORDER_STATUS = Order.FIELDS.ORDER_OWNER
				+ " = ? and " + Order.FIELDS.ORDER_STATUS + " = ?";
		public static final String BY_ORDER_OWNER_AND_ORDER_STATUS_AND_RESTAURANT = BY_ORDER_OWNER_AND_ORDER_STATUS + " and "+ FIELDS.RESTAURANT + " = ?";
		public static final String BY_OWNER = Order.FIELDS.ORDER_OWNER + " = ?";
		public static final String BY_SHORT_ID = Order.FIELDS.SHORTHAND_ID
				+ " = ?";
		public static final String BY_SHORT_ID_OR_LIKE_FULL_ID = Order.FIELDS.SHORTHAND_ID
				+ " = ? or " + Order.FIELDS.ID + " like ?";
		public static final String BY_RESTAURANT_AND_STATUS = Order.FIELDS.RESTAURANT + " = ? and "+ Order.FIELDS.ORDER_STATUS + " = ? ";
		public static final String BY_RESTAURANT_AND_STATUS_AND_AFTER_DATE = BY_RESTAURANT_AND_STATUS + " and " + FIELDS.ORDER_COOKED +  " > ?";
		public static final String BY_RESTAURANT_AND_STATUS_ORDERBY_ACCEPTED_DESC = Order.FIELDS.RESTAURANT + " = ? and "+ Order.FIELDS.ORDER_STATUS + " in (?) order by "+FIELDS.ORDER_ACCEPTED+" desc";
		
		public static final String LAST_ORDERS_BY_CITY_AND_STATUS = 
				"select ord from Order ord join ord."+ FIELDS.RESTAURANT +" as rest where rest." 
		+ Restaurant.FIELDS.RESTAURANT_CITY + " = ? and ord."+FIELDS.ORDER_STATUS+" = ? order by ord."+FIELDS.ORDER_ACCEPTED +  " desc ";
		
		public static final String LAST_ORDERS_BY_CITY_AND_STATUS_AND_AFTER_DATE = 
				"select ord from Order ord join ord."+ FIELDS.RESTAURANT +" as rest where rest." 
		+ Restaurant.FIELDS.RESTAURANT_CITY + " = ? and ord."+FIELDS.ORDER_STATUS+" = ? and ord."+Order.FIELDS.ORDER_ACCEPTED+
		" > ?"+ " order by ord."+FIELDS.ORDER_ACCEPTED +  " desc ";
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
	/**
	 * Restaurant discount saved when order is accepted by user from Restauraunt.discount
	 * */
	public Float restaurantDiscount;
	
	/**
	 * Menu total price saved when order is accepted by user
	 * */
	public Integer totalMenuPrice;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	@Where(clause = "deleted = 0")
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
	public Date updated;
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
	
	@ManyToOne
	public CourierUser confirmedCourier;

	/**
	 * calculated delivery price for this order
	 * */
	public Integer getDeliveryPrice() {
		return SystemCalc.getDeliveryPrice(this);
	}
	public String getDeliveryPriceString() {
		int i = getDeliveryPrice();
		return new BigDecimal(i).setScale(2,RoundingMode.HALF_EVEN).divide(new BigDecimal(100).setScale(2,RoundingMode.HALF_EVEN)).toString();
	}
	/**
	 * Grand total including delivery price and minus calculated user discount
	 * */
	public Integer getGrandTotal() {

		Integer menuTotal = getMenuTotal();
		return menuTotal + getDeliveryPrice() - getUserDiscount().multiply(new BigDecimal( menuTotal).setScale(0)).intValue();
	}
	
	public String getGrandTotalString() {
		int i = getGrandTotal();
		return new BigDecimal(i).setScale(2,RoundingMode.HALF_EVEN).divide(new BigDecimal(100).setScale(2,RoundingMode.HALF_EVEN)).toString();
	}

	/**
	 * Just sum of all items without discount
	 * */
	public Integer getMenuTotal() {
		Integer i = 0;
		for (OrderItem item : items) {
			i += item.totalPriceInclComponents();
		}
		return i;
	}
	
	public String getMenuTotalString() {
		int i = getMenuTotal();
		return new BigDecimal(i).setScale(2,RoundingMode.HALF_EVEN).divide(new BigDecimal(100).setScale(2,RoundingMode.HALF_EVEN)).toString();
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
			shortHandId = Integer.toHexString(id.hashCode());
			save();
		}
		return shortHandId;
	}

	/**
	 * calculated discount for entire order
	 * */
	public BigDecimal getUserDiscount() {
		return SystemCalc.getUserDiscount(this);
	}

	public void setShortHandId(String id) {
		shortHandId = id;
	}
	/**
	 * is called for index page
	 * */
	public String oneLineDescription() {
		StringBuilder b = new StringBuilder();
		for (Iterator<OrderItem> it = items.iterator(); it.hasNext();){
			b.append(it.next().menuItem.name);
			if (it.hasNext()){
				b.append(", ");
			} else {
				break;
			}
			if (b.length() > 240){
				b.append ("...");
				break;
			}
		}
		return b.toString();
	}
	@Override
	public boolean create() {
		this.updated = new Date();
		return super.create();
	}
	@Override
	public <T extends JPABase> T save() {
		this.updated = new Date();
		return super.save();
	}
	public String aproxTime(){
		String time = "--";
		switch (orderStatus) {
		case ACCEPTED:
		case COOKED:
		case DELIVERING:
			long x = orderPlanedDeliveryTime==null?
					0:orderPlanedDeliveryTime.getTime() - new Date().getTime();
			x = x / 1000 / 60;
			if (x > 0){
				return String.valueOf(x);
			}
			else {return "00";}

		case DELIVERED:
		case CONFIRMED:
		case DECLINED:
		case RECIEVED:
		case SENT:

		default:
			break;
		}
		return time;
		
	}
	public String textStatus(){
		switch (orderStatus) {
		case ACCEPTED:
			return "ресторан прийняв завмовлення";
		case COOKED:
			return "ваше замовлення приготовано, очікуємо курєра";
		case DELIVERING:
			return "курєр спішить до вас :) ";
		case CONFIRMED:
			return "адреса перевірена, замовлення поставлене в чергу";
		case DECLINED:
			return "відмовлено :(";
		case RECIEVED:
			return "";
		case SENT:
			return "перевіряється адреса";
		case DELIVERED:
		default:
			break;
		}
		return "";
	}
	
}
