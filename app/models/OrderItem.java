package models;

public class OrderItem extends MenuItem {
    Integer count;
    /**
     * Archived real price from client (That was calculated in moment, when
     * order was approved).
     * */
    public Integer orderItemUserPrice;
    /**
     * Archived price that should be paid by user (That was calculated in
     * moment, when order was approved).
     * */
    public Integer orderItemPrice;
    public Order orderId;
}
