package models;

import play.data.validation.Required;
import play.i18n.Lang;

import javax.persistence.*;


@Table(name = "vd_restaurants_categories")
@SequenceGenerator(name = "restaurants_categories_seq_gen", sequenceName = "restaurants_categories_seq")
public class RestaurantCategory {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "restaurants_categories_seq_gen", strategy = GenerationType.SEQUENCE)
    public Integer id;
    /**
     * on this field applies slugvify
     */
    @Required
    @Column(name = "categoryDisplayNameEN", nullable = false)
    public String categoryDisplayNameEN;

    @Column(name = "categoryDisplayNameRU")
    public String categoryDisplayNameRU;
    @Required
    @Column(name = "categoryDisplayNameUA", nullable = false)
    public String categoryDisplayNameUA;

    public String localizedName() {
        String lang = Lang.get();
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
