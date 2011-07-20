/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import siena.Id;
import siena.Model;

/**
 * 
 * @author mike
 */

public class Client extends Model {
    @Id
    public Long id;
    public String name;
    public String address;
    public ClientStatus status;
    public WorkHours workHours;
    public String contactPerson;
    public String contactPhone;
    public Float discount;

    public static enum ClientStatus {
	ACTIVE, DELETED
    }
}
