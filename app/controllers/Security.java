/**
 *
 */
package controllers;

import org.json.JSONException;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import org.json.JSONObject;
/**
 * @author Mike
 */

public class Security extends Controller {

    @Inject static UserService userService;

    /*
    * Provides signed login cookie
    * */
    public static Result authenticate(String username, String password) {
        Logger.trace(String.format("Trying to login as %s", username));
        response().setContentType("application/json");
        JSONObject response = new JSONObject();
        try {
            if (userService.verifyCredentials(username, password)) {
                Logger.trace("Login failed.");
                session("username", username);
                response.put("success", true);
            }  else {
                Logger.trace("Login failed.");
                session().remove("username");
                response.put("success", false);
            }
        } catch (JSONException e) {
            Logger.error("Failed to add params to json response", e);
            return internalServerError();
        }
        return ok(response.toString());
    }

}