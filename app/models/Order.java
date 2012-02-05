package models;

import enumerations.OrderStatus;
import enumerations.PaymentStatus;
import helpers.SystemCalc;
import models.geo.Address;
import models.users.User;
import org.hibernate.annotations.GenericGenerator;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import play.db.jpa.GenericModel;
import play.db.jpa.JPABase;
import play.libs.Codec;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Table(name = "Orders")
public class Order  {

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
        public static final String BY_ORDER_OWNER_AND_ORDER_STATUS_AND_RESTAURANT = BY_ORDER_OWNER_AND_ORDER_STATUS + " and " + FIELDS.RESTAURANT + " = ?";
        public static final String BY_OWNER = Order.FIELDS.ORDER_OWNER + " = ?";
        public static final String BY_SHORT_ID = Order.FIELDS.SHORTHAND_ID
                + " = ?";
        public static final String BY_SHORT_ID_OR_LIKE_FULL_ID = Order.FIELDS.SHORTHAND_ID
                + " = ? or " + Order.FIELDS.ID + " like ?";
        public static final String BY_RESTAURANT_AND_STATUS = Order.FIELDS.RESTAURANT + " = ? and " + Order.FIELDS.ORDER_STATUS + " = ? ";
        public static final String BY_RESTAURANT_AND_STATUS_AND_AFTER_DATE = BY_RESTAURANT_AND_STATUS + " and " + FIELDS.ORDER_COOKED + " > ?";
        public static final String BY_RESTAURANT_AND_STATUS_ORDERBY_ACCEPTED_DESC = Order.FIELDS.RESTAURANT + " = ? and " + Order.FIELDS.ORDER_STATUS + " in (?) order by " + FIELDS.ORDER_ACCEPTED + " desc";

        public static final String LAST_ORDERS_BY_CITY_AND_STATUS =
                "select ord from Order ord join ord." + FIELDS.RESTAURANT + " as rest where rest."
                        + Restaurant.FIELDS.RESTAURANT_CITY + " = ? and ord." + FIELDS.ORDER_STATUS + " = ? order by ord." + FIELDS.ORDER_ACCEPTED + " desc ";

        public static final String LAST_ORDERS_BY_CITY_AND_STATUS_AND_AFTER_DATE =
                "select ord from Order ord join ord." + FIELDS.RESTAURANT + " as rest where rest."
                        + Restaurant.FIELDS.RESTAURANT_CITY + " = ? and ord." + FIELDS.ORDER_STATUS + " = ? and ord." + Order.FIELDS.ORDER_ACCEPTED +
                        " > ?" + " order by ord." + FIELDS.ORDER_ACCEPTED + " desc ";
    }

    /**
     * Message on declined status
     */
    public String declineMessage;
    public boolean deleted = false;

    @ManyToOne
    public Address deliveryAddress;
    public Long deliveryAddress_id;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     */
    public Integer deliveryPrice;
    /**
     * Restaurant discount saved when order is accepted by user from Restauraunt.discount
     */
    public Float restaurantDiscount;

    /**
     * Menu total price saved when order is accepted by user
     */
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
     */
    public Date orderAccepted;
    /**
     * User order close date/time
     */
    public Date orderClosed;
    /**
     * Order cooked
     */
    public Date orderCooked;
    /**
     * confirmed to be valid
     */
    public Date orderConfirmed;
    /**
     * Order Created
     */
    public Date orderCreated;
    /**
     * Courier delivered order;
     */
    public Date orderDelivered;
    public Date updated;
    /**
     * User who made this order
     */
    @ManyToOne
    public User orderOwner;

    /**
     * is set + time told by client
     */
    public Date orderPlanedCooked;
    /**
     * Estimated time of delivery to restaurant
     */
    public Date orderPlanedDeliveryTime;
    @Enumerated(value = EnumType.STRING)
    public OrderStatus orderStatus = OrderStatus.OPEN;
    /**
     * Courier picked up bundle
     */
    public Date orderTaken;
    @Enumerated(value = EnumType.STRING)
    public PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    @ManyToOne
    public Restaurant restaurant;

    public String shortHandId;

    @ManyToOne
    public Long confirmedCourier_id;

    /**
     * calculated delivery price for this order
     */
    public Money getDeliveryPrice() {
        return SystemCalc.getDeliveryPrice(this);
    }
    /**
     * @deprecated use getDeliveryPrice().toString
     * */
    public String getDeliveryPriceString() {
        return  getDeliveryPrice().toString();
    }

    /**
     * Grand total including delivery price and minus calculated user discount
     */
    public Money getGrandTotal() {

        Money menuTotal = getMenuTotal();
        return menuTotal.plus( getDeliveryPrice().minus(getUserDiscount().multipliedBy(menuTotal.getAmount(), RoundingMode.HALF_EVEN)));
    }
     /**
      * @deprecated use getGrandTotal().toString
      * */
    public String getGrandTotalString() {

        return getGrandTotal().toString();
    }

    /**
     * Just sum of all items without discount
     */
    public Money getMenuTotal() {
        Money i = Money.zero(CurrencyUnit.of("UAH"));
        for (OrderItem item : items) {
            i = i.plus( item.totalPriceInclComponents());
        }
        return i;
    }
    /*
    * @deprecated don't use this
    * */
    public String getMenuTotalString() {
         /*getMenuTotal();*/
        return getMenuTotal().toString();
    }

    /**
     * calculated discount for entire order
     */
    public Money getUserDiscount() {
        return SystemCalc.getUserDiscount(this);
    }

    public void setShortHandId(String id) {
        shortHandId = id;
    }

    /**
     * is called for index page
     */
    public String oneLineDescription() {
        StringBuilder b = new StringBuilder();
        for (Iterator<OrderItem> it = items.iterator(); it.hasNext(); ) {
            b.append(it.next().menuItem.name);
            if (it.hasNext()) {
                b.append(", ");
            } else {
                break;
            }
            if (b.length() > 240) {
                b.append("...");
                break;
            }
        }
        return b.toString();
    }

    public String aproxTime() {
        String time = "--";
        switch (orderStatus) {
            case ACCEPTED:
            case COOKED:
            case DELIVERING:
                long x = orderPlanedDeliveryTime == null ?
                        0 : orderPlanedDeliveryTime.getTime() - new Date().getTime();
                x = x / 1000 / 60;
                if (x > 0) {
                    return String.valueOf(x);
                } else {
                    return "00";
                }

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

    public String textStatus() {
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
                return "замовлення доставлено";
            default:
                break;
        }
        return "";
    }

}
