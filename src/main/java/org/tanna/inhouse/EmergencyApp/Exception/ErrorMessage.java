package org.tanna.inhouse.EmergencyApp.Exception;

public class ErrorMessage {
	private String message;

	public ErrorMessage(){
		
	}
	public ErrorMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

