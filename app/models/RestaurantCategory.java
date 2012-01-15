package models;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.i18n.Lang;


public class RestaurantCategory extends Model {
	/**
	 * on this field applies slugvify
	 * */
	@Required
	public String categoryDisplayNameEN;
	public String categoryDisplayNameRU;
	@Required
	public String categoryDisplayNameUA;

	public String localizedName() {
		String lang = Lang.get();
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
