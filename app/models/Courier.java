/**
 * 
 */
package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.db.jpa.Model;

import models.device.CourierDevice;


/**
 * @author Mike
 *
 */
@Entity
public class Courier extends Model {
    
    public CourierDevice device;
}
