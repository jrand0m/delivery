package unit.services;

import models.Order;
import models.Restaurant;
import models.dto.extern.BasketJSON;
import models.users.User;
import org.junit.Test;
import services.BasketService;
import services.OrderService;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:40 PM
 */
public class BasketServiceTest{
    @Inject
    private BasketService service;
    @Inject
    private OrderService orderService;
    @Test
    public void getBasketAsJSON_(){
        User u = new User();
        Restaurant r = new Restaurant();
        u.id = 43l;
        r.id = 1;
        Order order = orderService.getCurrentOrderFor(u,r);
        if (order == null){
            order =  orderService.createNewOpenOrderFor(u, r);
        }
        BasketJSON j = service.getBasketAsJSON(order);
        assertNotNull(j);
        assertNotNull(j.items);
        assertNotNull(j.delivery);
        assertThat( j.delivery, equalTo(new Integer(1500)));
        assertNotNull(j.no);
        assertNotNull(j.total);


    }
    @Test
    public void getBaskerasJson(){
        assertFalse("TODO", true);
    }
}
