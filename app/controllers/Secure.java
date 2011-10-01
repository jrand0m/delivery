/**
 * 
 */
package controllers;

import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;
import annotations.AllowAnonymous;
import annotations.Check;
import enumerations.UserRoles;

public class Secure extends Controller {

	@Before(unless = { "login", "authenticate", "logout" })
	static void checkAccess() throws Throwable {
		boolean allowAnon = getActionAnnotation(AllowAnonymous.class) != null;

		// Authent
		if (!session.contains("username") && !allowAnon) {
			flash.put("url", "GET".equals(request.method) ? request.url : "/"); // seems
			login();
		}
		// Checks
		Check check = getActionAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
		check = getControllerInheritedAnnotation(Check.class);
		if (check != null) {
			check(check);
		}
	}

	private static void check(Check check) throws Throwable {
		for (UserRoles profile : check.value()) {
			boolean hasProfile = (Boolean) Security.invoke("check", profile);
			if (!hasProfile) {
				Security.invoke("onCheckFailed", profile);
			}
		}
	}

	// ~~~ Login

	public static void login() throws Throwable {
		Http.Cookie remember = request.cookies.get("rememberme");
		if (remember != null && remember.value.indexOf("-") > 0) {
			String sign = remember.value.substring(0,
					remember.value.indexOf("-"));
			String username = remember.value.substring(remember.value
					.indexOf("-") + 1);
			if (Crypto.sign(username).equals(sign)) {
				session.put("username", username);
				redirectToOriginalURL();
			}
		}
		flash.keep("url");
		render();
	}

	public static void authenticate(@Required String username, String password,
			boolean remember) throws Throwable {
		// Check tokens
		Boolean allowed = false;
		// try {
		// // This is the deprecated method name
		// allowed = (Boolean)Security.invoke("authentify", username, password);
		// } catch (UnsupportedOperationException e ) {
		// This is the official method name
		allowed = (Boolean) Security.invoke("authenticate", username, password);
		// }
		if (validation.hasErrors() || !allowed) {
			flash.keep("url");
			flash.error("secure.error");
			params.flash();
			login();
		}
		// Mark user as connected
		session.put("username", username);
		// Remember if needed
		if (remember) {
			response.setCookie("rememberme", Crypto.sign(username) + "-"
					+ username, "30d");
		}
		// Redirect to the original URL (or /)
		redirectToOriginalURL();
	}

	public static void logout() throws Throwable {
		Security.invoke("onDisconnect");
		session.clear();
		response.removeCookie("rememberme");
		Security.invoke("onDisconnected");
		flash.success("secure.logout");
		login();
	}

	// ~~~ Utils

	static void redirectToOriginalURL() throws Throwable {
		Security.invoke("onAuthenticated");
		String url = flash.get("url");
		if (url == null) {
			url = "/";
		}
		redirect(url);
	}

}