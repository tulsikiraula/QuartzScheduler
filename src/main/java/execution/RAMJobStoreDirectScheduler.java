package execution;

import java.sql.SQLException;

import job.SchedulerJob;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.jdbcjobstore.InvalidConfigurationException;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.JobStore;

public class RAMJobStoreDirectScheduler {

	public static void main(String[] args) throws SchedulerException,
			SQLException, InvalidConfigurationException {
		
		SimpleThreadPool threadPool = new SimpleThreadPool(2,
				Thread.NORM_PRIORITY);

		Trigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger 1")
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.startNow().build();

		JobBuilder jobBuilder = JobBuilder.newJob(SchedulerJob.class);
		JobDetail jobDetail = jobBuilder.build();

		// create normal direct Scheduler

		// Create JobDataMap object to set attributes
		JobDataMap dataMap = jobDetail.getJobDataMap();
		dataMap.put("Job_Name", "Job 1");
		dataMap.put("Job_Store", "RAM job store");


		final DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory
				.getInstance();
		
		// JobStore’s are responsible for keeping track of all the “work data”
		// that you give to the scheduler: jobs, triggers, calendars, etc
		// RAM job store : it keeps all of its data in RAM
		JobStore jobStore = new RAMJobStore();
		schedulerFactory.createScheduler(threadPool, jobStore);
		Scheduler scheduler1 = schedulerFactory.getScheduler();
		scheduler1.start();

		// Schedule the job
		scheduler1.scheduleJob(jobDetail, simpleTrigger);

		// stop scheduler
		//scheduler1.shutdown();
		
		
	}

}
