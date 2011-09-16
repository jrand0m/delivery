package jobs;

import models.User;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.vfs.VirtualFile;

@OnApplicationStart
public class DevBootStrap extends Job {
    @Override
    public void doJob() {
	if (Play.mode.isDev()) {
	    Logger.warn("Loading fixtures!");
	    if (User.count() > 0) {
		Logger.warn("Database not empty, clearing db");
		//Fixtures.deleteDatabase();
	    }
	    VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
	    Play.javaPath.add(0, appRoot.child("test"));
	    try {
		//Fixtures.loadModels("dev_data.yml");
		Logger.warn("fixtures loaded");
	    } catch (Exception e) {
		e.printStackTrace();
		Logger.warn("fixtures load failed: ", e);
	    }
	}

    }
}
