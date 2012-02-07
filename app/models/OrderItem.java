package models;



import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import play.db.jpa.Model;
import play.modules.guice.InjectSupport;
import services.RestaurantService;

import javax.inject.Inject;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@InjectSupport
@Table(name = "vd_order_items")
@SequenceGenerator(name = "order_items_seq_gen", sequenceName = "order_items_seq")
public class OrderItem  {
    @Id
    @GeneratedValue(generator = "order_items_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;
    
    @Column(name = "count")
    public Integer count;
    
    @Column(name = "deleted")    
    public boolean deleted = false;
    
    /**
     * Archived real price from restaurant including components
     * (That was calculated in moment, when order was approved).
     */
    @Column(name = "total_order_item_price")
    public Money orderItemPrice;

    @Column(name ="menu_item_id")
    public Long menuItemId;
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    public MenuItem menuItem;
    
    @Column(name = "order_id")
    public Long orderId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order order;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vd_order_items_selected_components")
    public Set<MenuItemComponent> selectedComponents = new HashSet<MenuItemComponent>();

    @Inject
    @Deprecated
    private static RestaurantService restaurantService;

    public Money totalPriceInclComponents() {
        //Todo extract to calculation service
        Money componentPrice = Money.zero(CurrencyUnit.of("UAH"));
        for (MenuItemComponent mc : selectedComponents) {
            componentPrice = componentPrice.plus(mc.price);
        }
        return  menuItem.price.plus(componentPrice).multipliedBy(count)  ;
    }

    public OrderItem() {

    }

    public OrderItem(MenuItem menuItem, Order order, Long[] component) {
        // TODO [Mike] (add calculations of a price here )
        this.menuItem = menuItem;
        this.order = order;
        this.count = 1;
        this.orderItemPrice = menuItem.price;
        if (component != null) {
            for (Long comp : component) {
                MenuItemComponent mic = restaurantService.getMenuItemComponent(comp);
                if (mic.menuItemId.equals(menuItemId)) {
                    selectedComponents.add(mic);
                    this.orderItemPrice = this.orderItemPrice.plus( mic.price);
                }
            }
        }
    }

    public String name() {
        return menuItem.name();
    }

    public String desc() {
        StringBuilder s = new StringBuilder(menuItem.description());
        if (!selectedComponents.isEmpty()) {
            s .append( " (");
            for (Iterator<MenuItemComponent> i = selectedComponents.iterator(); i.hasNext(); ) {
                s.append(i.next().name());
                s.append(", ");
            }
            s.delete(s.length()-2, s.length());
            s.append( ") ");
        }
        return s.toString();
    }
    /**
     * @deprecated use orderItemField
     * */
    public String priceString() {
        
        return orderItemPrice.toString();
    }

    public ArrayList<String> selectedComponentsNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (MenuItemComponent comp : selectedComponents) {
            names.add(comp.name());
        }
        return names;
    }
}
