package models.dto.extern;

import models.Order;
import models.OrderItem;
import play.modules.guice.InjectSupport;
import services.OrderService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static helpers.OrderUtils.convertMoneyToCents;


public class BasketJSON {

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
