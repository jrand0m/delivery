/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;
import enumerations.LogLevel;

import models.Restaurant;
import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "RestaurantLogs")
public class RestaurantLog extends Model {
    public Restaurant restaurant;
    public LogActionType actionType;
    public LogLevel level;
    
    public Date date;
    public String info;
}
