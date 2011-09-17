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
    public static final class FIELDS{
	public static final String ENCRIPTIONKEY= "encriptionKey";
	public static final String RESTAURANTID= "restaurantId";
}
    //UUID to encrypt messages
    public String encriptionKey;
    public Long restaurantId;
    
}
