package models.geo;

import play.db.jpa.Model;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Deprecated
@Table(name = "IP_GEO_DATA")
public class IpGeoData extends Model {
    public static class FIELDS {
        public static final String IP = "ip";
        public static final String CITY = "city";
        public static final String LAST_UPDATE = "lastUpdate";
        public static final String COORDINATES = "coordinates";
    }

    public static class HQL {

        public static final String BY_IP = FIELDS.IP + " = ?";

    }


    public String ip;
    @ManyToOne
    public City city;
    public Date lastUpdate;
    @ManyToOne
    public Coordinates coordinates;
}
