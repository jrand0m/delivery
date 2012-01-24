package helpers.guice;

import helpers.guice.providers.MyBatisSQLSessionFactoryProvider;
import helpers.guice.providers.SQLSessionProvider;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
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
		bind(SqlSession.class).toProvider(SQLSessionProvider.class);
		bind(SqlSessionFactory.class).toProvider(MyBatisSQLSessionFactoryProvider.class);
	}
	
}
