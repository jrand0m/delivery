/**
 *
 */
package models.geo;

import play.i18n.Messages;

import javax.persistence.*;

/**
 * @author Mike
 */

@Table(name = "vd_city")
@SequenceGenerator(name = "city_seq_gen", sequenceName = "city_seq")
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(generator = "city_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "city_name_key", nullable = false)
    public String cityNameKey;

    /**
     * This field is used to store universal alias provided by our geoip-service.
     */
    @Column(name = "city_alias_name")
    public String cityAliasName;

    @Column(name = "display")
    public boolean display = false;

    public String getLocalizedName() {
        return Messages.get(cityNameKey);
    }
}
