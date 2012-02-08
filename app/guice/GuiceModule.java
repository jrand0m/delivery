package guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import play.modules.guice.GuiceSupport;

public class GuiceModule extends GuiceSupport {

    @Override
    protected Injector configure() {
        Injector injector = Guice.createInjector( new MyBatisConfigModule(), new ApplicationModule());
        return injector;
    }

}