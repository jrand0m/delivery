/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

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
    String address;
    List<MenuItem> menu;
    ClientStatus status;
    WorkHours workHours;
    String contactPerson;
    String contactPhone;
    Float discount;

    public static enum ClientStatus {

    }
}
