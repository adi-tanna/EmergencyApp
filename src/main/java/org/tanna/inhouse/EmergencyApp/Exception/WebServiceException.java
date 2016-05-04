package org.tanna.inhouse.EmergencyApp.Exception;

import java.io.Serializable;

public class WebServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 3387516993124229948L;
	
	public WebServiceException(){
		
	}
	public WebServiceException(String message){
		super(message);
	}
	
	public WebServiceException(String message,Exception e){
		super(message, e);
	}

}
