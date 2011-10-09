package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
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
		public static final String ORDER_ITEM_PRICE = "orderItemPrice";
		// public static final String ORDER_ITEM_USER_PRICE =
		// "orderItemUserPrice";
	}

	public Integer count;

	public boolean deleted = false;
	@ManyToOne
	public MenuItem menuItem;
	@ManyToOne
	public Order order;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SELECTED_ORDERITEMS_COMPONENTS")
	public Set<MenuItemComponent> selectedComponents = new HashSet<MenuItemComponent>();
	/**
	 * Archived real price from restaurant including components (That was calculated in moment, when
	 * order was approved).
	 * */
	public Integer orderItemPrice;

	/* *
	 * Archived price that should be paid by user (That was calculated in
	 * moment, when order was approved).
	 * */
	/* public Integer orderItemUserPrice; */

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
		// this.orderItemUserPrice = orderItemUserPrice;
		this.orderItemPrice = orderItemPrice;
		this.order = orderId;
		this.deleted = false;
	}

	public OrderItem(MenuItem menuItem, Order order) {
		// TODO [Mike] (add calculations of a price here )
		this.menuItem = menuItem;
		this.order = order;
	}
	public String name() {
		// TODO transliteration?
		return menuItem.name();
	}
	public String desc() {
		// TODO transliteration?
		return menuItem.description();
	}
	public ArrayList<String> selectedComponentsNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (MenuItemComponent comp :selectedComponents){
			names.add(comp.name());
		}
		return names;
	}
}
