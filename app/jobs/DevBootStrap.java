package jobs;


import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class DevBootStrap extends Job {
	@Override
	public void doJob() {
		if (Play.mode.isDev()) {

		}
	}
}
