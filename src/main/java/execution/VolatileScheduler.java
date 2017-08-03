package execution;

import job.SchedulerJob;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;

public class VolatileScheduler {

	public static void main(String[] args) throws SchedulerException {

		Trigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger 1")
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.startNow().build();

		JobBuilder jobBuilder = JobBuilder.newJob(SchedulerJob.class);
		JobDetail jobDetail = jobBuilder.build();

		// createVolatileScheduler: a scheduler that does not write anything to
		// the database
		final DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory
				.getInstance();

		schedulerFactory.createVolatileScheduler(2);
		schedulerFactory.getScheduler().start();
		System.out.println(schedulerFactory.getScheduler().getSchedulerName());
		schedulerFactory.getScheduler().scheduleJob(jobDetail, simpleTrigger);
		System.out
				.println("///////////////////////////////////////////////////////");
		//schedulerFactory.getScheduler().shutdown();
	}

}
