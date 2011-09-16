package models.settings;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.User;
@Entity
@Table(name ="UserSettings")
public class UserSetting extends GeneralSetting {
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public User user;
}
