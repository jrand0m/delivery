/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.User;
import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "UserLogs")
public class UserLog extends Model {
    public User user;
    public LOG_ACTION_TYPE actionType;
    public Date date;
    public String info;
}
