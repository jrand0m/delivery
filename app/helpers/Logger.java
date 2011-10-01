/**
 * 
 */
package helpers;

import java.util.Date;

import enumerations.LogActionType;
import enumerations.LogLevel;
import models.Restaurant;
import models.device.CourierDevice;
import models.device.GenericDevice;
import models.device.RestaurantDevice;
import models.device.UserDevice;
import models.logging.DeviceLog;
import models.logging.SystemLog;

/**
 * @author Mike TODO Write logger wrapper!
 */
public class Logger {
	public static void logDevicePing(GenericDevice device) {
		DeviceLog log = new DeviceLog();
		log.device = device;
		log.actionType = LogActionType.PING;
		log.level = LogLevel.INFO;
		log.date = new Date();
		log.info = "access time log";
		log.create();
		play.Logger.debug("Device %s ping logged", device.id);
	}

	public static void logSystemInfo(LogActionType type, String message,
			Object... objects) {
		play.Logger.info(message, objects);
		SystemLog syslog = new SystemLog();
		syslog.actionType = type;
		syslog.date = new Date();
		syslog.level = LogLevel.INFO;
		syslog.info = String.format(message, objects);
		syslog.create();
	}
	public static void logSystemWarn(LogActionType type, String message,
			Object... objects) {
		play.Logger.warn(message, objects);
		SystemLog syslog = new SystemLog();
		syslog.actionType = type;
		syslog.date = new Date();
		syslog.level = LogLevel.WARN;
		syslog.info = String.format(message, objects);
		syslog.create();
	}
	public static void logSystemDebug(LogActionType type, String message,
			Object... objects) {
		play.Logger.debug(message, objects);
		SystemLog syslog = new SystemLog();
		syslog.actionType = type;
		syslog.date = new Date();
		syslog.level = LogLevel.DEBUG;
		syslog.info = String.format(message, objects);
		syslog.create();
	}
	public static void logSystemError(LogActionType type, String message,
			Object... objects) {
		play.Logger.error(message, objects);
		SystemLog syslog = new SystemLog();
		syslog.actionType = type;
		syslog.date = new Date();
		syslog.level = LogLevel.ERROR;
		syslog.info = String.format(message, objects);
		syslog.create();
	}
}
