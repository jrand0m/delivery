/**
 * 
 */
package models.users;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.device.CourierDevice;
import models.geo.City;

/**
 * @author Mike
 * 
 */
@Entity
public class CourierUser extends User {
	public Integer sallary = 0;
	public Integer gasPayment = 0;
	@ManyToOne
	public City city;
	@Override
	public String landingUrl() {
		return "/client/c";
	}

}
