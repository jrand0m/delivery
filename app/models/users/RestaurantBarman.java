/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Mike
 * 
 */
@Entity
@DiscriminatorValue("B")
public class RestaurantBarman extends RestaurantUser {

	@Override
	public String landingUrl() {
		return "/client/restaurant/web/main.html";
	}

}
