/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.*;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.users.EndUser;
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
    @ManyToOne
	public EndUser user;
	@Enumerated(value = EnumType.STRING)
	public LogActionType actionType;
	@Enumerated(value = EnumType.STRING)
	public LogLevel level;
	public Date date;
	public String info;
}
