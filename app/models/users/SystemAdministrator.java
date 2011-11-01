/**
 * 
 */
package models.users;

import javax.persistence.Entity;

import play.mvc.Router;

/**
 * @author Mike
 * 
 */
@Entity
public class SystemAdministrator extends User {
	public String lastloginip;

	@Override
	public String landingUrl() {
		return Router.getFullUrl("Admin.index");
	}
	
}
