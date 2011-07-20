package controllers;

import java.util.List;

import models.Client;
import models.MenuItem;
import play.mvc.Controller;
import siena.Model;

public class Application extends Controller {

    public static void index() {

	// List<MenuItem> itemzz = Model.all(MenuItem.class).fetch();
	//
	// render(itemzz);
	// List<Adress> a = Model.all(Adress.class).fetch();
	// System.out.println(a.get(0).additionalInfo);
	// Adress a = new Adress();
	// a.additionalInfo = "asdfdsf";
	// a.save();

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