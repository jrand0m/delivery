package helpers.guice;

import org.mybatis.guice.MyBatisModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import play.modules.guice.GuiceSupport;

public class GuiceModule extends GuiceSupport{

    @Override
    protected Injector configure() {
    	MyBatisModule mybatisModule = new MyBatisModule() {
            
            @Override
            protected void initialize() {
                environmentId("production");
            }
        };
        Module WebModule = new AbstractModule() {
			
			@Override
			protected void configure() {
				
			}
		};
        
        Injector injector = Guice.createInjector(WebModule, mybatisModule);
        return injector ;
    }

}