package execution;

import java.sql.SQLException;

import job.SchedulerJob;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
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

		Trigger simpleTrigger1 = TriggerBuilder.newTrigger()
				.withIdentity(new TriggerKey("trigger 1", "tiggerGroup1")) //trigger 1 belongs to triggerGroup1
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.startNow().build();
		

		Trigger simpleTrigger2 = TriggerBuilder.newTrigger()
				.withIdentity(new TriggerKey("trigger 2", "tiggerGroup1")) //trigger 2 belongs to triggerGroup1
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.startNow().build();
		

		JobBuilder jobBuilder1 = JobBuilder.newJob(SchedulerJob.class);
		JobDetail jobDetail1 = jobBuilder1.withIdentity("job1", "group1").build();
		
		
		JobBuilder jobBuilder2 = JobBuilder.newJob(SchedulerJob.class);
		JobDetail jobDetail2 = jobBuilder2.withIdentity(new JobKey("job2","group2")).build();
		

		// create normal direct Scheduler

		// Create JobDataMap object to set attributes or we can use job key
	/*	JobDataMap dataMap = jobDetail1.getJobDataMap();
		dataMap.put("Job_Name", "Job 1");
		dataMap.put("Job_Group", "Group1");
		dataMap.put("Job_Store", "RAM job store");*/


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
		scheduler1.scheduleJob(jobDetail1, simpleTrigger1);
		scheduler1.scheduleJob(jobDetail2, simpleTrigger2);

		// stop scheduler
		//scheduler1.shutdown();
		
		
	}

}
