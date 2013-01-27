/**
 *
 */
package controllers;

import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;

/**
 * Security will be controller which performs login and logout functionality
 * also maintains API's to login and logout via JSON request, or simple logins
 * via HTML page.
 * Authenticated user receives its own id in a signed session cookie with
 * key <b>uid</b>.
 *
 * @author Mike
 */

public class Security extends Controller {
    public static String USER_ID_SESSION_KEY = "uid";
    @Inject
    static UserService userService;


    /*
    * Provides signed login cookie
    * */
    public static Result authenticate(String username, String password, Boolean remember) {
        Logger.trace(String.format("Trying to login as %s", username));
        response().setContentType("application/json");
        ObjectNode response = Json.newObject();
        long id = userService.verifyCredentials(username, password);

        if (userService.verifyCredentials(username, password) > 0) {
            Logger.trace("Login Succeed.");
            session(USER_ID_SESSION_KEY, username);
            response.put("success", true);
        } else {
            Logger.trace("Login failed.");
            session().remove(USER_ID_SESSION_KEY);
            response.put("success", false);
        }

        return ok(response.toString());
    }

    public static Result logout() {
        return TODO;
    }
    public static Result login() {
        return TODO;
    }

}