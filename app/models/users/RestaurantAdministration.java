/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorValue;

import play.mvc.Router;

/**
 * @author Mike
 * 
 */

@DiscriminatorValue("A")
public class RestaurantAdministration extends RestaurantUser {
	@Override
	public String landingUrl() {
		return Router.getFullUrl("RestaurantAdmin.summary()");
	}
}
