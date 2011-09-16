/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;

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
	public Date date;
	public String info;
}
