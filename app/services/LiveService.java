package services;

import models.dto.extern.LastOrdersJSON;
import models.geo.City;

import java.util.ArrayList;

/**
 * User: Mike Stetsyshyn
 * Date: 1/29/12
 * Time: 1:05 AM
 * Services that produce some useless eye-candy
 */
public interface LiveService {
    ArrayList<LastOrdersJSON> getLastOrdersForCity(City city);
}
