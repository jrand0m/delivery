package models;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;


//@Where(clause = "deleted = 0")
public class OrderItem extends Model {

	public static final class HQL {
		public static final String BY_ORDER_AND_MENU_ITEM = OrderItem.FIELDS.ORDER
				+ " = ? and " + OrderItem.FIELDS.MENUITEM + " =? ";
	}

	public static final class FIELDS {
		public static final String COUNT = "count";
		public static final String DELETED = "deleted";
		public static final String MENUITEM = "menuItem";
		public static final String ORDER = "order";
		public static final String ORDER_ITEM_PRICE = "orderItemPrice";	}

	public Integer count;

	public boolean deleted = false;
	@ManyToOne
	public MenuItem menuItem;
	@ManyToOne
	public Order order;

	@ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "SELECTED_ORDERITEMS_COMPONENTS")
	public Set<MenuItemComponent> selectedComponents = new HashSet<MenuItemComponent>();
	/**
	 * Archived real price from restaurant including components (That was calculated in moment, when
	 * order was approved).
	 * */
	public Integer orderItemPrice;

	public Integer totalPriceInclComponents(){
		Integer componentPrice = 0;
		for (MenuItemComponent mc:selectedComponents){
			componentPrice += mc.itm_price;
		}
		return count * (menuItem.price + componentPrice);  
	}
	public OrderItem() {

	}

	public OrderItem(Integer count, /* Integer orderItemUserPrice, */
			Integer orderItemPrice, Order orderId, MenuItem menuitem) {
		this.menuItem = menuitem;
		this.count = count;
		this.orderItemPrice = orderItemPrice;
		this.order = orderId;
		this.deleted = false;
	}

	public OrderItem(MenuItem menuItem, Order order, Long[] component) {
		// TODO [Mike] (add calculations of a price here )
		this.menuItem = menuItem;
		this.order = order;
		this.count = 1;
		this.orderItemPrice = menuItem.price;
		if (component != null){
			for (Long comp: component){
				MenuItemComponent mic = MenuItemComponent.findById(comp);
				if (mic.itm_root.equals(menuItem)){
					selectedComponents.add(mic);
					this.orderItemPrice = this.orderItemPrice + mic.price();
				}
			}
		}
	}
	public String name() {
		return menuItem.name();
	}
	public String desc() {
		String s = menuItem.description();
		if (!selectedComponents.isEmpty()){
			s += " (";
			for (Iterator<MenuItemComponent> i = selectedComponents.iterator(); i.hasNext(); ){
				s += i.next().name();
				if (i.hasNext()){
					s+= ", ";
				}
			}
			s+= ") ";
		}
		return s;
	}
	public String priceString(){
		String string = new BigDecimal(orderItemPrice).setScale(2,RoundingMode.HALF_EVEN).divide(new BigDecimal(100).setScale(2,RoundingMode.HALF_EVEN)).toString();
		return string;
	}
	public ArrayList<String> selectedComponentsNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (MenuItemComponent comp :selectedComponents){
			names.add(comp.name());
		}
		return names;
	}
}
