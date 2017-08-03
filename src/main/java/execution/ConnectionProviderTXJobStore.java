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
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.quartz.utils.PoolingConnectionProvider;

public class ConnectionProviderTXJobStore {

	public static void main(String[] args) throws SQLException, SchedulerException {
		
		SimpleThreadPool threadPool = new SimpleThreadPool(2,
				Thread.NORM_PRIORITY);

		Trigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger 10")
				.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
				.startNow().build();

		JobBuilder jobBuilder = JobBuilder.newJob(SchedulerJob.class);
		JobDetail jobDetail = jobBuilder.build();
		
		final DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory
				.getInstance();
		
		// scheduler with connection provider and JobStoreTX

				JobDataMap dataMap1 = jobDetail.getJobDataMap();
				dataMap1.put("Job_Name", "Job 2");
				dataMap1.put("Job_Store", "TX job store");

				// JDBC job store: it keeps all of its data in a database via JDBC
				JobStoreTX jobStoreTX = new JobStoreTX();
				jobStoreTX.setTablePrefix("QRTZ_");
				jobStoreTX.setDataSource("Tulsi");

				// Connection provider to make connection with database(tables should be present in db)
				ConnectionProvider connectionProvider = new PoolingConnectionProvider(
						"oracle.jdbc.driver.OracleDriver",
						"jdbc:oracle:thin:@10.151.57.58:1521:orcl", "kiraula1",
						"kiraula1", 5, null);
				DBConnectionManager.getInstance().addConnectionProvider("Tulsi",
						connectionProvider);
				
				schedulerFactory.createScheduler(threadPool, jobStoreTX);
				Scheduler scheduler2 = schedulerFactory.getScheduler();
				
				//start the scheduler
				scheduler2.start();

				// Schedule the job
				scheduler2.scheduleJob(jobDetail, simpleTrigger);
				


	}

}
