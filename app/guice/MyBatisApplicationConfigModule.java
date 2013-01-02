package guice;

import com.google.inject.AbstractModule;
import services.*;
import services.mybatis.*;


/**
 * Intended to use in DAO classes to be able easily
 * create mocks for services and etc.
 *
 * @author mike
 */
public class MyBatisApplicationConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BasketService.class).to(BasketServiceMyBatisImpl.class);
        bind(UserService.class).to(UserServiceMyBatisImpl.class);
        bind(GeoService.class).to(GeoServiceMyBatisImpl.class);
        bind(LiveService.class).to(LiveServiceMyBatisImpl.class);
        bind(MailService.class).to(MailServiceMyBatisImpl.class);
        bind(OrderService.class).to(OrderServiceMyBatisImpl.class);
        bind(RestaurantService.class).to(RestaurantServiceMyBatisImpl.class);
        bind(SystemService.class).to(SystemServiceMyBatisImpl.class);
        bind(UserService.class).to(UserServiceMyBatisImpl.class);
    }

}
