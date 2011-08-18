/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import play.data.validation.Phone;
import siena.NotNull;

/**
 * 
 * @author mike
 */

public class Client extends User {
    @NotNull
    public String title;

    public WorkHours workHours;
    public String contactPerson;
    @Phone
    public String contactPhone;
    public Double discount;

    public Client() {
	userStatus = UserStatus.ACTIVE;
    }
}
