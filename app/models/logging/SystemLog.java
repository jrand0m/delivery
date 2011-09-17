/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
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
    public static final class FIELDS{
	public static final String ACTIONTYPE = "actionType";
	public static final String LEVEL = "level";
	public static final String DATE = "date";
	public static final String INFO= "info";
}
    
    public LogActionType actionType;
    public LogLevel level;
    public Date date;
    public String info;
}
