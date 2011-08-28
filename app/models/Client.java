/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


import org.hibernate.annotations.Where;

import play.data.validation.Phone;

/**
 * 
 * @author mike
 */
@Entity
@PrimaryKeyJoinColumn(name="USER_ID")
@Where(clause = "deleted = false")
public class Client extends User {

    public String         contactPerson;

    @Phone
    public String         contactPhone;
    public Double         discount;
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    public Set<MenuItem> menuBook;
    public String         title;
    public WorkHours      workHours;

    public Client() {
        userStatus = UserStatus.ACTIVE;
    }
}
