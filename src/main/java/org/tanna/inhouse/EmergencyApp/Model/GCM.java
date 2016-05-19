package org.tanna.inhouse.EmergencyApp.Model;

public class GCM {
	
	private String registrationId;
	
	private long contactNo;

	public GCM() {
		super();
	}

	public GCM(String registrationId, long contactNo) {
		super();
		this.registrationId = registrationId;
		this.contactNo = contactNo;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

}
