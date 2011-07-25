package controllers;

import java.util.List;

import models.Client;
import models.MenuItem;
import play.mvc.Controller;
import siena.Model;

public class Application extends Controller {

    public static void index() {

	List<Client> clients = Model.all(Client.class).fetch();
	render(clients);
    }

    public static void showMenu(Long id) {
	Client client = Model.getByKey(Client.class, id);
	List<MenuItem> menuItems = Model.all(MenuItem.class)
		.filter("client", client).fetch();
	renderTemplate("Application/showMenu.html", menuItems);
    }
}