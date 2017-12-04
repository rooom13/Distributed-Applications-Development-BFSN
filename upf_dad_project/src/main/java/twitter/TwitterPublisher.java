package twitter;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/* TWITTER PUBLISHER to be executed independtly of BFSNServer
 * 
 * Gets stats from BFSNServer's API and publishes it on Twitter
 * 
 * 
 * */
public class TwitterPublisher {

	public static void main(String[] args) throws SchedulerException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		TwitterJob twitterJob = new TwitterJob();

		// Set twitterJob an Scheduled job
		JobDetail bicingJob = JobBuilder.newJob(TwitterJob.class).withIdentity("twitterJob").build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(120).repeatForever())
				.build();
		// Execute it
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.start();
		sched.scheduleJob(bicingJob, trigger);

	}

}
