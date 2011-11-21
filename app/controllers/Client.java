package controllers;

import models.users.CourierUser;
import models.users.RestaurantBarman;
import models.users.User;
import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;

public class Client extends Controller {

	public static void restaurant(String username, String password)
			throws Throwable {
		if (username == null || password == null || username.trim().equals("")
				|| password.trim().equals("")) {
			if (Security.isConnected()
					&& RestaurantBarman.find("login = ?", Security.connected())
							.first() != null) {
				render();
			} else {
				Secure.login();
			}
		}

		if (Security.authenticate(username, password)
				&& RestaurantBarman.find("login = ?", username).first() != null) {
			session.put("username", username);
			render();
		} else {
			forbidden();
		}
	}

	public static void courier(String username, String password)
			throws Throwable {
		if (username == null || password == null || username.trim().equals("")
				|| password.trim().equals("")) {
			if (Security.isConnected()
					&& CourierUser.find("login = ?", Security.connected())
							.first() != null) {
				render();
			} else {
				Secure.login();
			}
		}

		if (Security.authenticate(username, password)
				&& CourierUser.find("login = ?", username).first() != null) {
			session.put("username", username);
			render();
		} else {
			forbidden();
		}
	}
}
