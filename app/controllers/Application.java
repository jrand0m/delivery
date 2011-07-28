package controllers;

import java.util.Date;
import java.util.List;

import models.Client;
import models.MenuItem;
import models.User;
import play.mvc.Controller;
import play.mvc.Router;
import siena.Model;

public class Application extends Controller {

    public static void index() {

	List<Client> clients = Model.all(Client.class).fetch();
	// List<WorkHours> whorkHours = Model.all(WorkHours.class)
	// .filter("client", clients).fetch();
	// List<Day> days = Model.all(Day.class).filter("workDay", whorkHours)
	// .fetch();
	User user = getCurrentUser();
	render(clients, /* whorkHours, days, */user);
    }

    private static User getCurrentUser() {
	User user = null;
	if (Security.isConnected()) {
	    List<User> users = User.all(User.class)
		    .filter("login", Security.connected()).fetch();
	    if (users.size() == 0) {
		forbidden();
	    }
	    user = users.get(0);
	}
	return user;
    }

    public static void showMenu(Long id) {
	Client client = Model.getByKey(Client.class, id);
	List<MenuItem> menuItems = Model.all(MenuItem.class)
		.filter("client", client).fetch();
	User user = getCurrentUser();
	render(menuItems, user);
    }

    public static void newUser() {
	render();
    }

    public static void registerNewUser(User user) {
	user.joinDate = new Date();
	user.lastLoginDate = new Date();
	user.userStatus = User.UserStatus.ACTIVE;
	user.insert();
	try {
	    Secure.authenticate(user.login, user.password, false);
	} catch (Throwable e) {
	    e.printStackTrace();
	    error();
	}
	redirect(Router.getFullUrl("Application.index"));
    }

}