package services.ebean;

import com.avaje.ebean.Ebean;
import models.Restaurant;
import models.geo.Address;
import models.geo.City;
import models.geo.Street;
import play.Logger;
import play.data.validation.Validation;
import services.GeoService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeoServiceEbeanImpl implements GeoService {
    @Override
    public City getCityByRemoteAddress(String remoteAddress) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Street> getStreetsOf(City city) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Restaurant> getIndexPageRestsByCity(Long i) {
        return Ebean.find(Restaurant.class).where().eq("city_id", i).findList();
    }

    @Override
    public List<City> getVisibleCities() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<Restaurant> getRestsByCity(Long l) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public City getCityById(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public City getCityByAlias(String cityName) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public City insertCity(City city) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public List<City> getAllCities() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Street getStreetById(Long streetId) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Street insertStreet(Street streetObj) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Address getAddressById(Long aid) {
        Logger.warn("implement tests for me plz!");
        Address s = Ebean.find(Address.class).where().eq("id",aid).findUnique();
        //use join or annotations to fast-load objects
        Street str = Ebean.find(Street.class).where().eq("id",s.street_id).findUnique();
        City city = Ebean.find(City.class).where().eq("id",s.city_id).findUnique();
//        if (str != null)  s.street = str;
//        if (city != null) s.city = city;
        return s;
    }

    @Override
    public Address validateAndInsertAddress(Address address, Validation validation) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Address insertAddress(Address address) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void updateCity(City city) {
        throw new UnsupportedOperationException("Implement Me");
    }
}
