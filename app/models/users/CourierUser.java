/**
 * 
 */
package models.users;

import javax.persistence.ManyToOne;

import models.geo.City;

/**
 * @author Mike
 * 
 */

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
