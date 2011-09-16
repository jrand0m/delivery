package controllers;

import models.User;
import annotations.Check;
import controllers.Secure.Security;
import enumerations.UserRoles;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class ClientAdmin extends Controller {

    @Check(UserRoles.CLIENT)
    public static void showPage() {
        String login = Security.connected();
        render(login);
    }

}
