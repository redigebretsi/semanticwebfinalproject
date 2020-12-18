package com.emse.lyonCityGuide.model;

public class Dbicycle {
	private String iri;
	private String nava;
	

	public Dbicycle(String iri, String nava) {
		this.iri = iri;	
		this.nava= nava;
	
		}

	public String getIri() {
		return iri;
	}

	public void setIri(String iri) {
		this.iri = iri;
	}

	public String getNava() {
		return nava;
	}


	public void setNava(String nava) {
		this.nava = nava;
	}

}

