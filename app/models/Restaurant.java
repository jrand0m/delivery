/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.HashSet;
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
import play.db.jpa.Model;
import play.libs.Codec;
import play.libs.Crypto;

/**
 * 
 * @author mike
 * 
 */
@Entity
public class Restaurant extends Model {
    
    @OneToMany(mappedBy="client")
    public List <Comment> 	comments;

    public User 		user;
    
    /**
     * Updates by job at 4 oclock every day based on approved comments for past 30 days
     * */
    public Integer 		raiting;
    public String         	contactPerson;
    public String         	salt 		= Codec.UUID();
    @Phone
    public String         	contactPhone;
    public Double         discount;
    @OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public Set<MenuItem> 	menuBook 	= new HashSet<MenuItem>();
    public String title;
    @OneToOne
    public WorkHours      	workHours;
    
    /**
     * last request from client updated on request
     * */
    public Date 		lastConnection;
    
}
