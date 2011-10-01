package models.settings;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import models.users.EndUser;

@Entity
@Table(name = "UserSettings")
public class UserSetting extends Model {
	public static final class FIELDS {
		public static final String USERSETTING_stg_key = "stg_key";
		public static final String USERSETTING_stg_value = "stg_value";
		public static final String USERSETTING_ISDEFAULT = "isDefault";
		public static final String USERSETTING_STARTDATE = "startDate";
		public static final String USERSETTING_ENDDATE = "endDate";
		public static final String USERSETTING_USER = "user";
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
	public boolean isDefault = false;
	/**
	 * if null than no date
	 * */
	public Date startDate;
	/**
	 * if null than no date
	 * */
	public Date endDate;
	public EndUser user;
}
