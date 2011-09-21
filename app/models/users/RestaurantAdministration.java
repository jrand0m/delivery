/**
 * 
 */
package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Mike
 *
 */
@Entity
@DiscriminatorValue("A")
public class RestaurantAdministration extends RestaurantUser {
    
}
