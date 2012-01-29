package models.dto.intern.admin;

import models.geo.City;

public class Street {
	public Street(models.geo.Street street) {
		city_id = street.city.id;
		use = street.use;
		title_ua = street.title_ua;
		title_en = street.title_en;
		id = street.id;
	}
	public Long city_id;
	public boolean use = false;
	public String title_ua;
	public String title_en;
	public Long id;
}
