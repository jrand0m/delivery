package models;

import com.avaje.ebean.annotation.EmbeddedColumns;
import enumerations.OrderStatus;
import enumerations.PaymentStatus;
import helpers.SystemCalc;
import models.geo.Address;
import models.users.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import play.Logger;
import play.db.ebean.Model;

import javax.persistence.*;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Table(name = "vd_order")
@SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_seq")
public class Order extends Model {

    @Id
    @GeneratedValue(generator = "orders_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    /**
     * Message on declined status
     */
    @Column(name = "declineMessage")
    public String declineMessage;
    @Column(name = "deleted")
    public boolean deleted = false;

    @Column(name = "delivery_address_id", nullable = false, updatable = false, insertable = false)
    public Long delivery_address_id;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    @Deprecated
    public Address deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     */
    @EmbeddedColumns(columns = "currency=deliveryPrice_currency, cents=deliveryPrice")
    public Money deliveryPrice;

    /**
     * Menu total price saved when order is accepted by user
     */
    @EmbeddedColumns(columns = "currency=totalMenuPrice_currency, cents=totalMenuPrice")
    public Money totalMenuPrice;


    /**
     * Time and date of order is accepted
     */
    @Column(name = "orderAccepted")
    public LocalDateTime orderAccepted;
    /**
     * User order close date/time
     */
    @Column(name = "orderClosed")
    public LocalDateTime orderClosed;
    /**
     * Order cooked
     */
    @Column(name = "orderCooked")
    public LocalDateTime orderCooked;
    /**
     * confirmed to be valid
     */
    @Column(name = "orderConfirmed")
    public LocalDateTime orderConfirmed;
    /**
     * Order Created
     */
    @Column(name = "orderCreated")
    public LocalDateTime orderCreated;
    /**
     * Courier delivered order;
     */
    @Column(name = "orderDelivered")
    public LocalDateTime orderDelivered;
    /**
     * Courier picked up bundle
     */
    @Column(name = "orderTaken")
    public LocalDateTime orderTaken;

    @Column(name = "updatedat")
    public DateTime updatedAt;
    /**
     * is set + time told by client
     */
    @Column(name = "orderPlanedCooked")
    public Period orderPlanedCooked;

    /**
     * Estimated time of delivery to restaurant
     */
    @Column(name = "orderPlanedDeliveryTime")
    public Period orderPlanedDeliveryTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "orderStatus")
    public OrderStatus orderStatus = OrderStatus.OPEN;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "paymentStatus")
    public PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    /**
     * User who made this order
     */
    @Column(name = "order_owner_id")
    public UUID order_owner_id;
    @ManyToOne
    @JoinColumn(name = "order_owner_id")
    @Deprecated
    public User orderOwner;

    @Column(name = "restaurant_id")
    public Integer restaurant_id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;
    @Column(name = "confirmed_courier_id")
    public UUID confirmed_courier_id;
    @ManyToOne
    @JoinColumn(name = "confirmed_courier_id")
    @Deprecated
    public User confirmedCourier;

    /**
     * calculated delivery price for this order
     */
    public Money getDeliveryPrice() {
        Logger.debug("<- Called!");

        return SystemCalc.getDeliveryPrice(this);
    }

    /**
     * @deprecated use getDeliveryPrice().toString
     */
    public String getDeliveryPriceString() {
        Logger.debug("<- Called!");
        return getDeliveryPrice().toString();
    }

    /**
     * Grand total including delivery price and minus calculated user discount
     */
    public Money getGrandTotal() {
        Logger.debug("<- Called!");
        Money menuTotal = getMenuTotal();
        return menuTotal.plus(getDeliveryPrice().minus(getUserDiscount().multipliedBy(menuTotal.getAmount(), RoundingMode.HALF_EVEN)));
    }

    /**
     * @deprecated use getGrandTotal().toString
     */
    public String getGrandTotalString() {
        Logger.debug("<- Called!");
        return getGrandTotal().toString();
    }

    /**
     * Just sum of all items without discount
     */
    public Money getMenuTotal() {
        Logger.debug("<- Called!");
        //todo extract to outer method
        //if (true) throw new UnsupportedOperationException("extract order.menuTotal to service");
        Money i = Money.zero(CurrencyUnit.of("UAH"));
        //for (OrderItem item : service.getAll(this)) {
        //    i = i.plus(item.totalPriceInclComponents());
        //}
        //return i;
        throw new UnsupportedOperationException("TODO: implement get all subsequent order items");
    }

    /*
   * @deprecated don't use this
   * */
    public String getMenuTotalString() {
        /*getMenuTotal();*/
        Logger.debug("<- Called!");
        return getMenuTotal().toString();
    }

    /**
     * calculated discount for entire order
     */
    public Money getUserDiscount() {
        Logger.debug("<- Called!");
        return SystemCalc.getUserDiscount(this);
    }

    /**
     * is called for index page
     */
    public String oneLineDescription() {
        Logger.debug("<- Called!");
        StringBuilder b = new StringBuilder("to be done");
        /*for (Iterator<OrderItem> it = items.iterator(); it.hasNext(); ) {
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
        }*/
        return b.toString();
    }

    public String aproxTime() {
        Logger.debug("<- Called!");
        String time = "--";
        int x = 0;
        switch (orderStatus) {
            case ACCEPTED:
                x = new Period(orderAccepted.plus(orderPlanedCooked), new LocalDateTime()).getMinutes();
            case COOKED:
            case DELIVERING:
                x = new Period(orderAccepted.plus(orderPlanedCooked).plus(orderPlanedDeliveryTime), new LocalDateTime()).getMinutes();
                break;
            case DELIVERED:
            case CONFIRMED:
            case DECLINED:
            case RECIEVED:
            case SENT:

            default:
                return time;

        }

        if (x > 0) {
            return String.valueOf(x);
        } else {
            return "00";
        }


    }

    public String textStatus() {
        Logger.debug("<- Called!");
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
