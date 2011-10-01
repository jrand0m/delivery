/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
public class RestaurantDescription extends Model {
	public static final class FIELDS {
		public static final String LANG = "lang";
		public static final String DESCRIPTION = "description";
		public static final String RESTAURANT = "restaurant";
	}

	public String lang;
	public String description;
	@ManyToOne
	public Restaurant restaurant;

}
