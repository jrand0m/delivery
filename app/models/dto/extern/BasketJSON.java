package models.dto.extern;

import models.Order;
import models.OrderItem;
import play.modules.guice.InjectSupport;
import services.OrderService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static helpers.OrderUtils.convertMoneyToCents;

@InjectSupport
public class BasketJSON {
    @Inject
    private static OrderService service;

    public BasketJSON(Order o) {
        if (o == null) {
            return;
        }
        //TODO
        discount = convertMoneyToCents(o.getUserDiscount());
        total = convertMoneyToCents(o.getGrandTotal());
        delivery = convertMoneyToCents(o.getDeliveryPrice());
        //todo move construction of this object to basket service
        for (OrderItem oi : service.getItems(o)) {
            items.add(new OrderItemJSON(oi));
        }
        no = o.id;
    }

    public String no;
    /**
     * 100 = 100%, 30 = 30%
     */
    public Integer discount = 0;
    /**
     * total of order including delivery including discount
     */
    public Integer total = 0;
    /**
     * delivery price, value in coins
     */
    public Integer delivery = 0;
    /**
     * place holder
     */
    public Object events = null;
    /**
     * items of menu
     */
    public List<OrderItemJSON> items = new ArrayList<OrderItemJSON>();


}
