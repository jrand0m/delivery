package controllers;

import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;
import controllers.Secure.Security;
import enumerations.UserRoles;

@With(Secure.class)
public class ClientAdmin extends Controller {

    @Check(UserRoles.CLIENT)
    public static void showPage() {
        String login = Security.connected();
        render(login);
    }

}
