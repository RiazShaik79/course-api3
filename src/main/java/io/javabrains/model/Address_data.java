package io.javabrains.model;

public class Address_data {
	
	private Address address;
	private Coords coords;
	private String query;
    private String locationId;
    private boolean isChecked;
    
    public Address_data() {} 
    
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Coords getCords() {
		return coords;
	}

	public void setCords(Coords coords) {
		this.coords = coords;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	public Address_data(Address address, Coords coords, String query, String locationId, boolean isChecked) {
		super();
		this.address = address;
		this.coords = coords;
		this.query = query;
		this.locationId = locationId;
		this.isChecked = isChecked;
	}

}
