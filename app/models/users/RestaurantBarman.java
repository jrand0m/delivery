/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorValue;

/**
 * @author Mike
 * 
 */

@DiscriminatorValue("B")
public class RestaurantBarman extends RestaurantUser {

	@Override
	public String landingUrl() {
		return "/client/r";
	}
}
