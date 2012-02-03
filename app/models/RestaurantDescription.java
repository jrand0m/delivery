/**
 *
 */
package models;

import play.db.jpa.Model;

import javax.persistence.ManyToOne;

/**
 * @author Mike
 */
public class RestaurantDescription {
    public static final class FIELDS {
        public static final String LANG = "lang";
        public static final String DESCRIPTION = "description";
        public static final String RESTAURANT = "restaurant";
    }

    public String lang;
    public String description;
    @ManyToOne
    public Restaurant restaurant;

}
