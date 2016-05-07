
package org.tanna.inhouse.EmergencyApp.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;

public class EmergencyContactServices {
    public Response getEmergencyContacts(long id) throws SQLException, WebServiceException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        try {
        	String strSelect = "select * from tbl_emergency_numbers where contact_no = " + id;
            ResultSet rs = Database.selectQuery((String)strSelect);
            while (rs.next()) {
            	           	
            	EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
               
            	listEmergencyContacts.add(tempEmerCont);
            }
            
            if (listEmergencyContacts.size() == 0) {
            	throw new WebServiceException("User has no Emergency Contacts.");
			}
            
		} catch (SQLException e) {
			throw new WebServiceException("Some thin went wrong. Please tra again later.");
		}
        GenericEntity<List<EmergencyContact>> list = new GenericEntity<List<EmergencyContact>>(listEmergencyContacts){};
        return Response.ok().entity(list).build();    
        
    }

    public Response getAsEmergencyContact(long id) throws SQLException, WebServiceException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        
        try {
        	String strSelect = "select * from tbl_emergency_numbers where emergency_number = " + id;
        	 
        	ResultSet rs = Database.selectQuery((String)strSelect);
            
        	while (rs.next()) {           
                EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
                
            	listEmergencyContacts.add(tempEmerCont);
            }
            
            if (listEmergencyContacts.size() == 0) {
            	throw new WebServiceException("User is not add as Emergency Contact to any other users.");
			}
            
		} catch (SQLException e) {
			throw new WebServiceException("Some thin went wrong. Please tra again later.");
		}
        
        GenericEntity<List<EmergencyContact>> list = new GenericEntity<List<EmergencyContact>>(listEmergencyContacts){};
        return Response.ok().entity(list).build();
    }

    
    public boolean addEmergencyContact(List<EmergencyContact> list) {
        for (EmergencyContact emergencyContact : list) {
            String strUserContactNo = String.valueOf(emergencyContact.getUserContactNo());
            String strUserName = String.valueOf(emergencyContact.getUserName());
            String strEmergencyContactNo = String.valueOf(emergencyContact.getEmergencyContactNo());
            String strEmergencyName = emergencyContact.getEmergencyContactName();
            String strEmergencyType = emergencyContact.getEmergencyContactType();
            boolean isaccepted = emergencyContact.isAccepted();
            String strInsertQuery = "insert into tbl_emergency_numbers (contact_no,contact_name,emergency_number,is_accepted,emergency_name,emergency_type) values(" + strUserContactNo + ",'"+strUserName+"'," + strEmergencyContactNo + "," + isaccepted +",'"+ strEmergencyName+"','"+ strEmergencyType +"')";
            System.out.println(strInsertQuery);
            if (Database.insertQuery((String)strInsertQuery)) continue;
            return false;
        }
        return true;
    }

    public boolean removeEmergencyContact(long number) throws WebServiceException {
    	 ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
         try {
         	String strSelect = "select * from tbl_emergency_numbers where contact_no = " + number;
             ResultSet rs = Database.selectQuery((String)strSelect);
             while (rs.next()) {
             
                 EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
                 
             	listEmergencyContacts.add(tempEmerCont);
             }
             
             if (listEmergencyContacts.size() > 0) {
            	 String strDeleteQuery = "Delete from tbl_emergency_numbers where contact_no =" + number;
                 boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
                 if (!isSuccess) {
                     return false;
                 }
 			}
 		} catch (SQLException e) {
 			throw new WebServiceException("Some thin went wrong. Please tra again later.");
 		}
         return true;
    }
}
