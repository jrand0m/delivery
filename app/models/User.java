/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import siena.Id;
import siena.Model;
import siena.NotNull;

/**
 * 
 * @author mike
 */

public class User extends Model {
	@Id
	@NotNull
	public Long id;
	@NotNull
	public String name;
	@NotNull
	public String surname;

	@Phone
	@NotNull
	public String phoneNumber;
	public String miscInfo;
	@Email
	public String email;

	@NotNull
	public String login; // FIXME need to be unique, but how ?
	@NotNull
	@Password
	public String password;

	@NotNull
	public UserStatus userStatus;

	public Date joinDate;
	public Date lastLoginDate;

	public static enum UserStatus {
		ACTIVE, DELETED
	}

}
