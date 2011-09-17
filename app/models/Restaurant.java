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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Phone;
import play.db.jpa.Model;
import play.libs.Codec;

/**
 * 
 * @author mike
 * 
 */
@Entity
public class Restaurant extends Model {
    public static final class FIELDS{
	public static final String COMMENTS= "comments";
	public static final String USER= "user";
	public static final String RAITING= "raiting";
	public static final String CONTACTPERSON= "contactPerson";
	public static final String DISCOUNT= "discount";
	public static final String MENUBOOK= "menuBook";
	public static final String TITLE= "title";
	public static final String WORKHOURS= "workHours";
	public static final String LASTCONNECTION= "lastConnection";
}
    @OneToMany(mappedBy="restaurant")
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
    public Double         	discount;
    @OneToMany(mappedBy="reataurant", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    public Set<MenuItem> 	menuBook 	= new HashSet<MenuItem>();
    public String 		title;
    @OneToOne(fetch=FetchType.LAZY)
    public WorkHours      	workHours;
    
    /**
     * last request from restaurant updated on request
     * */
    public Date 		lastConnection;
    
}
