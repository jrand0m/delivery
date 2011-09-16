/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Restaurant;
import models.User;
import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "ClientLogs")
public class ClientLog extends Model {
    public Restaurant client;
    public LOG_ACTION_TYPE actionType;
    public Date date;
    public String info;
}
