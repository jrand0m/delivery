package services;

import models.*;
import models.time.WorkHours;

import java.io.File;
import java.util.List;

/**
 * User: Mike Stetsyshyn
 * Date: 1/29/12
 * Time: 12:17 AM
 */
public interface RestaurantService {
    Restaurant getById(Long id);

    List<MenuItemGroup> getMenuBookFor(Long id);

    String getLogoPathFor(long id);

    MenuItem getMenuItemById(Long id);

    Restaurant getByOrder(Order o);

    List<RestaurantCategory> getAllCategories();

    void setWorkHoursFor(Restaurant restaurant, WorkHours workHours);

    WorkHours insertWorkHours(WorkHours workHours);

    WorkHours getWorkHours(Restaurant restaurant);

    RestaurantCategory insertCategory(RestaurantCategory cat);

    RestaurantCategory getRestaurantCategoryById(Long id);

    /**
     * get it to device service
     */
    List<Order> getLastOrdersForDeviceFrom(String device, Long from);

    /**
     * get it to device service
     */
    List<Order> getLastOrdersForDevice(String device);

    /**
     * get it to device service
     */
    void updateDevicePing(String device);

    List<Restaurant> getAllRestaurants();

    List<MenuItemGroup> getAllMenuItemGroups();

    /**
     * @param id of restaurant
     */
    List<MenuItem> getAllMenuItemsFor(Integer id);

    MenuItemGroup insertMenuGroup(MenuItemGroup group);

    MenuItemGroup getMenuGroupById(Long id);

    MenuItem insertMenuItem(MenuItem item);

    Restaurant insertRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long id);

    MenuItemComponent insertItemComponent(MenuItemComponent item);

    MenuItemComponent getMenuItemComponentById(Long id);

    void deleteMenuItemComponent(Long id);

    void setNewLogo(Long id, File logo);

    void updateRating(Integer id, Integer average);

    MenuItemComponent getMenuItemComponent(Long comp);

    List<Comment> findAllCommentsFromLastMonth();
}
