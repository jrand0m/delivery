package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Courier;
import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
@Table (name = "CourierSettings")
public class CourierSetting extends Model {
	public static final class FIELDS{
	    public static final String COURIERSETTING_KEY = "key";
	    public static final String COURIERSETTING_VALUE = "value";
	    public static final String COURIERSETTING_ISDEFAULT = "isDefault";
	    public static final String COURIERSETTING_STARTDATE = "startDate";
	    public static final String COURIERSETTING_ENDDATE = "endDate";
	    public static final String COURIERSETTING_COURIER = "courier";
	}
    	
    	@Required
	@Min(3)
	@Max(32)
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
	public Courier courier;
	 
}
