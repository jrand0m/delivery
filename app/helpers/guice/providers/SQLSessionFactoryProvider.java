package helpers.guice.providers;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import play.Logger;
import play.Play;

import com.google.inject.Provider;

public class SQLSessionFactoryProvider implements
		Provider<SqlSessionFactory> {

	private SqlSessionFactory _instance = null;

	@Override
	public SqlSessionFactory get() {
		if (_instance == null){
			String resource = Play.configuration.getProperty("mybatis.configuration.file");
			Logger.info("Reading MyBatis configuration from file : '%s'", resource);
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(resource);
			} catch (IOException e) {
				Logger.error(e,"Failed to read MyBatis configuration file (%s)", resource);
			}
			_instance = new SqlSessionFactoryBuilder().build(reader);
			Logger.info("SqlSessionFactory has been created");
		}
        return _instance;
	}

}
