package unit.services;

import models.Order;
import models.Restaurant;
import models.dto.extern.BasketJSON;
import models.users.User;
import org.junit.Test;
import play.modules.guice.InjectSupport;
import play.test.UnitTest;
import services.BasketService;
import services.OrderService;

import javax.inject.Inject;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:40 PM
 */
@InjectSupport
public class BasketServiceTest extends UnitTest{
    @Inject
    private static BasketService service;
    @Inject
    private static OrderService orderService;
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
        assertEquals(new Integer(1500), j.delivery);
        assertNotNull(j.no);
        assertNotNull(j.total);


    }
    @Test
    public void getBaskerasJson(){
        assertFalse("TODO", true);
    }
}
