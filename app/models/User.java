/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Table;

/**
 *
 * @author mike
 */
@Table("Users")
public class User extends Model{
    @Id
    Long id;
    String name;
    String surname;
    Date joinDate;
    Date lastLoginDate;
    String login;
    String password;
    UserStatus userStatus;
    List<Order> activeOrders;
    List<Order> orderHistory;
    List<Adress> addressBook;
    String phoneNumber;
    String miscInfo;
    
    public static enum UserStatus {

    }

    
}
