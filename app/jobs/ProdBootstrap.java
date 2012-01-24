/**
 * 
 */
package jobs;

import models.settings.SystemSetting;
import play.Logger;
import play.Play;
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
		
	}
}
