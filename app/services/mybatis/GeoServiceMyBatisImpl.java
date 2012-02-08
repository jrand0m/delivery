package services.mybatis;

import models.Restaurant;
import models.geo.Address;
import models.geo.City;
import models.geo.Street;
import play.data.validation.Validation;
import services.GeoService;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public class GeoServiceMyBatisImpl implements GeoService {
    @Override
    public City getCityByRemoteAddress(String remoteAddress) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Street> getStreetsOf(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Restaurant> getIndexPageRestsByCity(Long i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<City> getVisibleCities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Restaurant> getRestsByCity(Long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public City getCityById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public City getCityByAlias(String cityName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public City insertCity(City city) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<City> getAllCities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Street getStreetById(Long streetId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Street insertStreet(Street streetObj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Address getAddressById(Long aid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Address validateAndInsertAddress(Address address, Validation validation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Address insertAddress(Address address) {
        throw new UnsupportedOperationException();
    }
}
