package io.javabrains.model;

public class Coords {
	
	private double lat;
	private double lon;

	public Coords(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	
	//public Coords() {}
	
	
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

