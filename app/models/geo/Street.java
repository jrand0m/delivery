package models.geo;

import play.i18n.Lang;

import javax.persistence.*;


@Table(name = "vd_street")
@SequenceGenerator(name = "street_seq_gen", sequenceName = "street_seq")
public class Street {


    @Id
    @Column(name = "street_id")
    @GeneratedValue(generator = "street_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "city_id", insertable = false, updatable = false)
    public Long city_id;

    @Column(name = "display")
    public boolean display = false;

    @Column(name = "title_ua")
    public String title_ua;

    @Column(name = "title_en")
    public String title_en;

    @Column(name = "title_ru")
    public String title_ru;

    public String name() {
        return Lang.get().equals("en") ? title_en : title_ua;
    }

    @Override
    public String toString() {

        return Lang.get().equals("ua") ? title_ua : title_en;
    }


    /**
     * @deprecated dont use on myBatis, only for JPA compatibility
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @Deprecated
    public City city;
}
