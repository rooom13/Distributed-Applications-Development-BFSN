package BFSN;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.quartz.JobExecutionException;

import bicing.Station;
import bicing.Stations;

@Path("/")
public class BFSNServices {

	// STATIONS SERVICES

	/*
	 * RETURN ALL STATIONS
	 *
	 * Calls getStations static method of BFSNServer
	 */
	@GET
	@Path("stations/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Stations getAllStations() {
		// System.out.println( BFSNServer.getStations().toString() );

		return BFSNServer.getStations();
	}

	/*
	 * RETURN STATION BY ID
	 *
	 * Throws NotFoundException if id does not match with any Calls getStation
	 * static method of BFSNServer
	 */
	@GET
	@Path("stations/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Station getStation(@PathParam("id") String id) {
		// System.out.println(BFSNServer.getStation(id).toString());

		return BFSNServer.getStation(id);

	}

	/*
	 * RETURN NEARBY STATIONS BY ID
	 *
	 * Returns List of Nearby Stations of station with that id Throws
	 * NotFoundException if id does not match with any Calls getNearbyStations
	 * static method of BFSNServer
	 */
	@GET
	@Path("stations/getNearby/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Stations getNearby(@PathParam("id") String id) {
		// System.out.println(BFSNServer.getStation(id).toString());

		return BFSNServer.getNearbyStations(id);

	}

	/*
	 * RETURN STATIONS STATS
	 *
	 * Returns a String with stats (slots, bikes, AVG, percentages...) Calls
	 * getStats static method of BFSNServer
	 */
	@GET
	@Path("stations/stats")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStats() {
		return BFSNServer.getStats();

	}

	// CLIENTS SERVICES

	/*
	 * SUBSCRIBE CLIENT TO STATION LIST
	 *
	 * Subscribe client with num c_num to stations in sss (String Stations Stream)
	 * 
	 * Checks if client does not exist to create a new one.
	 * Checks if client's chat_id does match. Throws NotFoundException if not
	 * Checks if stations of sss exist. Throws NotFoundException if not
	 * 
	 * Calls subscribeClientToStation static method of BFSNServer
	 */
	@PUT
	@Path("clients/subscribe/{c_num}/{c_i}/{sss}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribeNew(@PathParam("c_num") int c_num, @PathParam("c_i") String c_i,
			@PathParam("sss") String sss) {
		Client c = new Client(c_num, Long.parseLong(c_i));
		// If client not exists
		if (!BFSNServer.getClients().contains(c_num))
			// add it
			BFSNServer.addClient(c);
		else {
			// get actual client
			c = BFSNServer.getClient(c_num);
			// If chat_id doesn't match
			if (!(c.getChat_id() == Long.parseLong(c_i))) {
				System.out.println("Chat_id does not match");
				throw new NotFoundException();
			}
		}
		// String with id list --> Integer list
		List<Integer> ss = Pattern.compile(",").splitAsStream(sss).map(Integer::valueOf).collect(Collectors.toList());

		// SuscribeClientToStation
		BFSNServer.subscribeClientToStation(c_num, ss); // subscribe

		return Response.status(200).entity(c).build();
	}

	/*
	 * RETURN ALL CLIENTS
	 *
	 * Calls getStations static method of BFSNServer
	 */
	@GET
	@Path("clients/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Clients getAllClients() {
		// System.out.println( BFSNServer.getClients().toString());
		return BFSNServer.getClients();

	}

	/*
	 * NOTIFY CLIENT BY NUM
	 *
	 * Calls notifySlots static method of BFSNServer
	 * Throws NotFoundException if client does not exist
	 * Returns true if message sent successfully
	 * False if not (client's chat_id is wrong)
	 */
	@PUT
	@Path("clients/notify/{c_num}")
	public Boolean notify(@PathParam("c_num") int c_num) throws JobExecutionException {
		try {
			BFSNServer.notifySlots(c_num);
		} catch (NotFoundException e) {
			System.out.println("Client with num: " + c_num + " does not exist");
			throw new NotFoundException();
		} catch (BadRequestException e) {
			System.out.println("Client with num: " + c_num + " has wrong chat id");
			return false;
		}
		return true;
	}
}
