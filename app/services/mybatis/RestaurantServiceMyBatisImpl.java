package services.mybatis;

import models.*;
import models.time.WorkHours;
import services.RestaurantService;

import java.io.File;
import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public class RestaurantServiceMyBatisImpl implements RestaurantService {
    @Override
    public Restaurant getById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MenuItemGroup> getMenuBookFor(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLogoPathFor(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Restaurant getByOrder(Order o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RestaurantCategory> getAllCategories() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWorkHoursFor(Restaurant restaurant, WorkHours workHours) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorkHours insertWorkHours(WorkHours workHours) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorkHours getWorkHours(Restaurant restaurant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RestaurantCategory insertCategory(RestaurantCategory cat) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RestaurantCategory getRestaurantCategoryById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getLastOrdersForDeviceFrom(String device, Long from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getLastOrdersForDevice(String device) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDevicePing(String device) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MenuItemGroup> getAllMenuItemGroups() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MenuItem> getAllMenuItemsFor(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItemGroup insertMenuGroup(MenuItemGroup group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItemGroup getMenuGroupById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItem insertMenuItem(MenuItem item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Restaurant insertRestaurant(Restaurant restaurant) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteRestaurant(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItemComponent insertItemComponent(MenuItemComponent item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItemComponent getMenuItemComponentById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteMenuItemComponent(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNewLogo(Long id, File logo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateRating(Integer id, Integer average) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItemComponent getMenuItemComponent(Long comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Comment> findAllCommentsFromLastMonth() {
        throw new UnsupportedOperationException();
    }
}
