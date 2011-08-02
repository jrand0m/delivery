/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Unique;

/**
 * 
 * @author mike
 */

public class User extends Model {
    @Id(Generator.AUTO_INCREMENT)
    public Long id;
    @NotNull
    public String name;
    @NotNull
    public String surname;

    @Phone
    public String phoneNumber;
    public String miscInfo;
    @Email
    public String email;

    @NotNull
    public String login; // FIXME need to be unique, but how ? [Mike] use @PreInsert + @PreUpdate
    
    @NotNull
    @Password
    public String password;

    @NotNull
    public UserStatus userStatus;

    public Date joinDate;
    public Date lastLoginDate;

    public static enum UserStatus {
	ACTIVE, PENDING_APPROVEMENT, DELETED, ADMIN
    }
    public List<Address> getAddresses(){
	return Model.all(Address.class).filter("userId",this).filter("deleted", false).fetch();
    }

}
