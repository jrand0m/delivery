package models.dto.intern.admin;

import models.geo.City;

public class Street {
	public Street(models.geo.Street street) {
		city_id = street.city.getId();
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
	public models.geo.Street getStreet(){
		models.geo.Street r = new models.geo.Street();
		if (city_id != null || city_id !=0){
			r.city = City.findById(city_id);
		}
		r.use = use;
		r.title_en = title_en;
		r.title_ua = title_ua;
		r.id = id==null||id==0?null:id;
		return r;
	};
}
