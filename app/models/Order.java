package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
@Table(name="Orders")
public class Order extends Model {
    public static enum OrderStatus {
        ACCEPTED, COOKED, DECLINED, DELIVERED, DELIVERING, OPEN
    }
    private static final double GURANTEE_PROFIT_RATE = 1.3;

    public Date                 clientPlanedFinish;
    public Date                 clientRealFinish;
    public Date                 clientRecieved;
    /**
     * Courier delivery time;
     * */
    public Date                 courierOrderClosed;
    public Date                 courierOrderRecieved;
    public Date                 courierPlanedDeliveryTime;
    public boolean              deleted              = false;
    public Address              deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */

    public Integer              deliveryPrice;
    //@OneToMany
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    public List<OrderItem>      items = new ArrayList<OrderItem>();

    /**
     * User order close date/time
     * */
    public Date                 orderClosed;

    public Date                 orderDate;
    public User                 orderOwner;
    
    public OrderStatus          orderStatus;

    private Integer getDeliveryCost() {
        return 20;
    }

    private Integer getDeliveryPrice() {
        int cost = getDeliveryCost() - (getTotalPrice() - getTotalCost());

        return cost < 0 ? 0 : getDeliveryCost();
    }

    public Integer getTotalCost() {
        Integer totalPrice = new Integer(0);
        for (OrderItem item : items) {
            totalPrice += item.orderItemUserPrice == null ? item.orderItemUserPrice = 0
                    : item.orderItemUserPrice * item.count;
        }
        return totalPrice;
    }

    public Integer getTotalPrice() {
        Integer totalPrice = new Integer(0);
        for (OrderItem item : items) {

            totalPrice += item.orderItemUserPrice == null ? item.orderItemUserPrice = 0
                    : item.orderItemUserPrice * item.count;
        }
        return totalPrice;
    }

}
