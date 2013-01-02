package models.geo;

import javax.persistence.Entity;

import play.db.jpa.Model;
@Entity
public class Coordinates extends Model {
	public static final class FIELDS{
		public static final String LONGITUDE = "longitude";
		public static final String LATITUDE = "latitude";
	}
	public static final class HQL{
		public static final String BY_LON_AND_LAT =FIELDS.LONGITUDE+" = ? and " +FIELDS.LATITUDE + "=?" ;
		
	}
	public Double longitude;
	public Double latitude;
}
