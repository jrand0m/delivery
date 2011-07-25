package models;

import siena.NotNull;

public class OrderItem extends MenuItem {
    @NotNull
    Integer count;
    /**
     * Archived real price from client (That was calculated in moment, when
     * order was approved).
     * */
    @NotNull
    public Integer orderItemUserPrice;
    /**
     * Archived price that should be paid by user (That was calculated in
     * moment, when order was approved).
     * */
    @NotNull
    public Integer orderItemPrice;
    @NotNull
    public Order orderId;
    public Boolean deleted = false;
}
