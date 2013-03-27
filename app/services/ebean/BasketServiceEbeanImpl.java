package services.ebean;

import com.avaje.ebean.Ebean;
import models.Order;
import models.OrderItem;
import models.dto.extern.MenuCompWrapJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import services.BasketService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasketServiceEbeanImpl implements BasketService {
    @Override
    public MenuCompWrapJson getComponentsForMenuItem(Long id) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public ObjectNode getBasketAsJSON(Order order) {
        ObjectNode result = Json.newObject();
        result.put("no",order.id);
        result.put("discount",0);
        result.put("total",order.getMenuTotal().plus(order.getDeliveryPrice()).getAmountMinorLong());
        ArrayNode items = result.putArray("items");
        extractItemsFromOrderAsJson(order,items);
        //todo next
        return result;

    }

    private void extractItemsFromOrderAsJson(Order order, ArrayNode node) {
        List<OrderItem> items = Ebean.find(OrderItem.class).where().eq("orderId",order.id).eq("deleted",false).findList();
        for (OrderItem item: items){

        }
    }
}
