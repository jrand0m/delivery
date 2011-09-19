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

import models.device.RestaurantDevice;
import models.users.User;

import org.hibernate.annotations.Where;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Phone;
import play.db.jpa.Blob;
import play.db.jpa.Model;
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
	public static final String RESTAURANT_USER = "user";
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
    }

    @OneToMany(mappedBy = "restaurant")
    public List<Comment> comments;
    public Blob logo;
    /**
     * Asociated device
     * */
    @OneToOne
    public RestaurantDevice device;
    /**
     * loginable user
     * */
    @OneToOne
    public User user;
    @ManyToOne
    public City city;
    /**
     * Updates by job at 4 oclock every day based on approved comments for past
     * 30 days
     * */
    public Integer raiting;
    /**
     * Name of contact person to contact( assigned by Restoraunt through admin
     * i-face)
     * */
    public String contactPerson;
    public String salt = Codec.UUID();
    /**
     * Fone of contact person (assigned by restoraunt through admin i-face)
     * */
    @Phone
    public String contactPhone;
    /**
     * XXX should i store it here ?
     * */
    public Double discount;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<MenuItem> menuBook = new HashSet<MenuItem>();
    /**
     * Restoraunt Title
     * */
    public String title;
    @OneToOne(fetch = FetchType.LAZY)
    public WorkHours workHours;

}
