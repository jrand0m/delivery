package models.dto.intern.admin;


public class Street {
    public Street() {
    }

    public Street(models.geo.Street street) {
        city_id = street.city_id;
        use = street.display;
        title_ua = street.title_ua;
        title_en = street.title_en;
        id = street.id;
    }

    public Long id;

    public Long city_id;

    public boolean use = false;

    public String title_ua;

    public String title_en;

    public String title_ru;

}
