/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import siena.Column;
import siena.Id;
import siena.Model;
import siena.Table;

/**
 *
 * @author mike
 */
@Table("Clients")
public class Client extends Model{
    @Id
    @Column("id")
    public Integer id;
    
    @Column("client_name")
    public String name;
    
}
