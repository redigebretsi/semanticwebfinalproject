package com.emse.bicycleSharingStations.model;

public class BTMStations {
	private String ID;
	private String name;
	private double lat;
	private double lon;
	private String numb;

	private String updatedtime;

	public BTMStations(String ID, String name, double lat, double lon, String numb, String updatedtime) {
		this.ID = ID;	
		this.name= name;
		this.lat=lat;
		this.lon= lon;
		this.numb = numb;
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

	public String getNumber() {
		return numb;
	}

	public void setNumber(String busNumber) {
		this.numb = numb;
	}

	public String getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(String updatedtime) {
		this.updatedtime = updatedtime;
	}
	

}
