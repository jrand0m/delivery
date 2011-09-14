package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * This type is hard deletable
 * */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GeneralSetting extends Model
{
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
