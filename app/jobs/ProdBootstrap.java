/**
 * 
 */
package jobs;

import models.settings.SystemSetting;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Mike On application start for prod env
 */
@OnApplicationStart
public class ProdBootstrap extends Job {
    @Override
    public void doJob() throws Exception {
	loadSystemDefaultSettings();
    }

    /**
     * Loading default values to system if there is no such
     */
    private void loadSystemDefaultSettings() {
	Logger.warn("System settings check..");
	if (SystemSetting.count() == 0) {
	    Logger.warn("No settings found, loading defaults");
	    Fixtures.loadModels("default_settings.yml");
	}
	Logger.warn("Done");
    }
}
