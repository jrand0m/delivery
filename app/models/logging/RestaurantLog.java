/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import models.Restaurant;
import play.db.jpa.Model;
import enumerations.LogActionType;
import enumerations.LogLevel;

/**
 * @author Mike
 * 
 */

@Table(name = "RestaurantLogs")
public class RestaurantLog extends Model {
	public static final class FIELDS {
		public static final String RESTAURANTLOG_RESTAURANT = "restaurant";
		public static final String RESTAURANTLOG_ACTIONTYPE = "actionType";
		public static final String RESTAURANTLOG_LEVEL = "level";
		public static final String RESTAURANTLOG_DATE = "date";
		public static final String RESTAURANTLOG_INFO = "info";
	}

	public Restaurant restaurant;
	@Enumerated(value = EnumType.STRING)
	public LogActionType actionType;
	@Enumerated(value = EnumType.STRING)
	public LogLevel level;

	public Date date;
	public String info;
}
