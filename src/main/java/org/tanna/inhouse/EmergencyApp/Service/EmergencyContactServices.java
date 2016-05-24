
package org.tanna.inhouse.EmergencyApp.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;

import com.mysql.jdbc.Statement;

public class EmergencyContactServices {
    public Response getEmergencyContacts(long id) throws SQLException, WebServiceException, ClassNotFoundException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        try {
        	Connection conn = Database.getConnection();
			Statement stmt= null;
			ResultSet rs = null;

			stmt = (Statement) conn.createStatement();
        	
        	String strSelect = "select * from tbl_emergency_numbers where contact_no = " + id;
        	
        	rs = stmt.executeQuery(strSelect);
        	
//        	ResultSet rs = Database.selectQuery((String)strSelect);
            while (rs.next()) {
            	           	
            	EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
               
            	listEmergencyContacts.add(tempEmerCont);
            }
            
            conn.close();
            
            if (listEmergencyContacts.size() == 0) {
            	throw new WebServiceException("User has no Emergency Contacts.");
			}
            
		} catch (SQLException e) {
			throw new WebServiceException("Some thin went wrong. Please tra again later.");
		}
        GenericEntity<List<EmergencyContact>> list = new GenericEntity<List<EmergencyContact>>(listEmergencyContacts){};
        return Response.ok().entity(list).build();    
        
    }

    public Response getAsEmergencyContact(long id) throws SQLException, WebServiceException, ClassNotFoundException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        
        try {
        	Connection conn = Database.getConnection();
			Statement stmt= null;
			ResultSet rs = null;

			stmt = (Statement) conn.createStatement();
        	
        	String strSelect = "select * from tbl_emergency_numbers where emergency_number = " + id;
        	
        	rs = stmt.executeQuery(strSelect);
        	
//        	ResultSet rs = Database.selectQuery((String)strSelect);
            
        	while (rs.next()) {           
                EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
                
            	listEmergencyContacts.add(tempEmerCont);
            }
            
        	 conn.close();
        	
            if (listEmergencyContacts.size() == 0) {
            	throw new WebServiceException("User is not add as Emergency Contact to any other users.");
			}
            
		} catch (SQLException e) {
			throw new WebServiceException("Some thin went wrong. Please tra again later.");
		}
        
        GenericEntity<List<EmergencyContact>> list = new GenericEntity<List<EmergencyContact>>(listEmergencyContacts){};
        return Response.ok().entity(list).build();
    }

    
    public boolean addEmergencyContact(List<EmergencyContact> list) throws SQLException, ClassNotFoundException, WebServiceException {
    	try {
    		Connection conn = Database.getConnection();
    		Statement stmt= null;
    		stmt = (Statement) conn.createStatement();

    		for (EmergencyContact emergencyContact : list) {
    			String strUserContactNo = String.valueOf(emergencyContact.getUserContactNo());
    			String strUserName = String.valueOf(emergencyContact.getUserName());
    			String strEmergencyContactNo = String.valueOf(emergencyContact.getEmergencyContactNo());
    			String strEmergencyName = emergencyContact.getEmergencyContactName();
    			String strEmergencyType = emergencyContact.getEmergencyContactType();
    			boolean isaccepted = emergencyContact.isAccepted();
    			String strInsertQuery = "insert into tbl_emergency_numbers (contact_no,contact_name,emergency_number,is_accepted,emergency_name,emergency_type) values(" + strUserContactNo + ",'"+strUserName+"'," + strEmergencyContactNo + "," + isaccepted +",'"+ strEmergencyName+"','"+ strEmergencyType +"')";
    			System.out.println(strInsertQuery);
    			stmt.executeUpdate(strInsertQuery);
    			continue;

    			//            if (Database.insertQuery((String)strInsertQuery)) continue;
    			//            return false;
    		}

    		conn.close();
    	} catch (SQLException e) {
 			throw new WebServiceException("Some thin went wrong. Please tra again later.");
 		}
        return true;
    }

    public boolean removeEmergencyContact(long number) throws WebServiceException, ClassNotFoundException {
    	 ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
         try {
        	Connection conn = Database.getConnection();
 			Statement stmt= null;
 			ResultSet rs = null;

 			stmt = (Statement) conn.createStatement();
        	 
         	String strSelect = "select * from tbl_emergency_numbers where contact_no = " + number;

         	rs = stmt.executeQuery(strSelect);
         	
//         	ResultSet rs = Database.selectQuery((String)strSelect);
             while (rs.next()) {
             
                 EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("contact_no"),rs.getString("contact_name"), rs.getLong("emergency_number"), rs.getString("emergency_name"), rs.getString("emergency_type"), rs.getBoolean("is_accepted"));
                 
             	listEmergencyContacts.add(tempEmerCont);
             }
             
             if (listEmergencyContacts.size() > 0) {
            	
            	 String strDeleteQuery = "Delete from tbl_emergency_numbers where contact_no =" + number;
            	 stmt.executeUpdate(strDeleteQuery);
            	 
            	 conn.close();
            	 
            	 return true;
//                 boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
//                 if (!isSuccess) {
//                     return false;
//                 }
 			}
 		} catch (SQLException e) {
 			throw new WebServiceException("Some thin went wrong. Please tra again later.");
 		}
         return true;
    }
}
