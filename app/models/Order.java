package models;

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
import play.modules.guice.InjectSupport;
import services.OrderService;

import javax.inject.Inject;
import javax.persistence.*;
import java.math.RoundingMode;


@Table(name = "vd_orders")
@SequenceGenerator(name = "orders_seq_gen", sequenceName = "orders_seq")
@InjectSupport
public class Order {

    @Id
    @GeneratedValue(generator = "orders_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    /**
     * Message on declined status
     */
    @Column(name = "decline_message")
    public String declineMessage;
    @Column(name = "deleted")
    public boolean deleted = false;

    @Column(name = "delivery_address_id", nullable = false, updatable = false, insertable = false)
    public Long deliveryAddressId;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    @Deprecated
    public Address deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     */
    @Column(name = "delivery_price")
    public Money deliveryPrice;

    /**
     * Menu total price saved when order is accepted by user
     */
    @Column(name = "total_menu_price")
    public Money totalMenuPrice;


    /**
     * Time and date of order is accepted
     */
    @Column(name = "order_accepted")
    public LocalDateTime orderAccepted;
    /**
     * User order close date/time
     */
    @Column(name = "order_closed")
    public LocalDateTime orderClosed;
    /**
     * Order cooked
     */
    @Column(name = "order_cooked")
    public LocalDateTime orderCooked;
    /**
     * confirmed to be valid
     */
    @Column(name = "order_confirmed")
    public LocalDateTime orderConfirmed;
    /**
     * Order Created
     */
    @Column(name = "order_created")
    public LocalDateTime orderCreated;
    /**
     * Courier delivered order;
     */
    @Column(name = "order_delivered")
    public LocalDateTime orderDelivered;
    /**
     * Courier picked up bundle
     */
    @Column(name = "order_taken")
    public LocalDateTime orderTaken;

    @Column(name = "updated_at")
    public DateTime updatedAt;
    /**
     * is set + time told by client
     */
    @Column(name = "order_planed_cooked")
    public Period orderPlanedCooked;

    /**
     * Estimated time of delivery to restaurant
     */
    @Column(name = "order_planed_delivery_time")
    public Period orderPlanedDeliveryTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    public OrderStatus orderStatus = OrderStatus.OPEN;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    public PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    /**
     * User who made this order
     */
    @Column(name = "order_owner_id")
    public Long orderOwnerId;
    @ManyToOne
    @JoinColumn(name = "order_owner_id")
    @Deprecated
    public User orderOwner;

    @Column(name = "restaurant_id")
    public Integer restaurantId;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;
    @Column(name = "confirmed_courier_id")
    public Long confirmedCourierId;
    @ManyToOne
    @JoinColumn(name = "confirmed_courier_id")
    @Deprecated
    public User confirmedCourier;

    @Inject
    @Deprecated
    private static OrderService service;

    /**
     * calculated delivery price for this order
     */
    public Money getDeliveryPrice() {
        return SystemCalc.getDeliveryPrice(this);
    }

    /**
     * @deprecated use getDeliveryPrice().toString
     */
    public String getDeliveryPriceString() {
        return getDeliveryPrice().toString();
    }

    /**
     * Grand total including delivery price and minus calculated user discount
     */
    public Money getGrandTotal() {

        Money menuTotal = getMenuTotal();
        return menuTotal.plus(getDeliveryPrice().minus(getUserDiscount().multipliedBy(menuTotal.getAmount(), RoundingMode.HALF_EVEN)));
    }

    /**
     * @deprecated use getGrandTotal().toString
     */
    public String getGrandTotalString() {

        return getGrandTotal().toString();
    }

    /**
     * Just sum of all items without discount
     */
    public Money getMenuTotal() {
        //todo extract to outer method
        //if (true) throw new UnsupportedOperationException("extract order.menuTotal to service");
        Money i = Money.zero(CurrencyUnit.of("UAH"));
        for (OrderItem item : service.getItems(this)) {
            i = i.plus(item.totalPriceInclComponents());
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

    /**
     * is called for index page
     */
    public String oneLineDescription() {
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
