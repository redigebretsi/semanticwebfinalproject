package com.emse.lyonCityGuide.model;

public class DynamicData {
	private String stationName;
	private String availableBikes;
	private String temperature;
	private String humidity;
	private String windSpeed;
	private String pressure;
	
	public DynamicData(String stationName, String availableBikes, String temperature, String humidity, String windSpeed,
			String pressure) {

		this.stationName = stationName;
		this.availableBikes = availableBikes;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
		this.pressure = pressure;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAvailableBikes() {
		return availableBikes;
	}

	public void setAvailableBikes(String availableBikes) {
		this.availableBikes = availableBikes;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

}
