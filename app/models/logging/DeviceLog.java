/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

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
    public GenericDevice device ;
    public LogActionType actionType;
    public LogLevel level;
    public Date date;
    public String info;
}
