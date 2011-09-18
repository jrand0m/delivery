package controllers;

import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import enumerations.UserRoles;

@With(Secure.class)
@Check(UserRoles.RESTAURANT)
public class RestaurantAdmin extends Controller {

    public static void index() {
	String login = Security.connected();
	render(login);
    }

}
