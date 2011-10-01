package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class MenuItem extends Model {
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
	@ManyToOne
	public Restaurant restaurant;
	public boolean deleted = false;
	public String description;
	@ManyToOne(fetch = FetchType.LAZY)
	public MenuItemGroup menuItemGroup;

	public Date menuItemCreated;

	public String name;
	/**
	 * value in coins
	 * */

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
	 * Cloning constructor generates new id
	 * */
	public MenuItem(MenuItem item) {
		this(null, item.name, item.description, item.price, item.avaliable,
				item.menuItemCreated, item.menuItemGroup, item.restaurant,
				item.deleted);
	}

	public String price() {
		int h = price / 100;
		int c = price - h * 100;
		assert c >= 0;
		return String.valueOf(h) + "." + (0 < c && c < 10 ? "0" : "")
				+ (c != 0 ? String.valueOf(c) : "--");
	}
}
