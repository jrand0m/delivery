import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.eBeanApplicationConfigurationModule;
import play.Application;
import play.GlobalSettings;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 2/11/13
 * Time: 1:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {
    private static Injector injector;

    @Override
    public void onStart(Application application) {
        super.onStart(application);
        injector = createInjector();
    }

    private static Injector createInjector() {
        return Guice.createInjector(new eBeanApplicationConfigurationModule());
    }

    @Override
    public <A> A getControllerInstance(Class<A> aClass) throws Exception {
        return injector.getInstance(aClass);
    }
}
