package guice;

import com.google.inject.Module;
import com.google.inject.name.Names;
import helpers.persistance.dao.samples.SampleMapper;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import play.Logger;
import play.Play;

import java.util.Properties;

public class MyBatisConfigModule extends MyBatisModule {

    @Override
    protected void initialize() {
        Logger.debug("Configuring mybatis started");
        Module driverHelper = null;
        Properties driverProps = new Properties();
        if (Play.runingInTestMode() || Play.id == null) {
            Logger.debug("Using in-mem db config");
            driverHelper = JdbcHelper.H2_IN_MEMORY_NAMED;
            driverProps.setProperty("JDBC.schema", "play");
            driverProps.setProperty("JDBC.username", "sa");
            driverProps.setProperty("JDBC.password", "");
        } else if (Play.mode.isProd()) {
            Logger.debug("Using postgresql config");
            driverHelper = JdbcHelper.PostgreSQL;
            driverProps.setProperty("JDBC.schema", "vdoma_db");
            driverProps.setProperty("JDBC.username", "vdoma_usr");
            driverProps.setProperty("JDBC.password", "271828183");
            driverProps.setProperty("JDBC.port", "5432");
            driverProps.setProperty("JDBC.host", "127.0.0.1");
        }
        environmentId(Play.mode.name());
        Names.bindProperties(binder(), driverProps);
        install(driverHelper);
        bindDataSourceProviderType(PooledDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(SampleMapper.class);
        //TODO 1) make mybatis config load from application conf file(i mean database settings dependant on test(must load in-mem db)/prod/dev)
        //TODO 2) make evolutions work(use mybatis evolutions framework, mb it is cooler!)!
        Logger.debug("MyBatisConfig finished");
    }
}
