package guice;

import play.db.DB;

import javax.inject.Provider;
import javax.sql.DataSource;

/**
 * User: Mike Stetsyshyn
 * Date: 2/3/12
 * Time: 2:24 AM
 */
public class PlayDataSourceProvider implements Provider<DataSource>, com.google.inject.Provider<DataSource> {

    @Override
    public DataSource get() {
        return DB.getDataSource();
    }
}
