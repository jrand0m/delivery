package services.ebean;

import models.Order;
import models.dto.extern.BasketJSON;
import models.dto.extern.MenuCompWrapJson;
import services.BasketService;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasketServiceEbeanImpl implements BasketService{
    @Override
    public MenuCompWrapJson getComponentsForMenuItem(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public BasketJSON getBasketAsJSON(Order order) {
        throw new UnsupportedOperationException("Implement Me");
    }
}
