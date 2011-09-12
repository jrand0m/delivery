/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

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
    public static enum UserRoles {
        ADMIN, CASHIER, CLIENT, COURIER, USER
    }

    public static enum UserStatus {
        ACTIVE, BANNED, PENDING_APPROVEMENT;
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
