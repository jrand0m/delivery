package guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import play.modules.guice.GuiceSupport;

public class GuiceModule extends GuiceSupport {

    @Override
    protected Injector configure() {
        Module[] applicationConfiguration =
                { new MyBatisConfigModule(), new MyBatisApplicationConfigModule()};
        //      { eBeanApplicationConfigurationModule()};

        Injector injector = Guice.createInjector( applicationConfiguration );
        return injector;
    }

}