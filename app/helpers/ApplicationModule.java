package helpers;

import models.session.DBSession;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
/**
 * Intended to use in DAO classes to be able easily 
 * create mocks for services and etc.
 * 
 * @author mike
 * */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		//bind(DBSession.class).in(Singleton.class);
	}

}
