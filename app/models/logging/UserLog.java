/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.User;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@Table(name = "UserLogs")
public class UserLog extends Model {

    public static final class FIELDS {
	public static final String USERLOG_USER = "user";
	public static final String USERLOG_ACTIONTYPE = "actionType";
	public static final String USERLOG_LEVEL = "level";
	public static final String USERLOG_DATE = "date";
	public static final String USERLOG_INFO = "info";
    }

    public User user;
    public LogActionType actionType;
    public LogLevel level;
    public Date date;
    public String info;
}
