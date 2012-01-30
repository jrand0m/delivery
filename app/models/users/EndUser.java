/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import models.Order;
import models.geo.Address;
import models.geo.UserAddress;

/**
 * 
 * @author mike
 */

public class EndUser extends BaseUser {
	public static final class FIELDS {
		public static final String USER_ADDRESS_BOOK = "addressBook";
		public static final String USER_ORDER_BOOK = "orderBook";
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Address> addressBook;
	@OneToMany(mappedBy = Order.FIELDS.ORDER_OWNER, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Order> orderBook;
	@Override
	public String landingUrl() {
		return null;
	}
}
