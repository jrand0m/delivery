/**
 * 
 */
package models.geo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.Where;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
@Where(clause = "deleted = 0")
@Inheritance(strategy = InheritanceType.JOINED)
public class Address extends Model {
	public final static class FIELDS {
		public final static String ADDRESS_BULDING_NUBER = "buldingNuber";
		public final static String ADDRESS_DELETED = "deleted";
		public final static String ADDRESS_STREET = "street";
		public final static String USER = "user";
	}

	public boolean deleted = false;
	@MaxSize(5)
	public String buldingNuber;
	@MaxSize(100)
	@MinSize(5)
	public String street;

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
