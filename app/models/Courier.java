/**
 * 
 */
package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * @author Mike
 *
 */
@Entity
@DiscriminatorValue("COURIER_USER")
public class Courier extends User {

}
