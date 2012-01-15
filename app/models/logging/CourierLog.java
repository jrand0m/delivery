/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import models.Courier;
import play.db.jpa.Model;
import enumerations.LogActionType;
import enumerations.LogLevel;

/**
 * @author Mike
 * 
 */

@Table(name = "CourierLogs")
public class CourierLog extends Model {
	public static final class FIELDS {
		public static final String RESTAURANT_DEVICE_COURIER = "courier";
		public static final String RESTAURANT_DEVICE_ACTIONTYPE = "actionType";
		public static final String RESTAURANT_DEVICE_LEVEL = "level";
		public static final String RESTAURANT_DEVICE_DATE = "date";
		public static final String RESTAURANT_DEVICE_INFO = "info";
	}

	public Courier courier;
	@Enumerated(value = EnumType.STRING)
	public LogActionType actionType;
	@Enumerated(value = EnumType.STRING)
	public LogLevel level;
	public Date date;
	public String info;
}
