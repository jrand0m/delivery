/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import play.db.jpa.Model;
import enumerations.LogActionType;
import enumerations.LogLevel;

/**
 * @author Mike
 * 
 */
@Entity
@Table(name = "SystemLogs")
public class SystemLog extends Model {
    public static final class FIELDS {
	public static final String SYSTEMLOG_ACTIONTYPE = "actionType";
	public static final String SYSTEMLOG_LEVEL = "level";
	public static final String SYSTEMLOG_DATE = "date";
	public static final String SYSTEMLOG_INFO = "info";
    }
    @Enumerated(value = EnumType.STRING)
    public LogActionType actionType;
    @Enumerated(value = EnumType.STRING)
    public LogLevel level;
    public Date date;
    public String info;
}
