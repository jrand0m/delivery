package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class MenuItem extends Model {
    public static final class FIELDS{
	public static final String AVALIABLE = "avaliable";
	public static final String DELETED = "deleted";
	public static final String RESTAURANT = "restaurant";
	public static final String GROUP = "group";
	public static final String DESCRIPTION = "description";
	public static final String MENUITEMCREATED = "menuItemCreated";
	public static final String NAME = "name";
	public static final String PRICE = "price";
    }
    
    
    
    public boolean       avaliable = false;
    @ManyToOne
    public Restaurant        restaurant;
    public boolean       deleted   = false;
    public String        description;
    @Column(name="menuItemGroupId")
    public MenuItemGroup group;

    public Date          menuItemCreated;

    public String        name;
    /**
     * value in coins
     * */

    public Integer       price;

    public MenuItem() {

    }

    public MenuItem(Long id, String name, String description, Integer price,
            Boolean avaliable, Date menuItemCreated, MenuItemGroup group,
            Restaurant restaurant, Boolean deleted) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.avaliable = avaliable;
        this.menuItemCreated = menuItemCreated;
        this.group = group;
        this.restaurant = restaurant;
        this.deleted = deleted;
    }

    /**
     * Cloning constructor generates new id
     * */
    public MenuItem(MenuItem item) {
        this(null, item.name, item.description, item.price, item.avaliable,
                item.menuItemCreated, item.group, item.restaurant, item.deleted);
    }

}
