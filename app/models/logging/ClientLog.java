/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Client;
import models.User;
import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@Table(name = "ClientLogs")
public class ClientLog extends Model {
    public Client client;
    public LOG_ACTION_TYPE actionType;
    public Date date;
    public String info;
}
