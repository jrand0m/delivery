package controllers;

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
