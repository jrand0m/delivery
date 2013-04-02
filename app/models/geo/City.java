/**
 *
 */
package models.geo;

import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;

/**
 * @author Mike
 */
@Entity
@Table(name = "vd_city")
@SequenceGenerator(name = "city_seq_gen", sequenceName = "city_seq")
public class City extends Model {

    public static final Long NO_CITY_ID = -1L;
    @Id
    @Column(name = "city_id")
    @GeneratedValue(generator = "city_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long city_id;

    @Column(name = "cityNameKey", nullable = false)
    public String cityNameKey;

    /**
     * This field is used to store universal alias provided by our geoip-service.
     */
    @Column(name = "cityAliasName")
    public String cityAliasName;

    @Column(name = "display")
    public boolean display = false;

    public String getLocalizedName() {
        return Messages.get(cityNameKey);
    }
}
