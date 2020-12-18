package com.emse.lyonCityGuide.model;

public class Museum {
	private String museumLabel;
	private String museumDescription;
	private String villeIdLabel;
	private String ville;
	private String coord;
	public Museum(String museumLabel, String museumDescription, String villeIdLabel, String ville, String coord) {
		this.museumLabel = museumLabel;
		this.museumDescription = museumDescription;
		this.villeIdLabel = villeIdLabel;
		this.ville = ville;
		this.coord = coord;
	}
	public String getMuseumLabel() {
		return museumLabel;
	}
	public void setMuseumLabel(String museumLabel) {
		this.museumLabel = museumLabel;
	}
	public String getMuseumDescription() {
		return museumDescription;
	}
	public void setMuseumDescription(String museumDescription) {
		this.museumDescription = museumDescription;
	}
	public String getVilleIdLabel() {
		return villeIdLabel;
	}
	public void setVilleIdLabel(String villeIdLabel) {
		this.villeIdLabel = villeIdLabel;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getCoord() {
		return coord;
	}
	public void setCoord(String coord) {
		this.coord = coord;
	}
	
	




}
