/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author Mike
 *
 */
@Entity
public class RestaurantNetwork extends Restaurant {
    @OneToMany
    public List<Restaurant> restoraunts = new ArrayList<Restaurant>();
}
