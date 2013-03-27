package integration.workflows;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.libs.F;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 3/23/13
 * Time: 12:41 AM
 */
@Ignore
public class RegularUserWorkFlowTest {
    @Test
    public void testNewComerUserCanOrderFromRestaurant() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.$("#title").getTexts().get(0)).isEqualTo("Hello Guest");
                browser.$("a").click();
                assertThat(browser.url()).isEqualTo("http://localhost:3333/Coco");
                assertThat(browser.$("#title", 0).getText()).isEqualTo("Hello Coco");
            }
        });
    }
    @Test
    public void testUserCanAddMultipleItemsFromOneRestaurant() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                assertThat(true).isEqualTo(false);
            }
        });
    }
    @Test
    public void testUserCanChooseComponentsFromItemsThatHaveComponents() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                assertThat(true).isEqualTo(false);
            }
        });
    }
    @Test
    public void testUserCanSeeMultipleRestaurants() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                assertThat(true).isEqualTo(false);
            }
        });
    }
    @Test
    public void testUserOrderFormHasValidationOnRequiredFields() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                assertThat(true).isEqualTo(false);
            }
        });
    }
    @Test
    public void testUserCanSeeAllStagesOnOrderStatusPage() {
        running(testServer(3333), HtmlUnitDriver.class, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                assertThat(true).isEqualTo(false);
            }
        });
    }


}
