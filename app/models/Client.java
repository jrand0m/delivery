/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;



import org.hibernate.annotations.Where;

import play.data.validation.Phone;
import play.libs.Codec;
import play.libs.Crypto;

/**
 * 
 * @author mike
 */
@Entity
@DiscriminatorValue("CLIENT_USER")
public class Client extends User {

    public String         contactPerson;
    public String         salt = Codec.UUID();
    @Phone
    public String         contactPhone;
    public Double         discount;
    @OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public Set<MenuItem> menuBook;
    public String title;
    @OneToOne
    public WorkHours      workHours;
    
    public Client() {
        userStatus = UserStatus.ACTIVE;
        role = UserRoles.CLIENT;
    }
    
    /**
     * last request from client
     * */
    public Date lastConnection;
    
}
