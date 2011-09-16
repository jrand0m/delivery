package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Restaurant;

import play.db.jpa.Model;


@Entity
@Table(name = "ClientSettings")
public class ClientSetting extends GeneralSetting {
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public Restaurant client;
}
