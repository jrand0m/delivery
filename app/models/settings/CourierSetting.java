package models.settings;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Courier;
@Entity
@Table (name = "CourierSettings")
public class CourierSetting extends GeneralSetting {
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public Courier courier;
	 
}
