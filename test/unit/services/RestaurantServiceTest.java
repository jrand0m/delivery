package unit.services;

import com.google.inject.Guice;
import guice.ServicesConfigurationModule;
import models.MenuItem;
import models.MenuItemGroup;
import models.Restaurant;
import models.time.WorkHours;
import org.junit.Ignore;
import org.junit.Test;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
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
    @Ignore
    public void selectById_returnsNoRestaurantIfNotFound() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Restaurant rest = service.getById(0);
                assertTrue("TODO", false);
            }
        });
    }

    @Test
    public void selectById_returnsRestaurantIfFound() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Restaurant rest = service.getById(1);
                assertNotNull(rest);
                assertNotNull(rest.getAddress());
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

    public void getMenuBookFor_ReturnsMenuGroups() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                List<MenuItemGroup> list = service.getMenuGroupsFor(1);
                assertThat(list, notNullValue());
                assertThat(list.size(), is(2));
                MenuItemGroup grp1 = list.get(0);
                MenuItemGroup grp2 = list.get(1);
                assertThat(grp1.name, notNullValue(String.class));
                assertThat(grp2.name, notNullValue(String.class));
                assertThat(grp1.deleted, is(false));
                assertThat(grp2.deleted, is(false));
                assertThat(grp1.description, notNullValue(String.class));
                assertThat(grp2.description, notNullValue(String.class));
            }
        });

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
    }

    @Test
    public void getLogoPathFor_returns_null_if_not_found() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String url = service.getLogoPathFor(0);
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
                assertThat("contains only one element ", map.size(), equalTo(1));
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

    @Test
    public void getAllMenuItemsBy_Returns_ItemsOfAGroup() throws Exception {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                List<MenuItem> items = service.getAllMenuItemsBy(1, 1);
                assertThat("contains only one element ", items.size(), equalTo(2));
                assertThat(items.get(0), notNullValue(MenuItem.class));
                assertThat(items.get(1), notNullValue(MenuItem.class));
                for (MenuItem i : items) {
                    assertThat(i.id, notNullValue());
                    assertThat(i.description, notNullValue());
                    assertThat(i.deleted, notNullValue());
                    assertThat(i.menuItemCreated, notNullValue());
                    assertThat(i.available, notNullValue());
                    assertThat(i.restaurantId, is(1));
                    assertThat(i.menuItemGroupId, is(1));
                }

            }
        });

    }
}