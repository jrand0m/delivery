/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;

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
    public Date date;
    public String info;
}
