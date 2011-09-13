/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Courier;
import models.User;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "CourierLogs")
public class CourierLog {
    public Courier courier;
    public LOG_ACTION_TYPE actionType;
    public Date date;
    public String info;
}
