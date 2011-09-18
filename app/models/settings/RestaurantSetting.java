package models.settings;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import models.Restaurant;

@Entity
@Table(name = "RestaurantSettings")
public class RestaurantSetting extends Model {
    public static final class FIELDS {
	public static final String RESTAURANTSETTING_KEY = "key";
	public static final String RESTAURANTSETTING_VALUE = "value";
	public static final String RESTAURANTSETTING_ISDEFAULT = "isDefault";
	public static final String RESTAURANTSETTING_STARTDATE = "startDate";
	public static final String RESTAURANTSETTING_ENDDATE = "endDate";
	public static final String RESTAURANTSETTING_RESTAURANT = "restaurant";
    }

    @Required
    @Min(3)
    @Max(32)
    @Column(name = "_key")
    public String key;
    @Required
    @Min(1)
    public String value;
    @Required
    public boolean isDefault = false;
    /**
     * if null than no date
     * */
    public Date startDate;
    /**
     * if null than no date
     * */
    public Date endDate;
    public Restaurant restaurant;
}