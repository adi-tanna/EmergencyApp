package org.tanna.inhouse.EmergencyApp.Model;

public class EmergencyContact {
	private long userContactNo;
	private String userName;
	private long emergencyContactNo;
	private String emergencyContactName;
	private String emergencyContactType;
	private boolean isAccepted;
	
	public EmergencyContact() {
		super();
	}
	public EmergencyContact(long userContactNo, String userName,
			long emergencyContactNo, String emergencyContactName,
			String emergencyContactType, boolean isAccepted) {
		super();
		this.userContactNo = userContactNo;
		this.userName = userName;
		this.emergencyContactNo = emergencyContactNo;
		this.emergencyContactName = emergencyContactName;
		this.emergencyContactType = emergencyContactType;
		this.isAccepted = isAccepted;
	}
	public long getUserContactNo() {
		return userContactNo;
	}
	public void setUserContactNo(long userContactNo) {
		this.userContactNo = userContactNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(long emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactType() {
		return emergencyContactType;
	}
	public void setEmergencyContactType(String emergencyContactType) {
		this.emergencyContactType = emergencyContactType;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

}