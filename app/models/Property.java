package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FieldResult;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
public class Property extends Model {
	/**
	 * use Like '{ModelName}_{ModelId}_{key}'
	 * */
	@Required
	@Min(3)
	public String name;
	@Required
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
