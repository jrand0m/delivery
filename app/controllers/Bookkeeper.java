package controllers;

import java.util.Date;

import play.data.binding.As;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author Mike Stetsyshyn
 * 
 *         class to make accounting and reporting on our income and outcome
 */
@With(Secure.class)
public class Bookkeeper extends Controller {

    public static void index() {
	showReport(null, null, null, true);
    }

    public static void showReport(Long group, @As("dd/MM/yyyy") Date from,
	    @As("dd/MM/yyyy") Date to, boolean excluding) {

    }

    public static void addTransaction() {

    }

    public static void delTransaction() {

    }

    public static void logTransaction() {

    }
}
