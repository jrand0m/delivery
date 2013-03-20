package unit.services;

import com.google.inject.Guice;
import guice.ServicesConfigurationModule;
import models.Restaurant;
import models.time.WorkHours;
import org.junit.Ignore;
import org.junit.Test;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:43 PM
 */
public class RestaurantServiceTest {

    @Inject
    private RestaurantService service = Guice.createInjector(new ServicesConfigurationModule()).getInstance(RestaurantService.class);

    @Test
    public void getWorkHoursMap_returnsFilledMaps() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Restaurant rest = mock(Restaurant.class);
                when(rest.getWorkhours_id()).thenReturn(1);//todo get real id from db
                List<Restaurant> list = new ArrayList<Restaurant>(1);
                list.add(rest);
                Map<Integer, WorkHours> map = service.getWorkHoursMap(list);
                assertTrue(map.keySet().size() == 1);
                for (Integer i : map.keySet()) {
                    WorkHours wh = map.get(i);
                    assertNotNull(wh);
                    assertNotNull(wh.id);
                    assertNotNull(wh.mon_end);
                    assertNotNull(wh.mon_start);
                    assertNotNull(wh.tue_end);
                    assertNotNull(wh.tue_start);
                    assertNotNull(wh.wed_end);
                    assertNotNull(wh.wed_start);
                    assertNotNull(wh.thu_end);
                    assertNotNull(wh.thu_start);
                    assertNotNull(wh.fri_end);
                    assertNotNull(wh.fri_start);
                    assertNotNull(wh.sat_end);
                    assertNotNull(wh.sat_start);
                    assertNotNull(wh.sun_end);
                    assertNotNull(wh.sun_start);
                }
            }
        });
    }

    @Test
    @Ignore
    public void getDescriptionsMapFor_returnsFilledMaps() {
        assertFalse("TODO", true);
    }

    @Test
    public void selectById_returnsRestaurant() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Restaurant rest = service.getById(1l);
                assertNotNull(rest);
                assertNotNull(rest.getAddress_id());
                assertNotNull(rest.getCategory_id());
                assertNotNull(rest.getCity_id());
                assertFalse(rest.isDeleted());
                assertNotNull(rest.getDeviceLogin());
                assertNotNull(rest.getDevicePassword());
                assertNotNull(rest.getDiscount());
                assertNotNull(rest.getId());
                //assertNotNull(rest.lastPing);
                assertNotNull(rest.getRaiting());
                assertNotNull(rest.isShowOnIndex());
                assertNotNull(rest.getTwoLetters());
                assertNotNull(rest.getUser_id());
                assertNotNull(rest.getTitle());
                assertNotNull(rest.getWorkhours_id());
            }
        });
    }

    @Test
    @Ignore
    public void getMenuBookFor_FillsSowMenuAndItems() {

        assertFalse("TODO", true);
    }

    @Test
    public void getLogoPathFor_returns_valid_path() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String url = service.getLogoPathFor(1);
                assertThat(url, equalTo("/public/images/restaurants/logos/1.jpg"));
            }
        });

        assertFalse("TODO", true);
    }
    @Test
    public void getLogoPathFor_returns_null_if_not_found() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String url = service.getLogoPathFor(2);
                assertThat(url, nullValue());
            }
        });
    }

    @Test
    @Ignore
    public void createNewOpenOrderFor_createsNewOrderAndReturnsIt() {
        assertFalse("TODO", true);
    }

    @Test
    public void testGetDescriptionMap_ReturnsDescriptionsForCurrentSetOfRestaurants() throws Exception {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Restaurant rest = mock(Restaurant.class);
                List<Restaurant> list = new ArrayList<Restaurant>(1);
                when(rest.getId()).thenReturn(1);
                list.add(rest);
                Map<Integer, String> map = service.getDescriptionsMapFor(list);
                assertThat("contains only one element ", map.size(),equalTo(1));
                String description = map.get(1);
                assertThat(description, equalTo("Test Description"));
            }
        });

    }

    @Test
    @Ignore
    public void testGetDescriptionMap_ReturnsDescriptionsForCurrentLanguage() throws Exception {
        assertFalse("TODO", true);

    }

    @Test
    @Ignore
    public void testGetDescriptionMap_DefaultsToDefaultLanguageIfNoTranslationAndResultsWarning() throws Exception {
        assertFalse("TODO", true);
    }
}