package services.ebean;

import models.Order;
import models.OrderItem;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import play.Logger;
import services.CalculationsService;
import services.OrderService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 4/2/13
 * Time: 4:20 PM
 */
public class CalculationsServiceEbeanImpl implements CalculationsService {
    @Inject
    private OrderService orderService;

    @Override
    public Money getMenuTotalFor(Long orderId) {
        Logger.warn("implement tests for me!!!");
        List<OrderItem> items = orderService.getItems(orderId);
        Money result = Money.zero(CurrencyUnit.of("UAH"));
        for (OrderItem itm : items){
            result = result.plus(itm.orderItemPrice);
        }
        return result;
    }

    @Override
    public Money getOrderPriceTotalForUser(Long orderId) {
        throw new UnsupportedOperationException("Implement me");
    }

    @Override
    public Money getDiscountForOrder(Order order) {
        Logger.warn("implement me!!! returning discount = '0 UAH' as stub !");
        return Money.zero(CurrencyUnit.getInstance("UAH"));
    }

    @Override
    public Money getDiscountForOrder(Long orderId) {
        Logger.warn("implement tests for me!!!");
        return getDiscountForOrder(orderService.getById(orderId));
    }

    @Override
    public Money getDeliveryPriceForOrder(Long orderId) {
        Logger.warn("implement tests for me!!!");
        CurrencyUnit u = CurrencyUnit.of("UAH");
        Money total = getMenuTotalFor( orderId);
        if (total.isGreaterThan(Money.ofMajor(u, 150))){
            return Money.zero(u);
        }
        return Money.ofMajor(u,15);
    }
}
