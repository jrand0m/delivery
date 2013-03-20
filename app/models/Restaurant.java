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
import play.db.ebean.Model;
import play.i18n.Messages;
import javax.persistence.*;

/**
 * @author mike
 */
@Entity
@Table(name = "vd_restaurant")
@SequenceGenerator(name = "restaurant_seq_gen", sequenceName = "restaurant_seq")
public class Restaurant extends Model {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getWorkhours_id() {
        return workhours_id;
    }

    public void setWorkhours_id(Integer workhours_id) {
        this.workhours_id = workhours_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isShowOnIndex() {
        return showOnIndex;
    }

    public void setShowOnIndex(boolean showOnIndex) {
        this.showOnIndex = showOnIndex;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public String getDeviceLogin() {
        return deviceLogin;
    }

    public void setDeviceLogin(String deviceLogin) {
        this.deviceLogin = deviceLogin;
    }

    public String getDevicePassword() {
        return devicePassword;
    }

    public void setDevicePassword(String devicePassword) {
        this.devicePassword = devicePassword;
    }

    public DateTime getLastPing() {
        return lastPing;
    }

    public void setLastPing(DateTime lastPing) {
        this.lastPing = lastPing;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getTwoLetters() {
        return twoLetters;
    }

    public void setTwoLetters(String twoLetters) {
        this.twoLetters = twoLetters;
    }

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
     * Stored file entity ?
     */
    //TODO: public Blob logo;


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
            int waitTimeInMillisecond = 300000;
            if (Logger.isDebugEnabled()){
                Logger.debug(String.format("//TODO:get from system properties >>> %s", waitTimeInMillisecond));
            }
            /* Long.parseLong(PropertyVault
                       .getSystemValueFor("pingTime"));*/

            if (lastPing != null) {
                online = System.currentTimeMillis() - lastPing.getMillis() < waitTimeInMillisecond;
            } else {
                // TODO Hardcore workaround on device null;
                online = true;
            }
            int waitTimeInSeconds = (waitTimeInMillisecond / 1000 ) + 1;

            Cache.set("isOnline_" + id, online, waitTimeInSeconds);
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

}
