package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Client;

import play.db.jpa.Model;


@Entity
@Table(name = "ClientSettings")
public class ClientSetting extends Model {
	public String key;
	public String value;
	public Date dateChanged;
	/**
	 * FIXME if null - default for all ?? consider
	 * */
	public Client client;
}
