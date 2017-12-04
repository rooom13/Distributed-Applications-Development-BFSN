package bicing;

import java.io.StringReader;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.gson.Gson;

import BFSN.BFSNServer;

/* CLASS BICING JOB
 * Private attributes:
 * 
 * BicingClient	: Client for target bicing API
 * bicinfTarget	: Target to bicing API
 * stations		: Stations list
 * 
 * Provides a station list from www.bicing.cat API's and set it to BFSNServer
 */
public class BicingJob implements Job {

	// Declaration of Client & Target
	private final Client bicingClient = ClientBuilder.newClient();
	private final WebTarget bicingTarget = bicingClient.target("http://wservice.viabicing.cat/").path("v2/stations");

	// Station List
	private static Stations stations;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Executing BicingJob at " + new Date());

		try {
			// Obtain JSON in StringReader format
			StringReader BicingResponse = new StringReader(
					bicingTarget.request(MediaType.APPLICATION_JSON).get(String.class));

			// Gson Converts JSON (StringReader) --> java object, (maven included)
			stations = new Gson().fromJson(BicingResponse, Stations.class);

			// Set stations to BFSNServer's list
			BFSNServer.setStations(stations);

			System.out.println("Stations list updated");
		} catch (Exception e) {
			System.out.println("\tProblem connecting to http://wservice.viabicing.cat\n" + e.getMessage());
		}
	}
}
