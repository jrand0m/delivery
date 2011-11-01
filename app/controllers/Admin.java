package controllers;

import models.users.SystemAdministrator;
import annotations.Check;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Check(SystemAdministrator.class)
public class Admin extends Controller {
	public static void index(){
		
	}
}
