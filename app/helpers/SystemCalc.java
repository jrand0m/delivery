/**
 *
 */
package helpers;

import models.Order;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * @author Mike
 */
public class SystemCalc {

    /**
     * Calculates delivery price for order
     *
     * @param order
     * @return delivery price
     */
    public static Money getDeliveryPrice(Order order) {
        Money delivery = Money.of(CurrencyUnit.of("UAH"), 15);
        if (order.getMenuTotal().isGreaterThan(Money.of(CurrencyUnit.of("UAH"), 150))) {
            delivery = Money.zero(CurrencyUnit.of("UAH"));
        }
        return delivery;
    }

    /**
     * Calculates user discount for order
     *
     * @param order
     * @return user discount in 1 - 100%; 0.01 - 1%
     */
    public static Money getUserDiscount(Order order) {
        //TODO NEED CALCULATION MODEL
        return Money.zero(CurrencyUnit.of("UAH"));
    }

}
