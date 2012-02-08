/**
 *
 */
package models;

import javax.persistence.*;

/**
 * @author Mike
 */
@Table(name = "vd_restaurant_descriptions")
@SequenceGenerator(name = "restaurant_descriptions_seq_gen", sequenceName = "restaurant_descriptions_seq")
public class RestaurantDescription {
    @Id
    @GeneratedValue(generator = "restaurant_descriptions_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public Integer id;
    @Column(name = "lang")
    public String lang;
    @Column(name = "description")
    public String description;

    @Column(name = "restaurant_id", insertable = false, updatable = false, nullable = false)
    public Integer restaurantId;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;

}
