package models.geo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import play.i18n.Lang;
@Entity
public class Street extends Model {
	@ManyToOne
	public City city;
	public String title_ua;
	public String title_en;
	public boolean use = false;
	public String name(){
		return Lang.get().equals("en")?title_en:title_ua;
	}
}
