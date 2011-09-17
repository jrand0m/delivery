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
    public LogActionType actionType;
    public LogLevel level;
    public Date date;
    public String info;
}
