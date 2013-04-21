/**
 *
 */
package models.geo;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * @author Mike
 */
@Entity
@Table(name = "vd_address")
@SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq")
public class Address extends Model {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(generator = "address_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    //TODO:@Max(value = 30)
    @Column(name = "buildingNumber")
    public String buildingNumber;

    //TODO:@Max(value = 30)
    @Column(name = "apartmentsNumber")
    public String apartmentsNumber;

    @Column(name = "additionalInfo")
    public String additionalInfo;

    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    @Column(name = "city_id",  nullable = false)
    public Long city_id = City.NO_CITY_ID;

    @Column(name = "street_id", nullable = false)
    public Long street_id = Street.NO_STREET_ID;

    @Transient
    public City city;

    @Transient
    public Street street;

    @Override
    public String toString() {

        return String.format("%s, %s", street, buildingNumber);
    }

}
