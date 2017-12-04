package bicing;
/* STATION 
	 * 
	 * Object class for station object.
	 * 
	 * */
public class Station {
	
	//Attributes
	private String id;
	private String type;
	private String latitude;
	private String longitude;
	private String streetName;
	private String streetNumber;
	private String altitude;
	private String slots;
	private String bikes;
	private String nearbyStations;
	private String status;

	// DEFAULT CONSTRUCTORS
	public Station() {

		this.id = "id";
		this.type = "type";
		this.latitude = "latitude";
		this.longitude = "longitude";
		this.streetName = "streetName";
		this.streetNumber = "streetNumber";
		this.altitude = "altitude";
		this.slots = "slots";
		this.bikes = "bikes";
		this.nearbyStations = "nearbyStations";
		this.status = "status";

	}

	public Station(String id, String type, String latitude, String longitude, String streetName, String streetNumber,
			String altitude, String slots, String bikes, String nearbyStations, String status) {
		this.id = id;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.altitude = altitude;
		this.slots = slots;
		this.bikes = bikes;
		this.nearbyStations = nearbyStations;
		this.status = status;
	}

	// DEFATUL GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getAltitude() {
		return altitude;
	}

	public String getSlots() {
		return slots;
	}

	public String getBikes() {
		return bikes;
	}

	public String getNearbyStations() {
		return nearbyStations;
	}

	public String getStatus() {
		return status;
	}

	// setters
	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public void setSlots(String slots) {
		this.slots = slots;
	}

	public void setBikes(String bikes) {
		this.bikes = bikes;
	}

	public void setNearbyStations(String nearbyStations) {
		this.nearbyStations = nearbyStations;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "Station with ID: " + id + " type: " + type + "\n";
	}

	public Boolean isOpen() {
		if (status.equals("OPN"))
			return true;
		else
			return false;
	}

	public int bikes() {
		return Integer.parseInt(bikes);
	}

	public int slots() {
		return Integer.parseInt(slots);
	}
}
