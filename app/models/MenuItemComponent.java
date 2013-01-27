package models;

import com.avaje.ebean.annotation.EmbeddedColumns;
import org.joda.money.Money;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "vd_menu_item_components")
@SequenceGenerator(name = "menu_item_component_seq_gen", sequenceName = "menu_item_component_seq")
public class MenuItemComponent extends Model {

    @Id
    @GeneratedValue(generator = "menu_item_component_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;

    @EmbeddedColumns(columns="currency=price_currency, cents=price")
    public Money price;

    @Column(name = "deleted")
    public boolean deleted = false;

    @Column(name = "requiredIds")
    public long[] requiredIds;

    @Column(name = "notCompatible")
    public long[] notCompatible;

    @Column(name = "menu_item_id")
    public Long menuItemId;
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    @Deprecated
    public MenuItem menuItem;


    // TODO add internationalization
    public String name() {
        return name;
    }

    // TODO add internationalization
    public String description() {
        return description;
    }

    public Money price() {
        return price;
    }

    @Override
    public String toString() {

        return name + " " + price;
    }
}
