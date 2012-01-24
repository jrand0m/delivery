package functional.application;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import play.test.FunctionalTest;
import play.mvc.Http.Response;

public class GeneralApplicationFunctionalTest extends FunctionalTest {
	
	@Test
	public void renderIndexPageWithoutErrors(){
		Response r = GET("/");
		assertIsOk(r);
		Document doc = Jsoup.parse(getContent(r));
		assertNotSame("Application error", doc.getElementsByTag("title").get(0).text());
	}

}
