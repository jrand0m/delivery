package controllers;

import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import enumerations.UserRoles;

@With(Secure.class)
@Check(UserRoles.RESTAURANT_ADMIN)
public class RestaurantAdmin extends Controller {

    public static void summary() {
	String login = Security.connected();
	renderArgs.put("module", "summary");
	render(login);
    }
    public static void showMenu() {
	String login = Security.connected();
	renderArgs.put("module", "showMenu");
	render(login);
    }
    public static void showReports() {
	String login = Security.connected();
	renderArgs.put("module", "showReports");
	render(login);
    }
    public static void showProfile() {
	String login = Security.connected();
	renderArgs.put("module", "showProfile");
	render(login);
    }
    public static void showEvents() {
	String login = Security.connected();
	renderArgs.put("module", "showEvents");
	render(login);
    }
    public static void showInvoices() {
	String login = Security.connected();
	renderArgs.put("module", "showInvoices");
	render(login);
    }
    public static void showShop() {
	String login = Security.connected();
	renderArgs.put("module", "showShop");
	render(login);
    }
    
}
