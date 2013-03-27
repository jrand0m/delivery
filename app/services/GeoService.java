package services;

import models.Restaurant;
import models.geo.Address;
import models.geo.City;
import models.geo.Street;
import play.data.validation.Validation;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 11:16 PM
 */
public interface GeoService {
    City getCityByRemoteAddress(String remoteAddress);

    List<Street> getStreetsOf(City city);

    List<Restaurant> getIndexPageRestsByCity(Long i);

    List<City> getVisibleCities();

    List<Restaurant> getRestsByCity(Long l);

    City getCityById(Long id);

    City getCityByAlias(String cityName);

    City insertCity(City city);

    List<City> getAllCities();

    Street getStreetById(Long streetId);

    Street insertStreet(Street streetObj);

    Address getAddressById(Long aid);

    Address validateAndInsertAddress(Address address, Validation validation);

    Address insertAddress(Address address);

    void updateCity(City city);
}
