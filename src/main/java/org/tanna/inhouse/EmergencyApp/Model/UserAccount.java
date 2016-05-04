package org.tanna.inhouse.EmergencyApp.Model;

public class UserAccount {
	private String contactNo;
	private String password;
		
	public UserAccount() {
		super();
	}
	public UserAccount(String contactNo, String password) {
		super();
		this.contactNo = contactNo;
		this.password = password;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
