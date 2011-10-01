/**
 * 
 */
package models.geo;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity
public class City extends Model {
	public String cityName;
	public Integer zipStart;
	public Integer zipEnd;
}
