package org.tanna.inhouse.EmergencyApp.Model;

public class EmergencyLocation {
	
	@Override
	public String toString() {
		return "{userName :" + userName + ", contactNo:"
				+ contactNo + ", latitude:" + latitude + ", longitude:"
				+ longitude + "}";
	}

	private String userName;
	
	private long contactNo;
	
	private String latitude;
	
	private String longitude;
	
	public EmergencyLocation() {
		super();
	}

	public EmergencyLocation(String userName, long contactNo, String latitude,
			String longitude) {
		super();
		this.userName = userName;
		this.contactNo = contactNo;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
}
