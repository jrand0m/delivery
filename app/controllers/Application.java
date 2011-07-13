package controllers;

import java.util.List;

import models.Adress;
import play.mvc.Controller;
import siena.Model;

public class Application extends Controller {

    public static void index() {

	// List<MenuItem> itemzz = Model.all(MenuItem.class).fetch();
	//
	// render(itemzz);
	List<Adress> a = Model.all(Adress.class).fetch();
	System.out.println(a.get(0).additionalInfo);
	// Adress a = new Adress();
	// a.additionalInfo = "asdfdsf";
	// a.save();
	render(a);
    }

}