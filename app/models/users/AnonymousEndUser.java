/**
 * 
 */
package models.users;

import javax.persistence.Entity;

/**
 * @author Mike
 *
 */
@Entity
public class AnonymousEndUser extends EndUser {
    public String usid;
}
