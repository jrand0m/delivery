/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.User;
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
    public LOG_ACTION_TYPE actionType;
    public Date date;
    public String info;
}
