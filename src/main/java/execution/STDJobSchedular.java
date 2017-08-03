package execution;

import job.SchedulerJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class STDJobSchedular {

	public static void main(String[] args) throws SchedulerException {

		// Seconds Minutes Hours DayOfMonth Month DayOfWeek
		// Cron trigger
		 Trigger trigger =
		 TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.
		 cronSchedule("1 * * * * ?")).build();

		// simple trigger with repeat forever
		/*Trigger simpleTrigger = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.build();*/

		// simple trigger(it is started only once)
		/*
		 * Trigger simpleTrigger1 = TriggerBuilder.newTrigger() .startNow()
		 * .build();
		 */

		JobBuilder jobBuilder = JobBuilder.newJob(SchedulerJob.class);

		JobDetail jobDetail = jobBuilder.build();

		//Standard scheduler
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	
		System.out.println("Start the scheduler");

		scheduler.start();
		System.out.println("scheduler has started");

		scheduler.scheduleJob(jobDetail, trigger);
		System.out.println("scheduler has scheduled the job");

	}

}
