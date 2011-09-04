/**
 * 
 */
package helpers;

import models.Order;

/**
 * @author Mike
 *
 */
public class SystemCalc {

    /**
     * Calculates delivery price for order
     * @param order
     * @return delivery price
     */
    public static Integer getDeliveryPrice(Order order) {
        //FIXME create default storage
        return 2000;
    }

    /**
     * Calculates user discount for order
     * @param order
     * @return user discount
     */
    public static Integer getUserDiscount(Order order) {
        // FIXME NEED CALCULATION MODEL
        return 0;
    }

}
