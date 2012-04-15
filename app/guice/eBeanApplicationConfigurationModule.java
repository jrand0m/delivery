package guice;

import com.google.inject.AbstractModule;
import services.*;
import services.ebean.*;


/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/11/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class eBeanApplicationConfigurationModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(BasketService.class).to(BasketServiceEbeanImpl.class);
        bind(GeoService.class).to(GeoServiceEbeanImpl.class);
        bind(LiveService.class).to(LiveServiceEbeanImpl.class);
        bind(MailService.class).to(MailServiceEbeanImpl.class);
        bind(OrderService.class).to(OrderServiceEbeanImpl.class);
        bind(RestaurantService.class).to(RestaurantServiceEbeanImpl.class);
        bind(SystemService.class).to(SystemServiceEbeanImpl.class);
        bind(UserService.class).to(UserServiceEbeanImpl.class);
    }
}
