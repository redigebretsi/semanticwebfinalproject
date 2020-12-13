package complete;

public class BTMStations {
	private String ID;
	private String name;
	private double lat;
	private double lon;
	private String busNumber;
	private String tramNumber;
	private String metroNumber;
	private String updatedtime;

	public BTMStations(String ID, String name, double lat, double lon, String busNumber, String tramNumber, String metroNumber, String updatedtime) {
		this.ID = ID;	
		this.name= name;
		this.lat=lat;
		this.lon= lon;
		this.busNumber = busNumber;
		this.tramNumber = tramNumber;
		this.metroNumber = metroNumber;
		this.updatedtime = updatedtime;
		}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getTramNumber() {
		return tramNumber;
	}

	public void setTramNumber(String tramNumber) {
		this.tramNumber = tramNumber;
	}

	public String getMetroNumber() {
		return metroNumber;
	}

	public void setMetroNumber(String metroNumber) {
		this.metroNumber = metroNumber;
	}

	public String getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(String updatedtime) {
		this.updatedtime = updatedtime;
	}
	
	
	
}
