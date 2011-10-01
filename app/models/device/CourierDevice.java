/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@DiscriminatorValue("COURIER_DEVICE")
public class CourierDevice extends GenericDevice {
	public static final class FIELDS {
		// public static final String = "";
	}
}
