/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.client;

import models.User;
import play.data.validation.Phone;
import siena.Id;
import siena.Model;
import siena.NotNull;

/**
 * 
 * @author mike
 */

public class Client extends Model {
    @Id
    public Long id;
    @NotNull
    public String name;
    @NotNull
    public String address;
    public ClientStatus status;
    public WorkHours workHours;
    public String contactPerson;
    @Phone
    public String contactPhone;
    public Float discount;

    /**
     * User who has rights to administer this account
     * */
    public User clientUser;
    public Boolean deleted = false;

    public static enum ClientStatus {
	ACTIVE, DELETED
    }
}
