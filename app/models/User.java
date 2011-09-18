/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;
import enumerations.UserRoles;
import enumerations.UserStatus;

/**
 * 
 * @author mike
 */
@Entity
@Where(clause = "deleted = 0")
public class User extends Model {
    public static final class HQL {
	public static final String BY_LOGIN = User.FIELDS.USER_LOGIN + " = ?";
	public static final String BY_LOGIN_OR_EMAIL = User.FIELDS.USER_LOGIN
		+ " = ? or " + User.FIELDS.USER_EMAIL + " = ?";;
    }

    public static final class FIELDS {
	public static final String USER_ADDRESS_BOOK = "addressBook";
	public static final String USER_ORDER_BOOK = "orderBook";
	public static final String USER_DELETED = "deleted";
	public static final String USER_EMAIL = "email";
	public static final String USER_JOIN_DATE = "joinDate";
	public static final String USER_LOGIN = "login";
	public static final String USER_LAST_LOGIN_DATE = "lastLoginDate";
	public static final String USER_MISC_INFO = "miscInfo";
	public static final String USER_NAME = "name";
	public static final String USER_PASSWORD = "password";
	public static final String USER_PHONENUMBER = "phoneNumber";
	public static final String USER_ROLE = "role";
	public static final String USER_SURNAME = "surname";
	public static final String USER_USERS_TATUS = "userStatus";
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Address> addressBook;
    @OneToMany(mappedBy = "orderOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Order> orderBook;

    public boolean deleted = false;
    @Email
    public String email;

    public Date joinDate;

    public Date lastLoginDate;

    @Required
    public String login; // FIXME need to be unique, but how ?
			 // [Mike] use
    // @PreInsert + @PreUpdate

    public String miscInfo;

    public String name;

    @Password
    public String password;
    @Phone
    public String phoneNumber;
    public UserRoles role;

    public String surname;

    public UserStatus userStatus;

}
