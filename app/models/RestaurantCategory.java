package models;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.i18n.Lang;

import javax.persistence.*;


@Entity
@Table(name = "vd_restaurants_categories")
@SequenceGenerator(name = "restaurants_categories_seq_gen", sequenceName = "restaurants_categories_seq")
public class RestaurantCategory {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "restaurants_categories_seq_gen",strategy = GenerationType.SEQUENCE)
    public Integer id;
    /**
	 * on this field applies slugvify
	 * */
	@Required
    @Column(name = "category_display_name_en", nullable = false)
	public String categoryDisplayNameEN;

    @Column(name = "category_display_name_ru")
    public String categoryDisplayNameRU;
	@Required
    @Column(name = "category_display_name_ua", nullable = false)
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
