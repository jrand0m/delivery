package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Courier;

import play.db.jpa.Model;
@Entity
@Table (name = "CourierSettings")
public class CourierSetting extends Model {
	public String key;
	public String value;
	public Date dateChanged;
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public Courier courier;
	 
}
