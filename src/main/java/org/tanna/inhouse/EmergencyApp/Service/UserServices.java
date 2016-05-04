
package org.tanna.inhouse.EmergencyApp.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.User;
import org.tanna.inhouse.EmergencyApp.Model.UserAccount;

public class UserServices {
    
	public Response getLogin(UserAccount userAccount) throws SQLException,WebServiceException {
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
	}
	
	public List<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String strQuery = "select * from tbl_users";
        ResultSet rs = Database.selectQuery((String)strQuery);
        while (rs.next()) {
            User user = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
            users.add(user);
        }
        return users;
    }

    public Response getUser(long id) throws SQLException,WebServiceException {
        User user = null;
        
        try {
        	 String strQuery = "select * from tbl_users where id = '" + id + "'";
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
    }

    public Response addUser(User user) throws SQLException, WebServiceException {
    	User userNew = null;
    	try {
    		String strQuery = "select * from tbl_users where contact_no = " + user.getContactNo();
    		ResultSet rs = Database.selectQuery((String)strQuery);
    		User tempUser = null;
    		if (rs.next()) {
    			tempUser = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
    		}
    		if (tempUser == null) {
    			String str = "insert into tbl_users (contact_no,fname,lname,password) values (" + user.getContactNo() + ",'" + user.getFname() + "','" + user.getLname() + "','" + user.getPassword() + "')";
    			boolean isSucess = Database.insertQuery((String)str);
    			if (isSucess && (rs = Database.selectQuery((String)("select * from tbl_users where contact_no='" + user.getContactNo() + "'"))).next()) {
    				userNew = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
    			}
    		}else{
    			throw new WebServiceException("User with same Phone number already exist");
    		}
    	} catch (SQLException e) {
    		throw new WebServiceException("Unable to create new user");
		}
    	return Response.ok().entity(userNew).build();
    }

    public User updateUser(User user) throws SQLException {
        User userNew = null;
        String str = "update tbl_users set contact_no = '" + user.getContactNo() + "', fname = '" + user.getFname() + "'," + " lname = '" + user.getLname() + "', password =  '" + user.getPassword() + "' where id='" + user.getId() + "'";
        boolean isSucess = Database.updateQuery((String)str);
        userNew = null;
        if (isSucess) {
            String strSelectQuery1 = "select * from tbl_users where contact_no='" + user.getId() + "'";
            ResultSet rs1 = Database.selectQuery((String)strSelectQuery1);
            if (rs1.next()) {
                userNew = new User(rs1.getLong("id"), rs1.getString("fname"), rs1.getLong("contact_no"), rs1.getString("lname"), rs1.getString("password"));
            }
            return userNew;
        }
        return userNew;
    }

    public boolean removeUser(long id) throws SQLException {
        String strDeleteQuery = "delete from tbl_users where id='" + id + "'";
        boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
        if (isSuccess) {
            return true;
        }
        return false;
    }
}