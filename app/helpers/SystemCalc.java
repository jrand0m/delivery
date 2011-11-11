/**
 * 
 */
package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import models.Order;

/**
 * @author Mike
 * 
 */
public class SystemCalc {

	/**
	 * Calculates delivery price for order
	 * 
	 * @param order
	 * @return delivery price
	 */
	public static Integer getDeliveryPrice(Order order) {
		// FIXME create default storage
		return 1500;
	}

	/**
	 * Calculates user discount for order
	 * 
	 * @param order
	 * @return user discount in 1 - 100%; 0.01 - 1%
	 */
	public static BigDecimal getUserDiscount(Order order) {
		// FIXME NEED CALCULATION MODEL
		return new BigDecimal(0.0F).setScale(2, RoundingMode.HALF_EVEN);
	}

}
