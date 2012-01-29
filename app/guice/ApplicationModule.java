package guice;

import com.google.inject.AbstractModule;
import helpers.persistance.dao.samples.SampleService;
import helpers.persistance.dao.samples.SampleServiceImpl;

/**
 * Intended to use in DAO classes to be able easily 
 * create mocks for services and etc.
 * 
 * @author mike
 * */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
        bind(SampleService.class).to(SampleServiceImpl.class);
	}
	
}
