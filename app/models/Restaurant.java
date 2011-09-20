/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.PropertyVault;

import java.util.ArrayList;
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

import models.device.RestaurantDevice;
import models.geo.Address;
import models.settings.SystemSetting;
import models.users.EndUser;
import models.users.RestaurantUser;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;

import play.cache.Cache;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Phone;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.i18n.Lang;
import play.i18n.Messages;
import play.libs.Codec;

/**
 * 
 * @author mike
 * 
 */
@Entity
public class Restaurant extends Model {
    public static class FIELDS {
	public static final String RESTAURANT_COMMENTS = "comments";
	public static final String RESTAURANT_USERS = "users";
	public static final String RESTAURANT_RAITING = "raiting";
	public static final String RESTAURANT_CONTACT_PERSON = "contactPerson";
	public static final String RESTAURANT_DISCOUNT = "discount";
	public static final String RESTAURANT_MENU_BOOK = "menuBook";
	public static final String RESTAURANT_TITLE = "title";
	public static final String RESTAURANT_WORK_HOURS = "workHours";
	// /public static final String RESTAURANT_LAST_CONNECTION =
	// "lastConnection";
	public static final String RESTAURANT_LOGO = "logo";
	public static final String RESTAURANT_CITY = "city";
	public static final String RESTAURANT_DEVICE= "device";
	public static final String DESCRIPTIONS="descriptions";
    }
    public Address address;
    /**
     * All comments given to this restaurant
     * */
    @OneToMany(mappedBy = "restaurant",fetch=FetchType.LAZY)
    public List<Comment> comments;
    /**
     * logo image , ATTENTION! stores in /attachents/ dir
     * */
    public Blob logo;
    /**
     * Associated device
     * */
    @OneToOne
    public RestaurantDevice device;
    /**
     * loginable users
     * */
    @OneToMany(fetch=FetchType.LAZY )
    public List<RestaurantUser> users;
    /**
     * Zip of city in witch Restaurant resides
     * */
    @Max(100000)
    @Min(999)
    public Integer cityZip;
    /**
     * Updates by job at 4 o'clock every day based on approved comments for past
     * 30 days
     * */
    public Integer raiting;
    /**
     * Name of contact person to contact( assigned by Restaurant through admin
     * i-face)
     * */
    public String contactPerson;
    public String salt = Codec.UUID();
    /**
     * Fone of contact person (assigned by Restaurant through admin i-face)
     * */
    @Phone
    public String contactPhone;
    /**
     * XXX should i store it here ? Restaurant setting ?
     * */
    public Double discount;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<MenuItem> menuBook = new HashSet<MenuItem>();
    /**
     * Restaurant Title
     * */
    public String title;
    
    @OneToMany(mappedBy=RestaurantDescription.FIELDS.RESTAURANT)
    public List<RestaurantDescription> descriptions = new ArrayList<RestaurantDescription>();
    
    @OneToOne(fetch = FetchType.LAZY)
    public WorkHours workHours;
    
    public boolean isOnline(){
	Boolean online = (Boolean) Cache.get("isOnline_"+getId());
	if (online==null){
	    long waitTimeInMiliseconds = Long.parseLong(PropertyVault.getSystemValueFor("pingtime"));
	    online = System.currentTimeMillis()- device.lastPing.getTime()<waitTimeInMiliseconds;
	    long waitTimeInMin = (waitTimeInMiliseconds/1000/60)+1;
	    
	    Cache.set("isOnline_"+getId(), online, waitTimeInMin+"mn");
	}
	return online;
    }
    public String description(){
	String lang = Lang.get();
	for (RestaurantDescription desc:descriptions){
	    if (desc.lang.equalsIgnoreCase(lang)){
		return desc.description;
	    }
	}
	
	return Messages.get("restaurant.nodescription");
	
    }
    public String addressToString(){
	return address.toString();
    }
    public String workHoursToday(){
	return workHours.today();
    }
    public String isOnlineAsString(){
	return isOnline() ? Messages.get("restaurant.online"): Messages.get("restaurant.offline");
    }
    
}
