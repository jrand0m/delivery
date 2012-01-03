package functional;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import play.test.UnitTest;


public class APIFunctionalTest extends FunctionalTest {
	
	@Test
    public void testUnloginedUserGetApiAccess() {
        Response response = GET("/api/g");
        assertStatus(403, response);
    }
	@Test
	public void testUnloginedUserPutApiAccess() {
		Response response = GET("/api/p");
		assertStatus(403, response);
	}

}
