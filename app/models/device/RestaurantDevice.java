/**
 * 
 */
package models.device;

import java.util.Date;

import javax.persistence.DiscriminatorValue;

/**
 * @author Mike
 * 
 */

@DiscriminatorValue("RESTAURANT_DEVICE")
public class RestaurantDevice extends GenericDevice {
	public static final class FIELDS {
		public static final String RESTAURANTDEVICE_ENCRIPTIONKEY = "encriptionKey";
		public static final String RESTAURANTDEVICE_RESTAURANTID = "restaurantId";
	}

	// UUID to encrypt messages
	public String encriptionKey;
	public Date lastPing;

}
