/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;

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
    public LogActionType actionType;
    public Date date;
    public String info;
}
