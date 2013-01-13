/**
 * 
 */
package controllers;

import helpers.Cypher;
import models.users.User;
import play.api.libs.Crypto;
import play.data.validation.Constraints;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;
import annotations.AllowAnonymous;
import annotations.Check;
import play.mvc.Result;
import views.html.*;
import views.html.Secure.login;

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
		boolean hasProfile = false;
		Class<? extends User> lastclazz = null;
		for (Class<? extends User> clazz : check.value()) {
			if (hasProfile = (Boolean) Security.invoke("check", clazz)){
				return;
			}
			lastclazz = clazz;
		}
		if (!hasProfile) {
			Security.invoke("onCheckFailed", lastclazz);
		}
	}

	// ~~~ Login

	public static Result login() throws Throwable {
		//todo implement remember me functionlity
		//flash("url",flash("url")); //todo saving redirect url -
		/*todo make this fuck respond to json via post requests
		if (request().ajax()){
			renderArgs.put("result", "false");
			return ok(login.);
		}*/
		
		return ok(login.render("blad"));
	}

	public static Result authenticate(/*Required (Param)*/
                                    String username, String password,
			boolean remember) throws Throwable {
		// Check tokens
		Boolean allowed = false;

		allowed = Security.authenticate( username, password);

		if (validation.hasErrors() || !allowed) {
			flash.keep("url");
			flash.error("secure.error");
			params.flash();
			login();
		}
		// Mark user as connected
		session("username", username);
		// Remember if needed

		if (remember) {
			response().setCookie("rememberme", Cypher.sign(username) + "-"
					+ username, "30d");
		}
		if (request.isAjax()){
			renderArgs.put("result", "true");
			renderArgs.put("role", User.find(User.HQL.BY_LOGIN, username).first().getClass().getSimpleName());
			render("Secure/login.json");
		}
		redirectToOriginalURL();
	}

	public static Result logout() throws Throwable {
		Security.invoke("onDisconnect");
		session().clear();
		Security.invoke("onDisconnected");
		//todo what the fuck is this ? flash.success("secure.logout");

        return Application.index(); //login();
	}

	// ~~~ Utils

	static Result redirectToOriginalURL() throws Throwable {
		Security.invoke("onAuthenticated");
		String url = flash("url");
		if (url == null) {
			url = "/";
		}
        return redirect(url);
	}

}