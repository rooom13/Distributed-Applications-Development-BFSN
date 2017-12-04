package bicing;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;

/* STATIONS 
 * 
 * Object class for stations list
 * 
 * */

public class Stations {
	// List of stations
	private List<Station> stations;

	// Constructor
	public Stations() {
		stations = new ArrayList<Station>();
	}

	/*
	 * Add Station to list
	 * 
	 */
	public void addStation(Station s) {
		stations.add(s);
	}

	public String toString() {
		String aux = "";
		for (Station station : stations)
			aux += station.toString();
		return aux;
	}

	/*
	 * Gets Station by id Throws NotFoundException if not found
	 * 
	 */
	public Station getStation(String id) {
		for (Station station : stations)
			if (station.getId().equals(id))
				return station;
		throw new NotFoundException();
	}

	// Default getters and setters
	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> s) {
		stations = s;
	}

	/*
	 * Returns String with stats
	 * 
	 * Mean of bikes, mean of slots, opened and closed percentage Total amount of
	 * stations
	 * 
	 */
	public String getStats() {

		String stats = "";
		double bikesMean = 0.0;
		double slotsMean = 0.0;
		double open = 0;
		double closed = 0;
		int total = stations.size();

		// Sum of all bikes, slots, opened and closed
		for (Station s : stations) {
			bikesMean += s.bikes();
			slotsMean += s.slots();

			if (s.isOpen())
				++open;

			else
				++closed;
		}
		// Comput the means
		bikesMean /= total;
		slotsMean /= total;
		open /= total;
		closed /= total;
		stats = "BICING STATIONS STATISTICS" + "\nMean of bikes: " + String.format("%.2f", bikesMean)
				+ "\nMean of slots: " + String.format("%.2f", slotsMean) + "\nPercentage of opened stations:	"
				+ String.format("%.2f", open * 100) + "%\nPercentage of closed stations: "
				+ String.format("%.2f", closed * 100) + "%\nTotal number of stations: " + total;
		return stats;
	}

	/*
	 * Returns slots summary of a list of stations's id
	 * 
	 */
	public String freeSlotsSummary(List<Integer> ss) {
		String line = "";
		for (Integer sid : ss) {
			Station s = this.getStation(sid.toString());
			line += " - Station in " + s.getStreetName() + " num " + s.getStreetNumber() + " has " + s.getSlots()
					+ " avalible.\n\n";
		}
		return line;
	}

	/*
	 * Simple contains method
	 * 
	 */
	public Boolean contains(String id) {
		for (Station s : stations) {
			if (s.getId().equals(id))
				return true;
		}
		return false;
	}

}