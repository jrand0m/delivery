/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;

/**
 * @author Mike
 * 
 */

@DiscriminatorValue("USER_DEVICE")
public class UserDevice extends GenericDevice {
	public static final class FIELDS {
		// public static final String = "";
	}
}
