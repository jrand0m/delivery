/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

import siena.Id;
import siena.Model;

/**
 * 
 * @author mike
 */

public class User extends Model {
    @Id
    public Long id;
    public String name;
    public String surname;
    public Date joinDate;
    public Date lastLoginDate;
    public String login;
    public String password;
    public UserStatus userStatus;
    public String phoneNumber;
    public String miscInfo;

    public static enum UserStatus {
	ACTIVE, DELETED

    }

}
