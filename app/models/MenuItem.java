package models;

import com.avaje.ebean.annotation.EmbeddedColumns;
import com.avaje.ebean.annotation.Formula;
import org.joda.money.Money;
import org.joda.time.LocalDateTime;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "vd_menu_items")
@SequenceGenerator(name = "menu_items_seq_gen", sequenceName = "menu_items_seq")
public class MenuItem extends Model {

    @Id
    @GeneratedValue(generator = "menu_items_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "deleted")
    public boolean deleted = false;

    @Column(name = "description")
    public String description;

    @Column(name = "menuItemCreated")
    public LocalDateTime menuItemCreated;

    @Column(name = "available")
    public boolean available = false;

    @Transient
    @Formula(select = "_b${ta}.has_components",
            join = "left outer join (select (count(id)>0) as has_components, menu_item_id from vd_menu_item_components where deleted='f' group by menu_item_id) as _b${ta} on _b${ta}.menu_item_id = ${ta}.id")
    public boolean showComponents = false;

    @EmbeddedColumns( columns = "currency=currency, cents=price")
    public Money price;

    @Column(name = "restaurant_id", nullable = false, updatable = false, insertable = false)
    public Integer restaurantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;

    @Column(name = "menu_item_group_id", nullable = false, updatable = false, insertable = false)
    public Integer menuItemGroupId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_group_id")
    @Deprecated
    public MenuItemGroup menuItemGroup;


    public MenuItem() {

    }

    public MenuItem(Long id, String name, String description, Money price,
                    Boolean available, LocalDateTime menuItemCreated, MenuItemGroup group,
                    Restaurant restaurant, Boolean deleted) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.menuItemCreated = menuItemCreated;
        this.menuItemGroup = group;
        this.restaurant = restaurant;
        this.deleted = deleted;
    }

    /**
     * Cloning constructor generates new id immutable >>>??
     */
    public MenuItem(MenuItem item) {
        this(null, item.name, item.description, item.price, item.available,
                item.menuItemCreated, item.menuItemGroup, item.restaurant,
                item.deleted);
    }

    public String price() {
        //price.getAmountMajorInt() + "." + price.getAmountMinorInt()
        return price.toString();
    }

    public String name() {
        // TODO add transliteration/translation
        return name;
    }

    public String description() {
        // TODO add transliteration/translation
        return description;
    }
}
