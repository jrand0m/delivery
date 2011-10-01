/**
 * 
 */
package models.geo;

import javax.persistence.Entity;

import play.db.jpa.Model;

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
}
