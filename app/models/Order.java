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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.Logger;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
@Table(name="Orders")
public class Order extends GenericModel {
    public static enum OrderStatus {
        OPEN(10), SENT(20), ACCEPTED(30), COOKED(40), DELIVERING(50),  DELIVERED(60),  DECLINED(0);
        private Integer i;
        OrderStatus(Integer ord){
            
        }
        public Integer getOrdinal(){
            return i;
        }
    }
    
    public static enum PaymentStatus {
        NOT_PAID, PAID, MANUAL_PAYMENT 
    }
    
//    private static final double GURANTEE_PROFIT_RATE = 1.3;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
    /**
     * Courier delivered order;
     * */
    public Date                 courierOrderClosed;
    /**
     * Courier picked up bundle
     * */
    public Date                 courierOrderRecieved;
    /**
     * Estimated time of delivery to client
     * */
    public Date                 courierPlanedDeliveryTime;
    public boolean              deleted              = false;
    @ManyToOne
    public Address              deliveryAddress;
    /**
     * Price calculated by application using formula, that should be paid by
     * user. value in coins
     * */
    public Integer              deliveryPrice;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    @Where(clause = "deleted = 0")
    public List<OrderItem>      items = new ArrayList<OrderItem>();

    /**
     * Order Created
     * */
    public Date                 orderCreated;
    /**
     * User order close date/time
     * */
    public Date                 orderClosed;
    
    /**
     * Time and date of order is accepted
     * */
    public Date                 orderDate;
    /**
     *  User who made this order 
     * */
    @ManyToOne
    public User                 orderOwner;
    
    public OrderStatus          orderStatus = OrderStatus.OPEN;
    public PaymentStatus        paymentStatus = PaymentStatus.NOT_PAID;
    /**
     * anonymous user id
     * generated by Codec.UUID();
     * */
    public String anonSID;
    public Client getClient(){
        return items.size()>0? items.get(0).menuItem.client :null;
    }
    
    /**
     * Function for getting short id 
     * */
    public String shortHandId(){
        if (id == null){
            Logger.warn("Taking shorthand on null id!");
            return null;
        }
        return id.substring(0, 8);
    }
    
    
    /**
     * Just sum of all items without discount
     * */
    public Integer getMenuTotal(){
        Integer i = 0;
        for (OrderItem item: items){
            i += item.orderItemUserPrice * item.count;
        }
        return i;
    }
    
    /**
     * calculated discount for entire order
     * */
    public Integer getUserDiscount(){
        
        return SystemCalc.getUserDiscount(this);
    }
    
    /**
     * calculated delivery price for this order
     * */
    public Integer getDeliveryPrice(){
        return SystemCalc.getDeliveryPrice(this);
    }
    
    /**
     * Grand total including delivery price and excluding user discount
     * */
    public Integer getGrandTotal(){

        Integer menuTotal = getMenuTotal();
        return menuTotal + getDeliveryPrice() - getUserDiscount()*menuTotal ;
    }

    /*
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
    } */

}
