/**
 * 
 */
package models.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.Where;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@Where(clause = "deleted = 0")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends Model {

	public static final class HQL {
		public static final String BY_LOGIN = User.FIELDS.LOGIN + " = ?";
		public static final String BY_LOGIN_OR_EMAIL = User.FIELDS.LOGIN
				+ " = ? or " + User.FIELDS.EMAIL + " = ?";
	}

	public static final class FIELDS {
		public static final String ADDRESS_BOOK = "addressBook";
		public static final String ORDER_BOOK = "orderBook";
		public static final String DELETED = "deleted";
		public static final String EMAIL = "email";
		public static final String JOIN_DATE = "joinDate";
		public static final String LOGIN = "login";
		public static final String LAST_LOGIN_DATE = "lastLoginDate";
		public static final String NAME = "usr_name";
		public static final String PASSWORD = "password";
		public static final String PHONENUMBER = "phoneNumber";
		public static final String SURNAME = "usr_surname";
		public static final String USERS_TATUS = "userStatus";
	}

	public boolean deleted = false;
	@Email
	public String email;
	public Date joinDate;
	public Date lastLoginDate;
	@Required
	public String login;
	@Required
	public String usr_name;
	public String usr_surname;
	@Password
	public String password;
	@Phone
	@Required
	public String phoneNumber;

	/**
     * 
     */
	public User() {
		super();
	}

	/**
	 * url that will be redirected to after login;
	 * @return relative url or null if no such preference
	 * */
	abstract public String landingUrl();

}