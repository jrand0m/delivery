/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import models.geo.Address;
import models.geo.City;
import models.time.WorkHours;
import models.users.User;
import org.joda.time.DateTime;
import play.Logger;
import play.cache.Cache;
import play.db.jpa.Blob;
import play.i18n.Messages;

import javax.persistence.*;

/**
 * @author mike
 */
<<<<<<< HEAD
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
		public static final String RESTAURANT_DEVICE = "device";
		public static final String DESCRIPTIONS = "descriptions";
		public static final String DELETED = "deleted";
		public static final String SHOW_ON_INDEX = "showOnIndex";
		public static final String CATEGORY = "category";
		
		public static final String TWO_LETTERS = "twoLetters";
	}
	
	public static class HQL {
		public static final String BY_CITY_AND_SHOW_ON_INDEX = FIELDS.RESTAURANT_CITY + " = ? and " +FIELDS.SHOW_ON_INDEX + " = ?";
		public static final String BY_CITY = FIELDS.RESTAURANT_CITY +" = ? ";
		
	}
	@ManyToOne
	public RestaurantCategory category;
	public boolean deleted = false;
	public boolean showOnIndex = false;
	@ManyToOne
	public City city;
	@OneToOne
	public Address address;
	/**
	 * All comments given to this restaurant
	 * */
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
	public List<Comment> comments;
	/**
	 * logo image , ATTENTION! stores in /attachents/ dir
	 * */
	public Blob logo;
	/**
	 * Associated device
	 * */
	@OneToOne(fetch=FetchType.EAGER)
	public RestaurantDevice device;
	/**
	 * loginable users
	 * */
	@OneToMany(fetch = FetchType.LAZY)
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
	@OneToMany(mappedBy = "restaurant")
    @Where(clause = "deleted = 'f' ")
	public List<MenuItemGroup> menuBook = new ArrayList<MenuItemGroup>();
	/**
	 * Restaurant Title
	 * */
	public String title;

	@OneToMany(mappedBy = RestaurantDescription.FIELDS.RESTAURANT)
	public List<RestaurantDescription> descriptions = new ArrayList<RestaurantDescription>();

	@OneToOne(fetch = FetchType.LAZY)
	public WorkHours workHours = new WorkHours();
	public String twoLetters;
	@Column(name = "restaurant_descr")
	public String desc;

	public boolean isOnline() {
		Boolean online = (Boolean) Cache.get("isOnline_" + getId());
		if (online == null) {
			long waitTimeInMiliseconds = Long.parseLong(PropertyVault
					.getSystemValueFor("pingTime"));
			if (device != null && device.lastPing != null) {
				online = System.currentTimeMillis() - device.lastPing.getTime() < waitTimeInMiliseconds;
			} else {
				// FIXME Hardcore workaround on device null;
				online = true;
			}
			long waitTimeInMin = (waitTimeInMiliseconds / 1000 / 60) + 1;

			Cache.set("isOnline_" + getId(), online, waitTimeInMin + "mn");
		}
		return online;
	}

	public String description() {
		/*String lang = Lang.get();
		for (RestaurantDescription desc : descriptions) {
			if (desc.lang.equalsIgnoreCase(lang)) {
				return desc.description;
			}
		}*/
		if(desc == null || desc.isEmpty()){
		return Messages.get("restaurant.nodescription");
		}else {return desc;}
	}

	public String addressToString() {
		// FIXME Hardcore workaround
		return address != null ? address.toString() : Messages
				.get("restaurant.noaddress");
	}

	public String workHoursToday() {
		return workHours.today();
	}

	public String isOnlineAsString() {
		return isOnline() ? Messages.get("restaurant.online") : Messages
				.get("restaurant.offline");
	}

	public String getTwoLetters() {
		if (twoLetters == null || twoLetters.isEmpty())
		{
			return "VD";
		} else 
		return twoLetters;
	}
	public void setTwoLetters(String ltrs) {
			twoLetters = ltrs;
	}
=======
@Table(name = "vd_restaurant")
@SequenceGenerator(name = "restaurant_seq_gen", sequenceName = "restaurant_seq")
public class Restaurant {

    @Id
    @GeneratedValue(generator = "restaurant_seq_gen", strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(name = "city_id", insertable = false, updatable = false, nullable = false)
    public Long city_id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    @Deprecated
    public City city;

    @Column(name = "address_id", insertable = false, updatable = false, nullable = false)
    public Long address_id;
    @OneToOne
    @JoinColumn(name = "address_id")
    @Deprecated
    public Address address;

    @Column(name = "category_id", insertable = false, updatable = false, nullable = false)
    public Integer category_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Deprecated
    public RestaurantCategory category;

    @Column(name = "workhours_id", insertable = false, updatable = false, nullable = false)
    public Integer workhours_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workhours_id")
    @Deprecated
    public WorkHours workhours;
    /**
     * loginable power user id
     */
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    public Long user_id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Deprecated
    public User restaurantAdminUser;

    @Column(name = "title")
    public String title;
    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;
    @Column(name = "show_on_index", nullable = false)
    public boolean showOnIndex = false;

    /**
     * logo image , ATTENTION! stores in /attachents/ dir
     */
    public Blob logo;


    /**
     * Updates by job at 4 o'clock every day based on approved comments for past
     * 30 days
     */
    @Column(name = "raiting", nullable = false)
    public Integer raiting;

    @Column(name = "deviceLogin", nullable = false)
    public String deviceLogin;
    @Column(name = "devicePassword", nullable = false)
    public String devicePassword;

    @Column(name = "lastPing")
    public DateTime lastPing;
    /**
     * XXX should i store it here ? Restaurant setting ?
     */
    @Column(name = "discount")
    public Integer discount;

    @Column(name = "twoLetters", nullable = false, length = 2)
    public String twoLetters;


    public boolean isOnline() {
        Boolean online = (Boolean) Cache.get("isOnline_" + id);
        if (online == null) {
            long waitTimeInMiliseconds = 300000;
            Logger.debug("//TODO:get from system properties >>> %s", waitTimeInMiliseconds);

            /* Long.parseLong(PropertyVault
                       .getSystemValueFor("pingTime"));*/

            if (lastPing != null) {
                online = System.currentTimeMillis() - lastPing.getMillis() < waitTimeInMiliseconds;
            } else {
                // TODO Hardcore workaround on device null;
                online = true;
            }
            long waitTimeInMin = (waitTimeInMiliseconds / 1000 / 60) + 1;

            Cache.set("isOnline_" + id, online, waitTimeInMin + "mn");
        }
        return online;
    }

    public String addressToString() {
        // FIXME Hardcore workaround
        return address != null ? address.toString() : Messages
                .get("restaurant.noaddress");
    }

    public String isOnlineAsString() {
        return isOnline() ? Messages.get("restaurant.online") : Messages
                .get("restaurant.offline");
    }
>>>>>>> master

}
