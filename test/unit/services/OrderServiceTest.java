package unit.services;

import com.avaje.ebean.Ebean;
import com.google.inject.Guice;
import guice.ServicesConfigurationModule;
import models.Order;
import models.users.User;
import org.junit.Ignore;
import org.junit.Test;
import services.OrderService;
import services.UserService;

import javax.inject.Inject;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * User: Mike Stetsyshyn
 * Date: 2/11/12
 * Time: 2:43 PM
 */

public class OrderServiceTest {
    @Inject
    private OrderService service = Guice.createInjector(new ServicesConfigurationModule()).getInstance(OrderService.class);
    @Inject
    private UserService userService = Guice.createInjector(new ServicesConfigurationModule()).getInstance(UserService.class);

    @Test
    @Ignore
    public void _() {
        assertFalse("TODO", true);
    }

    @Test
    public void getCurrentOrderFor_returnsValidOrderInCaseOfValidUserAndRestaurant() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Ebean.beginTransaction();
                try {
                    User u = userService.createAnonymousUser();

                    Order o = service.getCurrentOrderFor(u.id, 1);
                    assertThat(o, notNullValue());
                    assertThat(o.restaurant_id, equalTo(1));
                    assertThat(o.order_owner_id, equalTo(u.id));
                } finally {
                    Ebean.rollbackTransaction();
                }

            }
        });
    }

    @Test
    @Ignore
    public void getCurrentOrderFor_returnsNewOrderInCaseOfValidUserAndRestaurantAndNoOrderIsInProgress() {
        assertFalse("TODO", true);
    }

    @Test
    public void getCurrentOrderFor_returnsNullInCaseOfInValidUserOrRestaurant() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Ebean.beginTransaction();
                try {
                    User u = userService.createAnonymousUser();
                    Order o = service.getCurrentOrderFor(u.id, -2);
                    assertThat(o, nullValue());
                    Ebean.rollbackTransaction();
                    Ebean.beginTransaction();
                    o = service.getCurrentOrderFor(UUID.randomUUID(), 1);
                    assertThat(o, nullValue());
                } finally {
                    Ebean.rollbackTransaction();
                }

            }
        });
    }

    @Test
    public void createNewOpenOrderFor_ReturnsNewOpenOrderForSpecifiedUserAndRestaurantWithAllFieldsInitialized() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Ebean.beginTransaction();
                try {
                    User u = userService.createAnonymousUser();
                    Order o = service.createNewOpenOrderFor(u.id, 1);
                    assertThat(o, notNullValue());
                    assertThat(o.restaurant_id, equalTo(1));
                    assertThat(o.order_owner_id, equalTo(u.id));

                } finally {
                    Ebean.rollbackTransaction();
                }
            }
        });

    }

    @Test
    @Ignore
    public void getItems_() {
        assertFalse("TODO", true);
    }

}