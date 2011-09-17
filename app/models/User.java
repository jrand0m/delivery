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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="USER_CLASS",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("GENERIC_USER")
public class User extends Model {
public static final class FIELDS{
	public static final String ADDRESSBOOK = "addressBook";
	public static final String ORDERBOOK = "orderBook";
	public static final String DELETED = "deleted";
	public static final String EMAIL = "email";
	public static final String JOINDATE = "joinDate";
	public static final String LASTLOGINDATE = "lastLoginDate";
	public static final String MISCINFO = "miscInfo";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String PHONENUMBER = "phoneNumber";
	public static final String ROLE = "role";
	public static final String SURNAME = "surname";
	public static final String USERSTATUS = "userStatus";
}
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public List<Address> addressBook;
    @OneToMany(mappedBy="orderOwner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public List<Order>   orderBook;
    
    public boolean       deleted = false;
    @Email
    public String        email;

    public Date          joinDate;

    public Date          lastLoginDate;
    
    @Required
    public String        login;          // FIXME need to be unique, but how ?
                                          // [Mike] use
    // @PreInsert + @PreUpdate

    public String        miscInfo;

    public String        name;

    @Password
    public String        password;
    @Phone
    public String        phoneNumber;
    public UserRoles     role;

    public String        surname;

    public UserStatus    userStatus;

}
