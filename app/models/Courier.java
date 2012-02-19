/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.device.CourierDevice;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
public class Courier extends Model {
	public static final class FIELDS {
		public static final String COURIER_DEVICE = "device";
	}
    @ManyToOne
	public CourierDevice device;
}
