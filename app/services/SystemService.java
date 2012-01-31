package services;

import models.settings.SystemSetting;

import java.util.List;

public interface SystemService {

    List<SystemSetting> findAllSystemSettings();
}
