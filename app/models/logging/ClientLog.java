/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import enumerations.LogActionType;

import models.Restaurant;
import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "ClientLogs")
public class ClientLog extends Model {
    public Restaurant client;
    public LogActionType actionType;
    public Date date;
    public String info;
}
