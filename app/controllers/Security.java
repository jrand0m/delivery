package controllers;

import com.google.inject.Injector;
import models.users.User;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import services.UserService;

import javax.inject.Inject;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
    public static String USER_ID_SESSION_KEY = "u";
    @Inject
    static UserService userService;



    /*
    * Provides signed login cookie
    * */
    public static Result authenticate(String username, String password, Boolean remember) {
        if (Logger.isTraceEnabled()){
            Logger.trace(String.format("Trying to login as %s", username));
        }
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

        return ok(response.toString()).as("application/json");
    }

    public static Result logout() {
        return TODO;
    }
    public static Result login() {
        return TODO;
    }


    /**
     * Wraps the annotated action in an <code>AuthenticatedAction</code>.
     */
    @With(AuthenticatedAction.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Authenticated {
        Class<? extends AbstractAuthenticator> value() default AbstractAuthenticator.class;
    }

    public static class AuthenticatedAction extends Action<Authenticated> {

        public Result call(Http.Context ctx) {
            try {
                AbstractAuthenticator authenticator = configuration.value().newInstance();
                String username = authenticator.getUsername(ctx);
                if(username == null) {
                    return authenticator.onUnauthorized(ctx, delegate);
                } else {
                    try {
                        ctx.request().setUsername(username);
                        return delegate.call(ctx);
                    } finally {
                        ctx.request().setUsername(null);
                    }
                }
            } catch(RuntimeException e) {
                throw e;
            } catch(Throwable t) {
                throw new RuntimeException(t);
            }
        }

    }


    public static class LoggedInOrCreateAnonymous extends AbstractAuthenticator {

        @Override
        public Result onUnauthorized(Http.Context ctx, Action delegate) throws Throwable{
            User u = getUserService().createAnonymousUser();
            ctx.session().put(USER_ID_SESSION_KEY,u.id.toString());
            return delegate.call(ctx);
        }
    }

    /**
     * Basic authentication handler
     */
    public static abstract class AbstractAuthenticator extends Results {

        private static Injector injector;

        final protected UserService getUserService(){
            return injector.getInstance(UserService.class);
        }

        final public static void setInjector(Injector injector) {
            if (injector != null)
                AbstractAuthenticator.injector = injector;
        }

        /**
         * Retrieves the username from the HTTP context; the default is to read from the session cookie.
         * @todo null is bad idea :/
         * @return null if the user is not authenticated.
         */
        public String getUsername(Http.Context ctx) {
            return ctx.session().get(USER_ID_SESSION_KEY);
        }

        /**
         * Generates an alternative result if the user is not authenticated;
         * the default a simple '401 Not Authorized' page.
         */
        public Result onUnauthorized(Http.Context ctx, Action delegate) throws Throwable {
            return unauthorized(views.html.defaultpages.unauthorized.render());
        }
    }

}