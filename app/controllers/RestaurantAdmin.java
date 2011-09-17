package controllers;

import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import controllers.Secure.Security;
import enumerations.UserRoles;

@With(Secure.class)
public class RestaurantAdmin extends Controller {

    @Check(UserRoles.RESTAURANT)
    public static void showPage() {
        String login = Security.connected();
        render(login);
    }

}
