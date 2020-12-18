package com.emse.lyonCityGuide.model;

public class Hospital {
	private String adresse;
	private String tel_number;
	private String catagorie;
	private double lat;
	private double lon;

	public Hospital(String adresse, String tel_number, String catagorie, double lat, double lon) {
		this.adresse = adresse;
		this.tel_number = tel_number;
		this.catagorie = catagorie;
		this.lat = lat;
		this.lon = lon;

	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTel_number() {
		return tel_number;
	}

	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}

	public String getCatagorie() {
		return catagorie;
	}

	public void setCatagorie(String catagorie) {
		this.catagorie = catagorie;
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


}
