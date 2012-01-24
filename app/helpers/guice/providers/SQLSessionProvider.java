package helpers.guice.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class SQLSessionProvider implements Provider<SqlSession> {
	private SqlSessionFactory factory = null;
	
	@Inject
	public SQLSessionProvider(SqlSessionFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public SqlSession get() {
		
		return factory.openSession();
	}

}
