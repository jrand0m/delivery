/**
 * 
 */
package models;

import javax.persistence.Entity;

import models.device.CourierDevice;
import play.db.jpa.Model;


/**
 * @author Mike
 *
 */
@Entity
public class Courier extends Model {
    
    public CourierDevice device;
}
