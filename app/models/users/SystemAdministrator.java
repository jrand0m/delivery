/**
 * 
 */
package models.users;

import play.mvc.Router;

/**
 * @author Mike
 * 
 */

public class SystemAdministrator extends BaseUser {
	public String lastloginip;

	@Override
	public String landingUrl() {
		return Router.getFullUrl("Admin.index");
	}
	
}
