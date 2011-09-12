/**
 * 
 */
package models.logging;

import java.util.Date;

import javax.persistence.Entity;

import models.User;

import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
public class UserLog extends Model {
    public User user;
    public ACTION_TYPE actionType;
    public enum ACTION_TYPE {
        
    }
    public Date date;
    public String info;
    public String info2;
}
