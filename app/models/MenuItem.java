package models;

import org.joda.money.Money;
import org.joda.time.LocalDateTime;

import javax.persistence.*;


@Table(name = "vd_menu_items")
@SequenceGenerator(name = "menu_items_seq_gen", sequenceName = "menu_items_seq")
public class MenuItem {

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
    // filled dynamicaly via sql
    public boolean showComponents = false;

    @Column(name = "price")
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
