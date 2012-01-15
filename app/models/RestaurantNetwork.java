/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import play.i18n.Messages;

/**
 * @author Mike
 * 
 */

public class RestaurantNetwork extends Restaurant {
	public static final class FIELDS {
		public static final String RN_RESTORAUNTS = "restoraunts";
	}

	@OneToMany
	public List<Restaurant> restoraunts = new ArrayList<Restaurant>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see models.Restaurant#addressToString()
	 */
	@Override
	public String addressToString() {
		return Messages.get("restaurant.network.caption");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see models.Restaurant#workHoursToday()
	 */
	@Override
	public String workHoursToday() {
		// TODO
		return "todo";
	}
}
