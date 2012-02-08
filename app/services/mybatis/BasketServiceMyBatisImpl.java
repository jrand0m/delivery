package services.mybatis;

import models.Order;
import models.dto.extern.BasketJSON;
import models.dto.extern.MenuCompWrapJson;
import services.BasketService;

/**
 * User: Mike Stetsyshyn
 */
public class BasketServiceMyBatisImpl implements BasketService {
    @Override
    public MenuCompWrapJson getComponentsForMenuItem(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BasketJSON getBasketAsJSON(Order order) {
        throw new UnsupportedOperationException();
    }
}
