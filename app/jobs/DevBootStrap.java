package jobs;

import models.users.EndUser;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import play.vfs.VirtualFile;

@OnApplicationStart
public class DevBootStrap extends Job {
	@Override
	public void doJob() {
		if (Play.mode.isDev()) {

		}
	}
}
