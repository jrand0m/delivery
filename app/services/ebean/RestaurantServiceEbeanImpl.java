package services.ebean;

import com.avaje.ebean.Ebean;
import models.*;
import models.time.WorkHours;
import services.RestaurantService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestaurantServiceEbeanImpl implements RestaurantService {
    @Override
    public Restaurant getById(Long id) {
        return Ebean.find(Restaurant.class).where().eq("id", id).findUnique();
    }

    @Override
    public List<MenuItemGroup> getMenuBookFor(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public String getLogoPathFor(int id) {
        int count  = Ebean.find(Restaurant.class).where().eq("id",id).findRowCount();
        if (count == 0) return null;
        return "/public/images/restaurants/logos/"+id+".jpg";
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Restaurant getByOrder(Order o) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<RestaurantCategory> getAllCategories() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void setWorkHoursFor(Restaurant restaurant, WorkHours workHours) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public WorkHours insertWorkHours(WorkHours workHours) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public WorkHours getWorkHours(Restaurant restaurant) {

        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public RestaurantCategory insertCategory(RestaurantCategory cat) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public RestaurantCategory getRestaurantCategoryById(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getLastOrdersForDeviceFrom(String device, Long from) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Order> getLastOrdersForDevice(String device) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void updateDevicePing(String device) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<MenuItemGroup> getAllMenuItemGroups() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<MenuItem> getAllMenuItemsFor(Integer id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItemGroup insertMenuGroup(MenuItemGroup group) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItemGroup getMenuGroupById(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItem insertMenuItem(MenuItem item) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Restaurant insertRestaurant(Restaurant restaurant) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void deleteRestaurant(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItemComponent insertItemComponent(MenuItemComponent item) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItemComponent getMenuItemComponentById(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void deleteMenuItemComponent(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void setNewLogo(Long id, File logo) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void updateRating(Integer id, Integer average) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public MenuItemComponent getMenuItemComponent(Long comp) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Comment> findAllCommentsFromLastMonth() {
        throw new UnsupportedOperationException("Implement Me");
    }



    @Override
    public Map<Integer, String> getDescriptionsMapFor(List<Restaurant> restaurants) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        for (Restaurant restaurant: restaurants){
            Integer id = restaurant.getId();
            String description = Ebean.find(RestaurantDescription.class)
                    .where()
                    .eq("restaurantId",id)
                    .eq("lang","ua")
                    .findUnique()
                    .description;
            result.put(id,description);

        }
        return result;
    }

    @Override
    public Map<Integer, WorkHours> getWorkHoursMap(List<Restaurant> restaurants) {
        Map<Integer,WorkHours> result = new HashMap<Integer, WorkHours>(restaurants.size());

        for (Restaurant restaurant: restaurants){
            WorkHours wh = Ebean.find(WorkHours.class).where().eq("id", restaurant.getWorkhours_id()).findUnique() ;
            result.put(restaurant.getId(), wh);
        }
        return result;
    }
}
