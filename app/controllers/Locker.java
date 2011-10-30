package controllers;

import java.util.List;

import annotations.Check;

import controllers.Bookkeeper.RENDER_KEYS;

import models.Order;
import models.geo.UserAddress;
import models.users.EndUser;
import models.users.User;
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
@Check(User.class)
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
			notFound();

		}
		String userName = Security.connected();
		EndUser user = EndUser.find(EndUser.HQL.BY_LOGIN, userName).first();
		if (user == null) {
			Logger.debug(">>> locker -> user is null -> forbidden");
			forbidden();
		}
		renderArgs.put(RENDER_KEYS.USER, user);
	}

	public static void cabinet() {
		// FIXME move to locker
		Order order = null;
		renderArgs.put("order", order);
		render("/Locker/cabinetLastOrders.html");
	}

	public static void cabinetProfile() {
		// FIXME move to locker
		Order order = null;
		renderArgs.put("order", order);
		render("/Locker/cabinetProfile.html");
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
