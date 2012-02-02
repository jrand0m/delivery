package services;

import models.Order;
import models.dto.extern.BasketJSON;
import models.dto.extern.MenuCompWrapJson;

/**
 * User: Mike Stetsyshyn
 * Date: 1/29/12
 * Time: 1:17 AM
 * <p/>
 * basket serving stuff
 */
public interface BasketService {
    MenuCompWrapJson getComponentsForMenuItem(Long id);

    BasketJSON getBasketAsJSON(Order order);
}
