package functional;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import controllers.Security;
import controllers.routes;
import guice.MockInjector;
import models.users.User;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;
import services.UserService;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static play.test.Helpers.*;


public class BasketAPIFunctionalTest {
    @BeforeClass
    public static void  before(){
        Injector injector = Guice.createInjector(new MockInjector());
        UserService u = injector.getInstance(UserService.class);
        User uz = new User();
        uz.id = UUID.randomUUID();

        when(u.createAnonymousUser()).thenReturn(uz);
        Security.AbstractAuthenticator.setInjector(injector);
    }
    @AfterClass
    public static void after(){

    }

    @Test
    public void testAddOrderItem() {
        ObjectNode o = Json.newObject();
        FakeRequest f = new FakeRequest();
        Result result = callAction(
                routes.ref.Application.addOrderItem(), f.withJsonBody(o)
        );
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Hello Kiki");
        assertTrue("TODO", false);
    }

    @Test @Ignore
    public void testUnloginedUserPutApiAccess() {
//		Response response = GET("/api/p");
//		assertStatus(403, response);
        assertTrue("TODO", false);
    }

}
