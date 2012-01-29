/**
 * 
 */
package models.geo;

import helpers.GeoDataHelper;
import play.db.jpa.Model;
import play.i18n.Lang;
import play.i18n.Messages;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * @author Mike
 */
@Entity
@Table(name = "vd_city")
@SequenceGenerator(name = "city_seq_gen", sequenceName = "city_seq")
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(generator = "city_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "city_name_key", nullable = false)
	public String cityNameKey;

    @Column(name = "city_alias_name")
    public String cityAliasName;

    @Column(name = "display")
	public boolean display = false;

	public String getLocalizedName(){
		return Messages.get(cityNameKey);
	}
}
