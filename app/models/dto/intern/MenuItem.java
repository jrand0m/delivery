/**
 * 
 */
package models.dto.intern;

import java.util.List;

import javax.persistence.OneToMany;

import org.hibernate.annotations.CascadeType;

import models.OrderItem;

/**
 * @author Mike
 * 
 */
public class MenuItem {
	/**
	 * @param oi
	 */
	public MenuItem(OrderItem oi) {
		count = oi.count;
		name = oi.menuItem.name;
		pricePerItem = oi.menuItem.price;
		// check for components
	}

	/**
	 * default eblja
	 */
	public MenuItem() {
		// TODO Auto-generated constructor stub
	}
	public Long mi;
	public String name;
	public String descr;
	public Integer count;
	public Integer pricePerItem;
	public List<MenuItem> components;
}
