package models;

import play.db.jpa.Model;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@Where(clause = "deleted = 0")
public class MenuItem  {
    public static final class FIELDS {
        public static final String MENU_ITEM_AVALIABLE = "avaliable";
        public static final String MENU_ITEM_DELETED = "deleted";
        public static final String MENU_ITEM_RESTAURANT = "restaurant";
        public static final String MENU_ITEM_GROUP = "menuItemGroup";
        public static final String MENU_ITEM_DESCRIPTION = "description";
        public static final String MENU_ITEM_MENUITEM_CREATED = "menuItemCreated";
        public static final String MENU_ITEM_NAME = "name";
        public static final String MENU_ITEM_PRICE = "price";
    }

    public boolean avaliable = false;
    @ManyToOne(fetch = FetchType.LAZY)
    public Restaurant restaurant;
    public boolean deleted = false;
    public String description;
    @ManyToOne(fetch = FetchType.LAZY)
    public MenuItemGroup menuItemGroup;
    @OneToMany(fetch = FetchType.EAGER)
    public List<MenuItemComponent> components = new ArrayList<MenuItemComponent>();

    public Date menuItemCreated;
    public boolean showComponents = false;
    public String name;
    /**
     * value in coins
     */

    public Integer price;

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
        this.menuItemGroup = group;
        this.restaurant = restaurant;
        this.deleted = deleted;
    }

    /**
     * Cloning constructor generates new id immutable >>>??
     */
    public MenuItem(MenuItem item) {
        this(null, item.name, item.description, item.price, item.avaliable,
                item.menuItemCreated, item.menuItemGroup, item.restaurant,
                item.deleted);
    }

    public String price() {
        int h = price / 100;
        int c = price - h * 100;
        assert c >= 0;
        return String.valueOf(h) + (0 < c && c < 10 ? ".0" : ".")
                + (c != 0 ? String.valueOf(c) : "00");
    }

    public String name() {
        // TODO add transliteration/translation
        return name;
    }

    public String description() {
        // TODO add transliteration/translation
        return description;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        if (components != null) {
            for (MenuItemComponent mi : components) {
                b.append(mi.toString());
                b.append(',');
            }
        }
        b.append(']');
        return name + ":" + price + ":" + (restaurant != null ? restaurant.title : "<detached>");
    }
}
