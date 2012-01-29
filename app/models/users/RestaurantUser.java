/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import models.Restaurant;

/**
 * @author Mike
 * 
 */

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = RestaurantUser.FIELDS.DISCRIMINATOR, discriminatorType = DiscriminatorType.CHAR)
public abstract class RestaurantUser extends BaseUser {
	public static class FIELDS {
		public static final String RESTAURANT = "restaurant";
		public static final String DISCRIMINATOR = "REST_ROLE";
	}

	@ManyToOne
	public Restaurant restaurant;
}
