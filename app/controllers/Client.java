package controllers;

import enumerations.UserType;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import services.UserService;

import javax.inject.Inject;

@InjectSupport
public class Client extends Controller {
    @Inject
    private static UserService userService;

	public static void restaurant(String username, String password)
			throws Throwable {
		if (username == null || password == null) {
			if (session.contains("_id") ){
				render();
			} else {
				notFound();
			}
		}
        //TODO divide onto 2 methods depending on arg list. cleaning task////
		if (userService.verifyDeviceCredentials(username, password)) {
			session.put("_id", username);
			render();
		} else {
			forbidden();
		}
	}

	public static void courier(String username, String password)
			throws Throwable {
		if (username == null || password == null ) {
			if (Security.isConnected()
					&& userService.isUserInRole(Security.connected(), UserType.COURIER)) {
				render();
			} else {
				Secure.login();
			}
		}

		if ( userService.verifyCredentials(username,password)) {
			session.put("username", username);
			render();
		} else {
			forbidden();
		}
	}
}
