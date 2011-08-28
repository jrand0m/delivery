/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.db.jpa.Model;

/**
 * 
 * @author mike
 */
@Entity
@Where(clause = "deleted = false")
@Inheritance(strategy=InheritanceType.JOINED)
public class User extends Model {
    public static enum UserRoles {
        ADMIN, CASHIER, CLIENT, COURIER, USER
    }

    public static enum UserStatus {
        ACTIVE, BANNED, PENDING_APPROVEMENT;
    }

    @OneToMany
    public List<Address> addressBook;

    public boolean       deleted = false;
    @Email
    public String        email;
    @Id
    @Column(name="USER_ID")
    public Long          id;

    public Date          joinDate;

    public Date          lastLoginDate;

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
