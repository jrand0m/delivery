/**
 * 
 */
package models.geo;

import helpers.GeoDataHelper;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.i18n.Lang;

/**
 * @author Mike
 * 
 */
@Entity
public class City extends Model {
	public static class FIELDS {
		public static final String CITY_NAME_EN ="cityNameEN";
		public static final String CITY_NAME_RU ="cityNameRU";
		public static final String CITY_NAME_UA ="cityNameUA";
		public static final String ZIP_START="zipStart";
		public static final String ZIP_END="zipEnd";
		public static final String DISPLAY="display";
		
	}
	public static class HQL {
		public static final String BY_DISPLAY = FIELDS.DISPLAY + " = ?";
	}
	
	public String cityNameUA;
	public String cityNameRU;
	public String cityNameEN;
	public Integer zipStart;
	public Integer zipEnd;
	public boolean display = false;
	public String getLocalizedName(){
		if ("ua".equalsIgnoreCase(Lang.get())){
			return cityNameUA;
		}else if ("ru".equalsIgnoreCase(Lang.get())){
			return cityNameRU;
		}else if ("en".equalsIgnoreCase(Lang.get())){
			return cityNameEN;
		} else return cityNameUA; 
	}
	public static City getCityByIdSafely(String cityId) {
		City city = null;
		try {
			city = City.findById(Long.valueOf(cityId));
		} catch (NumberFormatException numformat){
			
		}
		if (city == null ){
			city = GeoDataHelper.getSystemDefaultCity();
		}
		return city;
	}
}
