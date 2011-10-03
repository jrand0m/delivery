package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class RestaurantCategory extends Model {
	public String categoryDisplayNameEN;
	public String categoryDisplayNameRU;
	public String categoryDisplayNameUA;

	public String localizedName(String lang) {
		if ("ua".equals(lang)) {
			return categoryDisplayNameUA;
		} else if ("en".equals(lang)) {
			return categoryDisplayNameEN;
		} else if ("ru".equals(lang)) {
			return categoryDisplayNameRU;
		}
		return categoryDisplayNameUA;

	}
}
