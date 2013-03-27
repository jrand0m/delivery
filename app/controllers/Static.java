package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Static extends Controller {

    public static Result about() {
        return ok(views.html.Static.about.render());
    }

    public static Result rules() {
        return ok(views.html.Static.rules.render());
    }

    public static Result forRestaurants() {
        return ok(views.html.Static.forRestaurants.render());
    }

    public static Result contacts() {
        return ok(views.html.Static.contacts.render());
    }
}