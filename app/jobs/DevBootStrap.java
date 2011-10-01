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

			if (EndUser.count() > 0) {
				Logger.warn("Database not empty, skiping fixture load");

			} else {
				VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
				Play.javaPath.add(0, appRoot.child("test"));
				try {
					Logger.warn("Loading fixtures!");
					Fixtures.deleteDatabase();
					Fixtures.loadModels("dev_data.yml");
					Logger.warn("Fixtures loaded");
				} catch (Exception e) {
					e.printStackTrace();
					Logger.warn("Fixtures load failed: ", e);
				}
			}
		}

	}
}
