package org.tanna.inhouse.EmergencyApp.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Content implements Serializable {
	
//	public List<String> getRegistration_ids() {
//		return registration_ids;
//	}
//
//	public void setRegistration_ids(List<String> registration_ids) {
//		this.registration_ids = registration_ids;
//	}

	public Map<String, Object> getData() {
		return data;
	}

	public String getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(String registration_ids) {
		this.registration_ids = registration_ids;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Content [registration_ids=" + registration_ids + ", data="
				+ data + "]";
	}

	private static final long serialVersionUID = 3387516993124229948L;
//	private List<String> registration_ids;
	private String registration_ids;
    private Map<String,Object> data;

//    public void addRegId(String regId){
//        if(registration_ids == null)
//            registration_ids = new LinkedList<String>();
//        registration_ids.add(regId);
//    }
    
    public void addRegId(String regId){
        setRegistration_ids(regId);
    }

    public void createData(String title, String message, EmergencyLocation emergencyLocation){
        if(data == null)
            data = new HashMap<String,Object>();
       
        Map<String, Object> mapNotificaiton = new HashMap<String, Object>();
        mapNotificaiton.put("body",emergencyLocation.getUserName()+" is in Emergency. Location is shared in app.");
        mapNotificaiton.put("sound", "default");
        mapNotificaiton.put("priority", "high");
        
        data.put("to", registration_ids);
        data.put("content_available", true);
        data.put("notification", mapNotificaiton);
        data.put("data", emergencyLocation);
        
        /*
   	 https://gcm-http.googleapis.com/gcm/send
		Content-Type:application/json
		Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA
		{
  "to" : "eHwXKnjD22Q:APA91bFodVtNxwm5AZwGTHxe5eN6XEpnAws_wlSdRGktOLadSO-JHZK1mgFWnAXVNdNkq4S9y7kSE4ng_yf8xhdyJwcLXhpOXKnwdfzUyhj7WDeoPhTcDscUYp9Y0zc9SNy8FBu0GqPv",
  "content_available":true,
  "notification" : {
    "body" : "great match!",
    "title" : "Portugal vs. Denmark",
    "sound": "default",
    "priority": "high"
    }
}
		*/	
      
    }
}