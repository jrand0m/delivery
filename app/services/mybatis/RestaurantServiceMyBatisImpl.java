package services.mybatis;

import com.google.inject.Inject;
import models.*;
import models.time.WorkHours;
import net.spy.memcached.transcoders.IntegerTranscoder;
import org.apache.commons.lang.builder.ToStringBuilder;
import play.Logger;
import play.Play;
import play.i18n.Lang;
import play.i18n.Messages;
import services.RestaurantService;
import services.mybatis.mappings.RestaurantDescriptionMapper;
import services.mybatis.mappings.RestaurantMapper;

import java.io.File;
import java.util.*;

import static play.Logger.*;

/**
 * User: Mike Stetsyshyn
 */
public class RestaurantServiceMyBatisImpl implements RestaurantService {
    @Inject
    private RestaurantMapper restaurantMapper;
    @Inject
    private RestaurantDescriptionMapper restaurantDescriptionMapper;

    public RestaurantServiceMyBatisImpl(){
        Logger.debug("RestaurantService:RestaurantServiceMyBatisImpl created!");
    }

    @Override
    public Restaurant getById(Long id) {
        return restaurantMapper.selectById(id);
    }

    @Override
    public List<MenuItemGroup> getMenuBookFor(Long id) {

        List<MenuItemGroup> groups = restaurantMapper.getMenuGroupsFor(id);
        
        for (MenuItemGroup grp : groups){
            List<MenuItem> itemz =   restaurantMapper.getMenuItemsFromGroup(grp.id);
            grp.items = itemz;
        }

        return groups;
    }

    @Override
    public String getLogoPathFor(long id) {
        StoredFile file = restaurantMapper.selectLogoFileFor(id);
        return Play.configuration.getProperty("attachments.path") +"/"+ file.id + "." + file.fileExt;
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

    @Override
    public Map<Integer, String> getDescriptionsMapFor(List<Restaurant> restaurants) {
        if (restaurants==null || restaurants.size() == 0){
            return new HashMap<Integer, String>(0);
        }
        HashSet<Integer> ids= new HashSet<Integer>(restaurants.size());
        for (Restaurant r :restaurants){
            ids.add(r.id);
        }
        String lang = Lang.get();
        Integer[] array = ids.toArray(new Integer[ids.size()]);

        StringBuilder arraySQLString = new StringBuilder("{") ; // JDBC has Restriction on IN clause + saving cached prepared statement
        for (Integer i : array){
            arraySQLString.append(i);
            arraySQLString.append(',');
        }
        arraySQLString.deleteCharAt(arraySQLString.length()-1);
        arraySQLString.append('}');
        List<RestaurantDescription> descriptionList  = restaurantDescriptionMapper.selectDescriptionsFor(lang, arraySQLString.toString());
        HashMap<Integer, String>  map = new HashMap<Integer, String>(ids.size(), 1.3f);
        for (RestaurantDescription d : descriptionList ){
            map.put(d.restaurantId, d.description );
            ids.remove(d.restaurantId);
        }
        if (ids.size()!=0){
            for (Integer id : ids){
                warn("Restaurant with id = %d has no description for lang = %s", id, lang);
                map.put(id, Messages.get("restaurant.nodescription")) ;
            }
        }
        return map;
    }

    @Override
    public Map<Integer, WorkHours> getWorkHoursMap(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.size() == 0){
            return new HashMap<Integer, WorkHours>(0);
        }
        HashSet<Integer> set= new HashSet<Integer>(restaurants.size());
        for (Restaurant r :restaurants){
            set.add(r.workhours_id);
        }
        StringBuilder b = new StringBuilder("{");
        for (Integer i : set){
            b.append(i);b.append(',');
        }
        b.deleteCharAt(b.length()-1);
        b.append('}');
        Map<Integer, WorkHours> workHoursList  = restaurantDescriptionMapper.selectWorkHoursFor( b.toString() );
        Map<Integer, WorkHours> result = new HashMap<Integer, WorkHours>(restaurants.size(), 1.3f);
        for (Restaurant r : restaurants){
           result.put(r.id, workHoursList.get(r.workhours_id));
        }
        return  result;
    }
}
