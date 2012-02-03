/**
 *
 */
package models.geo;

import play.data.validation.Max;
import play.data.validation.Required;

import javax.persistence.*;

/**
 * @author Mike
 */

@Table(name = "vd_address")
@SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(generator = "address_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Max(value = 30)
    @Column(name = "building_number")
    public String buildingNumber;

    @Max(value = 30)
    @Column(name = "apartments_number")
    public String apartmentsNumber;

    @Column(name = "additional_info")
    public String additionalInfo;

    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    @Column(name = "city_id", insertable = false, nullable = false, updatable = false)
    public Long city_id;

    @Column(name = "street_id", insertable = false, nullable = false, updatable = false)
    public Long street_id;


    @Required
    @ManyToOne
    @JoinColumn(name = "city_id")
    public City city;

    @Required
    @ManyToOne
    @JoinColumn(name = "street_id")
    public Street street;

    @Override
    public String toString() {

        return String.format("%s, %s/%s", street, buildingNumber, apartmentsNumber);
    }

}
