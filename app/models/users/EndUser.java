/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import models.Address;
import models.Order;

import org.hibernate.annotations.Where;

import enumerations.UserRoles;

/**
 * 
 * @author mike
 */
@Entity
public class EndUser extends User {
    public static final class FIELDS {
        public static final String USER_ADDRESS_BOOK = "addressBook";
        public static final String USER_ORDER_BOOK = "orderBook";
    }
    @OneToMany(mappedBy = Address.FIELDS.ADDRESS_USER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Address> addressBook;
    @OneToMany(mappedBy = Order.FIELDS.ORDER_OWNER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Order> orderBook;
}
