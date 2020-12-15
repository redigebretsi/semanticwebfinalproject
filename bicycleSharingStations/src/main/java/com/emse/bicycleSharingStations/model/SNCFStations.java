package com.emse.bicycleSharingStations.model;

public class SNCFStations {

	private String ID;
	private String name;
	private double lat;
	private double lon;
	private String arrival;
	private String depart;
	private boolean escalator;
	private boolean ascenseur;
	

	public SNCFStations(String ID, String name, double lat, double lon, String arrival, String depart, boolean escalator, boolean ascenseur) {
		this.ID = ID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.arrival=arrival;
		this.depart=depart;
		this.escalator=escalator;
		this.ascenseur=ascenseur;
		}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLon() {
		return this.lon;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public boolean isEscalator() {
		return escalator;
	}

	public void setEscalator(boolean escalator) {
		this.escalator = escalator;
	}

	public boolean isAscenseur() {
		return ascenseur;
	}

	public void setAscenseur(boolean ascenseur) {
		this.ascenseur = ascenseur;
	}

}
