package models.settings;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "SystemSettings")
public class SystemSetting extends Model {
	public static final class FIELDS {
		public static final String SYSTEMSETTING_stg_key = "stg_key";
		public static final String SYSTEMSETTING_stg_value = "stg_value";
		public static final String SYSTEMSETTING_ISDEFAULT_SETTING = "isDefaultSetting";
		public static final String SYSTEMSETTING_STARTDATE = "startDate";
		public static final String SYSTEMSETTING_ENDDATE = "endDate";
	}
	
	public static final class KEYS {
		public static final String DEFAULT_CITY_ID = "defaultCityId";
		public static final String GUESS_CITY_ENABLED = "guessCityByIpEnabled";
	}
	public static final class DEFAULT_VALUES{
		public static final long DEFAULT_CITY_ID = 1;
	}

	@Required
	@Min(3)
	@Max(32)
	@Column(name = "_stg_key")
	public String stg_key;
	@Required
	@Min(1)
	@Column(name = "_stg_value")
	public String stg_value;
	@Required
	public boolean isDefaultSetting = false;
	/**
	 * if null than no date
	 * */
	public Date startDate;
	/**
	 * if null than no date
	 * */
	public Date endDate;
}
