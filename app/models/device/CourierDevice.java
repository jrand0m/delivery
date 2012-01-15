/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;

/**
 * @author Mike
 * 
 */

@DiscriminatorValue("COURIER_DEVICE")
public class CourierDevice extends GenericDevice {
	public static final class FIELDS {
		// public static final String = "";
	}
}
