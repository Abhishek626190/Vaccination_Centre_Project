package com.vaccination;

public class Vaccination_Centre {
	private int centreId,cityId;
	private String centreName,location;
	
	public Vaccination_Centre(int centreId, int cityId, String centreName, String location) {
		super();
		this.centreId = centreId;
		this.cityId = cityId;
		this.centreName = centreName;
		this.location = location;
	}

	public int getCentreId() {
		return centreId;
	}

	public void setCentreId(int centreId) {
		this.centreId = centreId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCentreName() {
		return centreName;
	}

	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Vaccination_Centre [centreId=" + centreId + ", cityId=" + cityId + ", centreName=" + centreName
				+ ", location=" + location + "]";
	}

	 

}
