/**
 * 
 */
package models.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Mike
 *
 */
@Entity
@DiscriminatorValue("CLIENT_DEVICE")
public class ClientDevice extends GenericDevice {

}
