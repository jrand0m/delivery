/**
 * 
 */
package models.geo;

import javax.persistence.*;

import play.data.validation.Max;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */

@Entity
@Table(name = "vd_address")
@SequenceGenerator(name = "address_seq_gen", sequenceName = "address_seq")
public class Address {

    @Id
    @Column(name = "address_id" )
    @GeneratedValue(generator = "address_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Max(value = 30)
    @Column(name = "building_number")
    public String buildingNumber;

    @Column(name = "apartments_number")
    public String apartmentsNumber;
    
    public String additionalInfo;

    public boolean deleted = false;

    public Long city_id;

    public Long street_id;


    @Required
    @ManyToOne
    public City city;

    @Required
    @ManyToOne
    public Street street;	
	/*
	 * (non-Javadoc)
	 * 
	 * @see play.db.jpa.JPABase#toString()
	 */
	@Override
	public String toString() {

		return String.format("%s, %s", street, buldingNuber);
	}

}
