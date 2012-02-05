/**
 *
 */
package jobs;

import org.joda.money.CurrencyUnit;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * @author Mike On application start for prod env
 */
@OnApplicationStart
public class ProdBootstrap extends Job {
    @Override
    public void doJob() throws Exception {

    }
}
