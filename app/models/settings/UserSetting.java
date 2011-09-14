package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.User;

import play.db.jpa.Model;
@Entity
@Table(name ="UserSettings")
public class UserSetting extends GeneralSetting {
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public User user;
}
