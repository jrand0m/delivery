package guice;

import com.google.inject.Module;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.LocalDateTime;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.binder.TypeHandlerBinder;
import play.Logger;
import play.Play;
import services.mybatis.mappings.*;
import services.mybatis.typehandlers.LocalDateTimeTypeHandler;

public class MyBatisConfigModule extends MyBatisModule {

    @Override
    protected void initialize() {
        Logger.debug("Configuring mybatis started");
        Module driverHelper = null;
        /*Properties driverProps = new Properties();
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
        }*/
        //Names.bindProperties(binder(), driverProps);
        /*install(driverHelper);*/
        environmentId(Play.mode.name());
        bindDataSourceProvider(new PlayDataSourceProvider());
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(AddressMapper.class);
        addMapperClass(CityMapper.class);
        addMapperClass(CommentMapper.class);
        addMapperClass(MenuItemComponentMapper.class);
        addMapperClass(MenuItemGroupMapper.class);
        addMapperClass(MenuItemMapper.class);
        addMapperClass(OrderItemMapper.class);
        addMapperClass(OrderMapper.class);
        addMapperClass(RestaurantCategoryMapper.class);
        addMapperClass(RestaurantDescriptionMapper.class);
        addMapperClass(RestaurantMapper.class);
        addMapperClass(StreetMapper.class);
        addMapperClass(SystemSettingsMapper.class);
        addMapperClass(UserMapper.class);
        addMapperClass(WorkHoursMapper.class);
        handleType(LocalDateTime.class).with(LocalDateTimeTypeHandler.class);
        Logger.debug("MyBatisConfig finished");
    }
}
