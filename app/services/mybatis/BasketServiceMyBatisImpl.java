package services.mybatis;

import models.Order;
import models.OrderItem;
import models.dto.extern.BasketJSON;
import models.dto.extern.MenuCompWrapJson;
import models.dto.extern.OrderItemJSON;
import play.modules.guice.InjectSupport;
import services.BasketService;
import services.OrderService;

import javax.inject.Inject;

import static helpers.OrderUtils.convertMoneyToCents;

/**
 * User: Mike Stetsyshyn
 */
@InjectSupport
public class BasketServiceMyBatisImpl implements BasketService {
    @Inject
    private OrderService service;

    @Override
    public MenuCompWrapJson getComponentsForMenuItem(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BasketJSON getBasketAsJSON(Order order) {
        BasketJSON json = new BasketJSON();
        if (order == null) {
            return json ;
        }
        //TODO
        json.discount = convertMoneyToCents(order.getUserDiscount());
        json.total = convertMoneyToCents(order.getGrandTotal());
        json.delivery = convertMoneyToCents(order.getDeliveryPrice());
        //todo move construction of this object to basket service
        for (OrderItem oi : service.getItems(order)) {
            json.items.add(new OrderItemJSON(oi));
        }
        json.no = String.valueOf(order.id);

        return json;
    }
}
