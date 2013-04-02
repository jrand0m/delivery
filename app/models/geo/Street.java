package models.geo;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "vd_street")
@SequenceGenerator(name = "street_seq_gen", sequenceName = "street_seq")
public class Street extends Model {


    public static final Long NO_STREET_ID = -1L;
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
        //todo: internationalization
        return  title_ua;
    }

    @Override
    public String toString() {

        return name();
    }


    /**
     * @deprecated dont use on myBatis, only for JPA compatibility
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @Deprecated
    public City city;
}
