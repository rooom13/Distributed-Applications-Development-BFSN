package BFSN;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.NotFoundException;

/* CLIENT (aka USER)
 * 
 * Object class for client object.
 * 
 * */

public class Client {

	// ID
	private int num;
	// Telegram chat_id linker
	private long chat_id;
	// SubscribedStations id list
	private List<Integer> subStations;

	// DEFAULT CONSTRUCTORS, GETTERS AND SETTERS
	public Client() {
		num = 0;
		subStations = new ArrayList<Integer>();
		chat_id = -1;
	}

	public Client(int n, long c_i) {
		num = n;
		subStations = new ArrayList<Integer>();
		chat_id = c_i;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int n) {
		this.num = n;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long n) {
		this.chat_id = n;
	}

	public List<Integer> getSubStations() {
		return subStations;
	}

	public void setSubStations(List<Integer> ss) {
		this.subStations = ss;
	}

	/*
	 * Adds non-existing station ids to subStations id list
	 * 
	 */
	public void subscribe(List<Integer> ss) {
		// For each s_id not in the list add
		for (Integer sid : ss) {
			// If station does not exist
			if (!BFSNServer.containsStation(sid.toString())) {
				System.out.println("Station with id: " + sid.toString() + " not found");
				throw new NotFoundException();
			}
			// If its already subscribed, don't duplicate
			if (!subStations.contains(sid))
				subStations.add(sid.intValue());
		}
	}

	public String toString() {
		return "Client with num: " + num + ",  chat_id: " + chat_id + ", Sub Stations: " + subStations.toString();
	}

	/* Returns freeSlots summary
	 * 
	 * */
	public String freeSlotsStations() {
		String line = "Free slots summary:\n";
		return line + BFSNServer.getStations().freeSlotsSummary(subStations);

	}
}
