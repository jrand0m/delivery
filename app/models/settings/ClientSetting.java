package models.settings;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Restaurant;


@Entity
@Table(name = "ClientSettings")
public class ClientSetting extends GeneralSetting {
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public Restaurant client;
}
