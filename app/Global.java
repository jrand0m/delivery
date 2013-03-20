import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.Security;
import guice.ServicesConfigurationModule;
import play.Application;
import play.GlobalSettings;
import play.Logger;

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
        Security.AbstractAuthenticator.setInjector( injector);  //todo this is a hack
    }

    private static Injector createInjector() {
        return Guice.createInjector(new ServicesConfigurationModule());
    }

    @Override
    public <A> A getControllerInstance(Class<A> aClass) throws Exception {
        if (Logger.isDebugEnabled()){
            Logger.debug(String.format("Injector injects class -> %s", aClass.getCanonicalName()));
        }
        return injector.getInstance(aClass);
    }
}
