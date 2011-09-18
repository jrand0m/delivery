/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.Courier;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
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
    public LogActionType actionType;
    public LogLevel level;
    public Date date;
    public String info;
}
