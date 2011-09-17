/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.Courier;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@Table(name = "CourierLogs")
public class CourierLog extends Model
{
	public Courier courier;
	public LogActionType actionType;
	public LogLevel level;
	public Date date;
	public String info;
}
