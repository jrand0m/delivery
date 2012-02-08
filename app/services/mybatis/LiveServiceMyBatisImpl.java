package services.mybatis;

import models.dto.extern.LastOrdersJSON;
import models.geo.City;
import services.LiveService;

import java.util.ArrayList;

/**
 * User: Mike Stetsyshyn
 */
public class LiveServiceMyBatisImpl implements LiveService {
    @Override
    public ArrayList<LastOrdersJSON> getLastOrdersForCity(City city) {
        throw new UnsupportedOperationException();
    }
}
