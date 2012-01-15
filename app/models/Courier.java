/**
 * 
 */
package models;

import models.device.CourierDevice;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */

public class Courier extends Model {
	public static final class FIELDS {
		public static final String COURIER_DEVICE = "device";
	}

	public CourierDevice device;
}
