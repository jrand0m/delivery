package services;

import models.*;
import models.time.WorkHours;

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
}
