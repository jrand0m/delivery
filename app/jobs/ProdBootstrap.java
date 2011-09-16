/**
 * 
 */
package jobs;

import models.settings.SystemSetting;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author Mike
 * On application start for prod env
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
	if (SystemSetting.count()==0){
	    Fixtures.loadModels("deafault_settings.yaml");
	}
    }
}
