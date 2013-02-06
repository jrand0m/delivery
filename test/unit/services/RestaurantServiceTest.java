package unit.services;

import models.Restaurant;
import models.time.WorkHours;
import org.junit.Test;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:43 PM
 */
public class RestaurantServiceTest {

    @Inject
    private RestaurantService service;


    @Test
    public void getWorkHoursMap_returnsFilledMaps() {
        Restaurant rest = service.getById(1l);
        assertNotNull(rest);
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
            //TODO check data for id 1
        }
    }

    @Test
    public void getDescriptionsMapFor_returnsFilledMaps() {
        assertFalse("TODO", true);
    }

    @Test
    public void selectById_returnsRestaurant() {
        Restaurant rest = service.getById(1l);
        assertNotNull(rest);
        assertNotNull(rest.address_id);
        assertNotNull(rest.category_id);
        assertNotNull(rest.city_id);
        assertTrue(rest.deleted == false);
        assertNotNull(rest.deviceLogin);
        assertNotNull(rest.devicePassword);
        assertNotNull(rest.discount);
        assertNotNull(rest.id);
        //assertNotNull(rest.lastPing);
        assertNotNull(rest.raiting);
        assertNotNull(rest.showOnIndex);
        assertNotNull(rest.twoLetters);
        assertNotNull(rest.user_id);
        assertNotNull(rest.title);
        assertNotNull(rest.workhours_id);
        //todo check data
    }

    @Test
    public void getMenuBookFor_FillsSowMenuAndItems() {
        assertFalse("TODO", true);
    }

    @Test
    public void getLogoPathFor_returns_valid_path() {
        assertFalse("TODO", true);
    }

    @Test
    public void createNewOpenOrderFor_createsNewOrderAndReturnsIt() {
        assertFalse("TODO", true);
    }

}