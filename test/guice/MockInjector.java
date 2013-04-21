package guice;

import com.google.inject.*;
import org.mockito.Mockito;
import services.*;
import services.ebean.*;



/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 4/10/13
 * Time: 5:16 PM
 */
public class MockInjector extends AbstractModule {
    BasketService basketService;
    GeoService geoService;
    LiveService liveService;
    MailService mailService;
    OrderService orderService;
    RestaurantService restaurantService;
    SystemService systemService;
    UserService userService;
    CalculationsService calculationsService;

    @Provides
    public CalculationsService CalculationsServiceProvider(){
        if (calculationsService == null){
            calculationsService = Mockito.mock(CalculationsService.class);
        }
        return calculationsService;
    }
    @Provides
    public UserService UserServiceProvider(){
        if (userService == null){
            userService = Mockito.mock(UserService.class);
        }
        return userService;
    }
    @Provides
    public SystemService SystemServiceProvider(){
        if (systemService == null){
            systemService = Mockito.mock(SystemService.class);
        }
        return systemService;
    }
    @Provides
    public RestaurantService RestaurantServiceProvider(){
        if (restaurantService == null){
            restaurantService = Mockito.mock(RestaurantService.class);
        }
        return restaurantService;
    }
    @Provides
    public OrderService OrderServiceProvider(){
        if (orderService == null){
            orderService = Mockito.mock(OrderService.class);
        }
        return orderService;
    }
    @Provides
    public MailService MailServiceProvider(){
        if (mailService == null){
            mailService = Mockito.mock(MailService.class);
        }
        return mailService;
    }
    @Provides
    public LiveService LiveServiceProvider(){
        if (liveService == null){
            liveService = Mockito.mock(LiveService.class);
        }
        return liveService;
    }
    @Provides
    public GeoService GeoServiceProvider(){
        if (geoService == null){
            geoService = Mockito.mock(GeoService.class);
        }
        return geoService;
    }
    @Provides
    public BasketService BasketServiceProvider(){
        if (basketService == null){
            basketService = Mockito.mock(BasketService.class);
        }
        return basketService;
    }


    @Override
    public void configure() {

    }
}
