package jobs;


import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class DevBootStrap extends Job {
<<<<<<< HEAD
	@Override
	public void doJob() {
		if (Play.mode.isDev() && !"test".equals(Play.mode.name())) {

		/*	if (EndUser.count() > 0) {
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
			}*/
		}
=======
    @Override
    public void doJob() {
        if (Play.mode.isDev()) {
>>>>>>> master

        }
    }
}
