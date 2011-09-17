/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import models.Restaurant;

/**
 * @author Mike
 *
 */
@Entity
@DiscriminatorValue("RESTAURANT_DEVICE")
public class RestaurantDevice extends GenericDevice {
    //UUID to encrypt messages
    public String encriptionKey;
    public Long restaurantId;
    
}
