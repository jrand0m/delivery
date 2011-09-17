package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
@Table(name = "SystemSettings")
public class SystemSetting extends Model {
	public static final class FIELDS{
	public static final String  KEY= "key";
	public static final String VALUE= "value";
	public static final String ISDEFAULT= "isDefault";
	public static final String STARTDATE= "startDate";
	public static final String ENDDATE= "endDate";
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
}
