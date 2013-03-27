package models;

import play.Play;
import play.db.ebean.Model;
import play.i18n.Lang;

import javax.persistence.*;

@Entity
@Table(name = "vd_restaurants_categories")
@SequenceGenerator(name = "restaurants_categories_seq_gen", sequenceName = "restaurants_categories_seq")
public class RestaurantCategory extends Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "restaurants_categories_seq_gen", strategy = GenerationType.SEQUENCE)
    public Integer id;
    /**
     * on this field applies slugvify
     */
    //TODO: @Required
    @Column(name = "categoryDisplayNameEN", nullable = false)
    public String categoryDisplayNameEN;

    @Column(name = "categoryDisplayNameRU")
    public String categoryDisplayNameRU;
    //TODO: @Required
    @Column(name = "categoryDisplayNameUA", nullable = false)
    public String categoryDisplayNameUA;

    public String localizedName() {
        String lang = "ua"; //TODO: how to get current lang?
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
