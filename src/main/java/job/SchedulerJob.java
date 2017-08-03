package job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SchedulerJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		final Log LOG = LogFactory.getLog(SchedulerJob.class);

		LOG.info("This is the actual code to be executed");
		LOG.info("Fire time" + context.getFireTime());
		LOG.info("Next Fire time" + context.getNextFireTime());
		LOG.info("Current job name :: "
				+ context.getJobDetail().getJobDataMap().get("Job_Name"));
		LOG.info("Current job store name :: "
				+ context.getJobDetail().getJobDataMap().get("Job_Store"));

	}

}
