/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.*;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.device.GenericDevice;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@Table(name = "DeviceLogs")
public class DeviceLog extends Model {
	public static final class FIELDS {
		public static final String DEVICELOG_DEVICE = "device";
		public static final String DEVICELOG_ACTIONTYPE = "actionType";
		public static final String DEVICELOG_LEVEL = "level";
		public static final String DEVICELOG_DATE = "date";
		public static final String DEVICELOG_INFO = "info";
	}
    @ManyToOne
	public GenericDevice device;
	@Enumerated(value = EnumType.STRING)
	public LogActionType actionType;
	@Enumerated(value = EnumType.STRING)
	public LogLevel level;
	public Date date;
	public String info;
}
