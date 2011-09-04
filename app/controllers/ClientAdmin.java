package controllers;

import annotations.Check;
import controllers.Secure.Security;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class ClientAdmin extends Controller {

    @Check("CLIENT")
    public static void showPage() {
        String login = Security.connected();
        render(login);
    }

}
