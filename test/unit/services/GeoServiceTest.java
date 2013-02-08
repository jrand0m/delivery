package unit.services;

import models.Restaurant;
import models.geo.City;
import org.junit.Ignore;
import org.junit.Test;
import services.GeoService;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:39 PM
 */
@Ignore
public class GeoServiceTest {
    @Inject
    private GeoService service;

    @Test
    public void getCityByRemoteAddress_ReturnsDefaultCity() {
        City city = service.getCityByRemoteAddress("1.2.3.4");
        assertNotNull(city);
        assertNotNull(city.city_id);
    }

    @Test
    public void getCityById_Returns_City() {
        City c = service.getCityById(1l);
        assertNotNull(c);
        assertNotNull(c.city_id);
        assertNotNull(c.display);
        assertNotNull(c.cityNameKey);
    }

    @Test
    public void getIndexRestaurants() {
        List<Restaurant> list = service.getIndexPageRestsByCity(1l);
        assertNotNull(list);
        assertTrue(list.size() == 1);
    }

    @Test
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
    public void getAllCities() {
        assertTrue(false);
    }

    @Test
    public void updateCity() {
        assertTrue(false);
    }
}
