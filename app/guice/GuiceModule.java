package guice;

import org.mybatis.guice.MyBatisModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import play.Play;
import play.modules.guice.GuiceSupport;

public class GuiceModule extends GuiceSupport{

    @Override
    protected Injector configure() {
        Injector injector = Guice.createInjector(new ApplicationModule(), new MyBatisConfigModule());
        return injector ;
    }

}