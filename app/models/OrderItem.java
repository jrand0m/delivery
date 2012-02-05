package models;


import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import play.db.jpa.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


//@Where(clause = "deleted = 0")
public class OrderItem  {

    public static final class FIELDS {
        public static final String COUNT = "count";
        public static final String DELETED = "deleted";
        public static final String MENUITEM = "menuItem";
        public static final String ORDER = "order";
        public static final String ORDER_ITEM_PRICE = "orderItemPrice";
    }

    public Integer count;

    public boolean deleted = false;
    @ManyToOne
    public MenuItem menuItem;
    @ManyToOne
    public Order order;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "SELECTED_ORDERITEMS_COMPONENTS")
    public Set<MenuItemComponent> selectedComponents = new HashSet<MenuItemComponent>();
    /**
     * Archived real price from restaurant including components (That was calculated in moment, when
     * order was approved).
     */
    public Money orderItemPrice;

    public Money totalPriceInclComponents() {
        Money componentPrice = Money.zero(CurrencyUnit.of("UAH"));
        for (MenuItemComponent mc : selectedComponents) {
            componentPrice = componentPrice.plus(mc.itm_price);
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
                MenuItemComponent mic = MenuItemComponent.findById(comp);
                if (mic.itm_root.equals(menuItem)) {
                    selectedComponents.add(mic);
                    this.orderItemPrice = this.orderItemPrice.plus( mic.price());
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
