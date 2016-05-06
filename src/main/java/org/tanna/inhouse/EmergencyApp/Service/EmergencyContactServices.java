
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
                EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
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
        
//        String strSelect = "select * from tbl_emergency_numbers where contact_no = " + id;
//        ResultSet rs = Database.selectQuery((String)strSelect);
//        while (rs.next()) {
//            EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
//            listEmergencyContacts.add(tempEmerCont);
//        }
//        return listEmergencyContacts;
        
        
        /*
         User user = null;
		
		try {
			 String strQuery = "select * from tbl_users where contact_no = '" + userAccount.getContactNo() + "' and password = '"+userAccount.getPassword()+"'";
		        ResultSet rs = Database.selectQuery((String)strQuery);
		        while (rs.next()) {
		            user = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
		        }
		        
		        if(user == null) {
		           //return Response.serverError().entity("No Such User found").build();
		           throw new WebServiceException("No such user Found");
		        }
		} catch (SQLException e) {
			 throw new WebServiceException("No such user Found");
		}
       return Response.ok().entity(user).build();
         */
    }

    public Response getAsEmergencyContact(long id) throws SQLException, WebServiceException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        
        try {
        	String strSelect = "select * from tbl_emergency_numbers where emergency_number = " + id;
        	 
        	ResultSet rs = Database.selectQuery((String)strSelect);
            
        	while (rs.next()) {
            	EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
                
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
            String str1 = String.valueOf(emergencyContact.getUserContactNo());
            String str2 = String.valueOf(emergencyContact.getEmergencyContactNo());
            boolean isaccepted = emergencyContact.isAccepted();
            String strInsertQuery = "insert into tbl_emergency_numbers (contact_no,emergency_number,is_accepted) values(" + str1 + "," + str2 + "," + isaccepted + ")";
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
                 EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
                 listEmergencyContacts.add(tempEmerCont);
             }
             
             if (listEmergencyContacts.size() > 0) {
            	 String strDeleteQuery = "Delete from tbl_emergency_numbers where contact_no =" + number;
                 boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
                 if (isSuccess) {
                     return true;
                 }
                 return false;
 			}
 		} catch (SQLException e) {
 			throw new WebServiceException("Some thin went wrong. Please tra again later.");
 		}
         return true;
    }
}
