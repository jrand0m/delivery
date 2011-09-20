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
	render(login);
    }
    public static void showMenu() {
	String login = Security.connected();
	render(login);
    }
    public static void showReports() {
	String login = Security.connected();
	render(login);
    }
    public static void showProfile() {
	String login = Security.connected();
	render(login);
    }
    public static void showEvents() {
	String login = Security.connected();
	render(login);
    }
    public static void showInvoices() {
	String login = Security.connected();
	render(login);
    }
    public static void showShop() {
	String login = Security.connected();
	render(login);
    }
    
}
