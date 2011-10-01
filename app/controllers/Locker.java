package controllers;

import java.util.List;

import controllers.Bookkeeper.RENDER_KEYS;

import models.Order;
import models.geo.UserAddress;
import models.users.EndUser;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

/**
 * User Personal Page Controller
 * 
 * @author mike
 * */
@With(Secure.class)
public class Locker extends Controller {
	
	public static final class RENDER_KEYS{

		public static final String USER = "user";
		
	}
	
	@Before
	public static void __prepare() {
		Logger.debug(">>> Accesing locker %s ? %s", Security.connected(),
				session.getId());
		if (!Security.isConnected()) {
			Logger.debug(">>> Anonymous locker -> redirecting to basket()");
			if (!Security.isConnected()) {
				Application.basket();
			}

		}
		String userName = Security.connected();
		EndUser user = EndUser.find(EndUser.HQL.BY_LOGIN, userName).first();
		if (user == null) {
			Logger.debug(">>> locker -> user is null -> forbidden");
			forbidden();
		}
		renderArgs.put(RENDER_KEYS.USER, user);
	}

	/**
	 * Shows user's personal page
	 */
	public static void index() {
		Logger.debug(">>> Entering index");
		EndUser user = (EndUser) renderArgs.get(RENDER_KEYS.USER);

		List<UserAddress> addressList = user.addressBook;

		List<Order> orderList = Order.find(Order.HQL.BY_OWNER, user).fetch();
		render(user, addressList, orderList);
	}

	public static void addAddress(UserAddress address) {

		if (address == null) {
			redirect(Router.getFullUrl("Locker.index"));
		}
		address.user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		address.create();
		// TODO in future do it asynchronously!
		todo();
	}

	public static void editAddress(UserAddress address) {

		if (address.id == null) {
			error("Data inconsistency detected");
		}

		address.user = (EndUser) renderArgs.get(RENDER_KEYS.USER);
		UserAddress base = UserAddress.findById(address.id);
		if (!address.equals(base)) {
			// TODO Make logging
			// LogItem.log(Address.class.getName(), field, newValue, oldValue,
			// address.id, modifiedBy, modifiedOn)
			address.save();
		}

		// TODO in future do it asynchronously!
		index();
	}

	public static void deleteAddress(Long id) {
		if (id != null) {
			// TODO add logging
			UserAddress address = UserAddress.findById(id);
			address.deleted = true;
			address.save();
		}
		index();
	}

}
