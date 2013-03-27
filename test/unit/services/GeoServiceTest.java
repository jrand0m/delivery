package unit.services;

import com.google.inject.Guice;
import guice.ServicesConfigurationModule;
import models.Restaurant;
import models.geo.City;
import org.junit.Ignore;
import org.junit.Test;
import services.GeoService;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:39 PM
 */

public class GeoServiceTest {
    @Inject
    private GeoService service =  Guice.createInjector(new ServicesConfigurationModule()).getInstance(GeoService.class);

    @Test
    @Ignore
    public void getCityByRemoteAddress_ReturnsDefaultCity() {
        City city = service.getCityByRemoteAddress("1.2.3.4");
        assertNotNull(city);
        assertNotNull(city.city_id);
    }

    @Test
    @Ignore
    public void getCityById_Returns_City() {
        City c = service.getCityById(1l);
        assertNotNull(c);
        assertNotNull(c.city_id);
        assertNotNull(c.display);
        assertNotNull(c.cityNameKey);
    }

    @Test
    public void getIndexRestaurants() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                List<Restaurant> list = service.getIndexPageRestsByCity(1l);
                assertNotNull(list);
                assertTrue(list.size() == 1);
            }
        });
    }

    @Test
    @Ignore
    public void getVivsibleCities() {
        List<City> cities = service.getVisibleCities();
        assertNotNull(cities);
        for (City city : cities) {
            assertNotNull(city.city_id);
            assertNotNull(city.cityNameKey);
            // todo add alias check
            assertTrue("City not visible id = " + city.city_id, city.display);
        }
    }

    @Test
    @Ignore
    public void getAllCities() {
        assertTrue(false);
    }

    @Test
    @Ignore
    public void updateCity() {
        assertTrue(false);
    }
}
