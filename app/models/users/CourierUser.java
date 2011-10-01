/**
 * 
 */
package models.users;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import models.device.CourierDevice;

/**
 * @author Mike
 * 
 */
@Entity
public class CourierUser extends User {
	@OneToOne
	public CourierDevice device;
	public Integer sallary = 0;
	public Integer gasPayment = 0;

}
