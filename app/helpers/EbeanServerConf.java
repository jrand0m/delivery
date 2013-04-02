package helpers;

import com.avaje.ebean.config.PstmtDelegate;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.ServerConfigStartup;
import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
import play.Logger;


import java.sql.PreparedStatement;

/**
 * Created with IntelliJ IDEA CE.
 * User: mike
 * Date: 3/31/13
 * Time: 10:32 PM
 */
public class EbeanServerConf implements ServerConfigStartup {
    @Override
    public void onStart(ServerConfig serverConfig) {
        serverConfig.setDebugSql(true);
    }
}
