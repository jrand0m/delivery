package services.mybatis;

import models.settings.SystemSetting;
import services.SystemService;

import java.util.List;

/**
 * User: Mike Stetsyshyn
 */
public class SystemServiceMyBatisImpl implements SystemService {
    @Override
    public List<SystemSetting> findAllSystemSettings() {
        throw new UnsupportedOperationException();
    }
}
