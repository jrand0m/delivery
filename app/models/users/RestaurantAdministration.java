/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.mvc.Router;

/**
 * @author Mike
 * 
 */
@Entity
@DiscriminatorValue("A")
public class RestaurantAdministration extends RestaurantUser {
	@Override
	public String landingUrl() {
		return Router.getFullUrl("RestaurantAdmin.summary()");
	}
}
