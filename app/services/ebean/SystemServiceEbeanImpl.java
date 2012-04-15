package services.ebean;

import models.settings.SystemSetting;
import services.SystemService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemServiceEbeanImpl implements SystemService{
    @Override
    public List<SystemSetting> findAllSystemSettings() {
        throw new UnsupportedOperationException("Implement Me");
    }
}
