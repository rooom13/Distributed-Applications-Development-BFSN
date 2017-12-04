package BFSN;

import com.sun.net.httpserver.HttpServer;

import bicing.BicingJob;
import bicing.Station;
import bicing.Stations;
import telegram.GetUpdatesJob;
import telegram.SendMessagesJob;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

@SuppressWarnings("restriction")
public class BFSNServer {

	/*
	 * BFSN SERVER, main program This Class handles the Server and publishes its API
	 * 
	 * It has an interface with statics methods to be accessed
	 * 
	 */

	// Main structures
	private static Stations stations = new Stations();
	private static Clients clients = new Clients();

	// GetUpdates variables declaration
	public static int nUpdates;
	public static boolean firstTime;

	public static void main(String[] args) throws IOException, SchedulerException {
		//Server mount
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(15000).build();
		ResourceConfig config = new ResourceConfig(BFSNServices.class);
		@SuppressWarnings("unused")
		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
		System.out.println("BFSN Server started...");

		// GetUpdates variables init
		firstTime = true;
		nUpdates = 0;

		/* <-- GET STATIONS JOB --> */
		// Specify the bicingJob' s details..
		JobDetail bicingJob = JobBuilder.newJob(BicingJob.class).withIdentity("bicingJob").build();

		// Specify the running period of the bicingJob
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();

		// Schedule the bicingJob
		Scheduler sched = new StdSchedulerFactory().getScheduler();
		sched.start();
		sched.scheduleJob(bicingJob, trigger);

		/* <-- GET UPDATES JOB --> */
		// Specify the GetUpdatesJob details..
		JobDetail getUpdatesJob = JobBuilder.newJob(GetUpdatesJob.class).withIdentity("getUpdatesJob").build();

		// Specify the running period of the GetUpdatesJob
		Trigger trigger2 = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

		// Schedule the getUpdatesJon
		Scheduler sched2 = new StdSchedulerFactory().getScheduler();
		sched2.start();
		sched2.scheduleJob(getUpdatesJob, trigger2);

		//clients.addClient(new Client(644327614, 335813769));

	}

	// STATIONS STUFF
	
	/* Allows BicingJob to update stations list
	 * 
	 * */
	public static void setStations(Stations st) {
		stations = st;
	}
	
	/* Allows Client read all existing Stations
	 * 
	 * */
	public static Stations getStations() {
		return stations;
	}

	/* Returns Station by ID
	 * 
	 * */
	public static Station getStation(String stid) {
		return stations.getStation(stid);
	}

	/* Returns nearby stations list of an Station by its ID
	 * 
	 * */
	public static Stations getNearbyStations(String id) {
		Stations nearbys = new Stations();

		// Nearby stations String list from Station id,
		String[] nearbyIds = getStation(id).getNearbyStations().split(", "); // Split: separate each ', '
		// Get Station references
		for (String ids : nearbyIds)
			nearbys.addStation(getStation(ids));
		// Return
		return nearbys;

	}

	/* Returns String with stats
	 * 
	 * Mean of bikes and slots, percentages...
	 * 
	 * */
	public static String getStats() {
		return stations.getStats() + "\n" + new Date();
	}

	// CLIENTS STUFF
	
	/* Returns clients list
	 * 
	 * */
	public static Clients getClients() {
		return clients;
	}

	/* Add new client to clients
	 * 
	 * */
	public static void addClient(Client c) {
		clients.addClient(c);
	}

	/* Retrieve client by its num
	 * 
	 * */
	public static Client getClient(int c) {
		return clients.getClient(c);
	}
	
	/* Retrieve client by its num
	 * 
	 * */
	public static Client getClient(long c) {
		return clients.getClient(c);
	}
	

	/* Suscribes Client to id list of stations id
	 * 
	 * */
	public static void subscribeClientToStation(int c, List<Integer> ss) {
		clients.subscribeClientToStation(c, ss);
	}


	/* Sends a Telegram message to client by its num
	 * 
	 * */
	public static void notifySlots(int num) throws JobExecutionException {
		//Find client 
		Client c = clients.getClient(num);
		//Send message to client
		sendMessage(c.getChat_id(), c.freeSlotsStations());
	}

	/* Sends message text to chat_id 
	 * 
	 * Uses TelegramJob
	 * */
	public static void sendMessage(long chat_id, String text) throws JobExecutionException {
		// Telegram Job declaration
		SendMessagesJob sendMessagesJob = new SendMessagesJob();
		// Set Job Arguments 
		sendMessagesJob.setChat_id(chat_id);
		sendMessagesJob.setFreeSlots(text);
		// Execute Job 
		sendMessagesJob.execute(null);
	}

	/* Simple contains method
	 * 
	 * */
	public static Boolean containsStation(String id) {
		if (stations.contains(id))
			return true;
		else
			return false;
	}
}