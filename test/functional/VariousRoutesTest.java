package functional;

import org.junit.Ignore;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.routeAndCall;

/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 3/23/13
 * Time: 1:00 AM
 */
@Ignore
public class VariousRoutesTest {
    @Test
    public void badRoute() {
        Result result = route(fakeRequest("GET", "/xx/"));
        assertThat(result).isNull();
        assertThat(false).isEqualTo(true);

    }
}
