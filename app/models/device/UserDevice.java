/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Mike
 *
 */
@Entity
@DiscriminatorValue("USER_DEVICE")
public class UserDevice extends GenericDevice {

}