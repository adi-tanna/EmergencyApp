package org.tanna.inhouse.EmergencyApp.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.ErrorMessage;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.Content;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyLocation;
import org.tanna.inhouse.EmergencyApp.Model.GCM;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmergencyLocationServices {
	
public Response declareEmergency(EmergencyLocation emergencyLocation) throws SQLException, WebServiceException {
		
		/*// Uncomment this code to add location into database. 
		boolean isSuccess = addEmergencyLocationIntoDb(emergencyLocation);
		
		if (!isSuccess) {
			System.out.println("Fail to add Emergency Location into Database");
		}*/
		
		  String apiKey = "AIzaSyBeJhqG9EaWHmx1hXm6b70a3L-rVWlrtkc";
	      
		  ArrayList<String> deviceIds = getEmergencyDeviceIdForUser(emergencyLocation.getContactNo());
		  
		  if (deviceIds.size() == 0) {
			  return Response.status(Status.OK).entity(new ErrorMessage("It seems User Doesn't have any Emergency Contact !!")).type(MediaType.APPLICATION_JSON).build();
		  }
		  
//		  for (String string : deviceIds) {
//			  Content content = createContentWithDeviceIdsAndData(string, emergencyLocation);
//
//			  post(apiKey, content);
//			  
//		  }
//		  
//		  return Response.status(Status.OK).entity(new ErrorMessage("Successfull !!!")).type(MediaType.APPLICATION_JSON).build(); 
		  Content content = createContentWithDeviceIdsAndData(deviceIds, emergencyLocation);

		  return post(apiKey, content);
	}
	
	public boolean addEmergencyLocationIntoDb(EmergencyLocation emergencyLocation) {
		
		String strQueryInsert = "Insert into tbl_emergency_location (contact_no, userName, latitude, longitude)"
				+ "values ("+emergencyLocation.getContactNo()+",'"+emergencyLocation.getUserName()
				+"','"+emergencyLocation.getLatitude()+"','"+emergencyLocation.getLongitude()+"')";
		
		boolean isSuccess = Database.insertQuery(strQueryInsert);
		
		if(!isSuccess){
			return false;
		}
		
		return true;
	}
	
	public ArrayList<String> getEmergencyDeviceIdForUser(long contactNo) throws SQLException {
		
		ArrayList<String> emergencyDeviceId = new ArrayList<String>();
		
		ArrayList<Long> emergencyUsers = (ArrayList<Long>) getEmergencyUsersForUser(contactNo);
		
		if (emergencyUsers.size() >0) {
			for (Long emergencyUserContactNo : emergencyUsers) {
				try {
					String strSelect = "select * from tbl_gcm_registration where contact_no = " + emergencyUserContactNo;
		            ResultSet rs = Database.selectQuery((String)strSelect);
		            while (rs.next()) {
		            	           	
		            	GCM GCMDetails = new GCM(rs.getString("registrationId"), rs.getLong("contact_no")); 
		            	
		            	emergencyDeviceId.add(GCMDetails.getRegistrationId());
		            }
				} catch (Exception e) {
					
				}
			}
		}
		
		return emergencyDeviceId;
	}
	
	public List<Long> getEmergencyUsersForUser(long contactNo) throws SQLException {
		
		List<Long> emergencyUsers = new ArrayList<Long>();

		try {
			String strSelect = "select * from tbl_emergency_numbers where contact_no = " + contactNo;
			ResultSet rs = Database.selectQuery((String)strSelect);
			while (rs.next()) {

				EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));

				emergencyUsers.add(tempEmerCont.getEmergencyContactNo());
			}
		} catch (Exception e) {
			
		}

		return emergencyUsers;
	}	
	
//	public static Content createContentWithDeviceIdsAndData(String deviceId,EmergencyLocation emergencyLocation){
//
//        Content c = new Content();
//        
//        c.addRegId(deviceId);
//		
//        c.createData("Test Title", "Test Message",emergencyLocation);
//
//        return c;
//    }
	public static Content createContentWithDeviceIdsAndData(ArrayList<String> deviceIds,EmergencyLocation emergencyLocation){

        Content c = new Content();
        
        for (String deviceId : deviceIds) {
        	c.addRegId(deviceId);
		}
        
        c.createData("Test Title", "Test Message",emergencyLocation);

        return c;
    }
	
	
	public static Response post(String apiKey, Content content) throws WebServiceException{
		try{
			/*
	        	 https://gcm-http.googleapis.com/gcm/send
				Content-Type:application/json
				Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aAAIzaSyBeJhqG9EaWHmx1hXm6b70a3L-rVWlrtkc
				{
	  					"to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
	  					"notification" : {
	    				"body" : "great match!",
	    				"title" : "Portugal vs. Denmark"
	    				}
				}
			 */	
			 	// 1. URL
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");

				// 2. Open connection	
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

				// 3. Specify POST method
			 con.setRequestMethod("POST");
		        
			 	// 4. Set the headers
			 con.setRequestProperty("Content-Type", "application/json");
			 con.setRequestProperty("Authorization", "key="+apiKey);

			 con.setDoOutput(true);

			 	// 5. Add JSON data into POST request body 
			 
			 	//`5.1 Use Jackson object mapper to convert Contnet object into JSON
			 ObjectMapper mapper = new ObjectMapper();
			
			 	// 5.2 Get connection output stream
			 OutputStream os = con.getOutputStream();

			 	// 5.3 Copy Content "JSON" into 
			 String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(content.getData());
			 System.out.println(jsonInString);
			 os.write(jsonInString.getBytes());
			
			// 5.4 Send the request
			  os.flush();
		       

			// 5.5 close
			  os.close();

			// 6. Get the response
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 7. Print result
			System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
			
			throw new WebServiceException("Fail sending Notificaiton due to MalformedURL");
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new WebServiceException("Fail sending Notificaiton due to IOException");
		}
		
		return Response.status(Status.OK).entity(new ErrorMessage("Successfull !!!")).type(MediaType.APPLICATION_JSON).build();
	}
}
