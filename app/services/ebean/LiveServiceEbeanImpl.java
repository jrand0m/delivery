package services.ebean;

import models.dto.extern.LastOrdersJSON;
import models.geo.City;
import services.LiveService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiveServiceEbeanImpl implements LiveService{
    @Override
    public ArrayList<LastOrdersJSON> getLastOrdersForCity(City city) {
        throw new UnsupportedOperationException("TODO");
    }
}
