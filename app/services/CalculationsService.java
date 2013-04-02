package services;

import models.Order;
import org.joda.money.Money;

/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 4/2/13
 * Time: 4:20 PM
 */
public interface CalculationsService {
    /**
     * will first get order by id than call getMenuTotalFor
     * @return raw total(just sums all prices for items in order)
     * */
    Money getMenuTotalFor(Long orderId);

    /**
     * @return total to be paid by user including discounts, delivery, etc
     * */
    Money getOrderPriceTotalForUser(Long orderId);


    /**
     *
     * @return discount for order in money representation
     * */
    Money getDiscountForOrder(Order order);

    /**
     * will get order by id and call getDiscountForOrder
     * @return discount for order in money representation
     * */
    Money getDiscountForOrder(Long orderId);

    /**
     *
     * @return price for delivery
     */
    Money getDeliveryPrice(Long orderId);
}
