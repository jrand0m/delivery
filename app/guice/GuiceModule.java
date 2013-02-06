package guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuiceModule  {

    protected Injector configure() {
        Module[] applicationConfiguration =
              { new eBeanApplicationConfigurationModule()};

        Injector injector = Guice.createInjector( applicationConfiguration );
        return injector;
    }

}