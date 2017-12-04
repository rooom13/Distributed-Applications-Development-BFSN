package twitter;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/* CLASS TWITTER JOB
 * Private attributes:
 * 
 * CONSTANTS: tokens and keys, client & webTargets
 * 
 * Twitter to update status
 * 
 */
public class TwitterJob implements Job {
	// Keys & Tokens of TwitterApp (UPF_DAD_2017_)
	private static final String CONSUMER_KEY = "mnsFrsN8uEE044m9hipPZB8FX";
	private static final String CONSUMER_SECRET = "X1IbnfPNBL1KY4HYnZxCa3gH5MXa9w5LnPgDucXnagsuXyNYhW";
	private static final String ACCESS_TOKEN = "936140032888463360-uVi8hTZLd2rIUSjZoZnl72V1bC24iEV";
	private static final String ACCESS_TOKEN_SECRET = "v6c2k6FoQsFuxD1rzLjS2MIlEQUDYa2pDNQ6SQsIeWpHX";

	// Client & Target decaration
	private static final Client BFSNClient = ClientBuilder.newClient();
	private static final WebTarget BFSNTarget = BFSNClient.target("http://localhost:15000/").path("stations/stats");

	// Twitter
	private Twitter twitter;

	public TwitterJob() {
		// Init Twitter
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET));
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Executing TwitterJob at " + new Date());

		// Default status to publish
		String BFSNResponse = "BFSN:\n Stats comming soon...\n" + new Date();
		try {
			// Request BFSNServer's API, obtain stats
			BFSNResponse = BFSNTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<String>() {
			});
			// System.out.println(BFSNResponse);

		} catch (Exception e) {
			System.out.println("Twitter Publisher:\n\tProblem connecting to BFSNServer" + e.getMessage());

		}

		try {
			@SuppressWarnings("unused")
			// Update Twitter status
			Status status = twitter.updateStatus(BFSNResponse);
			System.out.println("Published to twitter: " + BFSNResponse);
		} catch (TwitterException e) { // TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

}
