
package org.tanna.inhouse.EmergencyApp.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.User;
import org.tanna.inhouse.EmergencyApp.Model.UserAccount;

import com.mysql.jdbc.Statement;

public class UserServices {
    
	public Response getLogin(UserAccount userAccount) throws SQLException,WebServiceException, ClassNotFoundException {
		User user = null;
		
		try {

			Connection conn = Database.getConnection();
			Statement stmt= null;
			ResultSet rs = null;

			stmt = (Statement) conn.createStatement();
			String strQuery = "select * from tbl_users where contact_no = '" + userAccount.getContactNo() + "' and password = '"+userAccount.getPassword()+"'";
			rs = stmt.executeQuery(strQuery);
//			ResultSet rs = Database.selectQuery((String)strQuery);
			while (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
			}
			
			conn.close();

			if(user == null) {
				//return Response.serverError().entity("No Such User found").build();
				throw new WebServiceException("No such user Found");
			}
		} catch (SQLException e) {
			 throw new WebServiceException("No such user Found");
		}
       return Response.ok().entity(user).build();
	}
	
	public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
		
        ArrayList<User> users = new ArrayList<User>();
        Connection conn = Database.getConnection();
		Statement stmt= null;
		ResultSet rs = null;

		stmt = (Statement) conn.createStatement();
        String strQuery = "select * from tbl_users";
        rs = stmt.executeQuery(strQuery);
        //ResultSet rs = Database.selectQuery((String)strQuery);
        while (rs.next()) {
            User user = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
            users.add(user);
        }
        
        conn.close();
        return users;
    }

    public Response getUser(long id) throws SQLException,WebServiceException, ClassNotFoundException {
        User user = null;
        
        try {
        	Connection conn = Database.getConnection();
			Statement stmt= null;
			ResultSet rs = null;

			stmt = (Statement) conn.createStatement();
			String strQuery = "select * from tbl_users where id = '" + id + "'";
			rs = stmt.executeQuery(strQuery);
        	
             //ResultSet rs = Database.selectQuery((String)strQuery);
             while (rs.next()) {
                 user = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
             }
             
             conn.close();
             
             if(user == null) {
            	 //return Response.serverError().entity("No Such User found").build();
            	 throw new WebServiceException("No such user Found");
             }
		} catch (SQLException e) {
			 throw new WebServiceException("No such user Found");
		}
        
        return Response.ok().entity(user).build();
    }

    public Response addUser(User user) throws SQLException, WebServiceException, ClassNotFoundException {
    	User userNew = null;
    	try {
    		Connection conn = Database.getConnection();
			Statement stmt= null;
			ResultSet rs = null;

			stmt = (Statement) conn.createStatement();
			String strQuery = "select * from tbl_users where contact_no = " + user.getContactNo();
			rs = stmt.executeQuery(strQuery);
//    		ResultSet rs = Database.selectQuery((String)strQuery);
    		User tempUser = null;
    		if (rs.next()) {
    			tempUser = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
    		}
    		if (tempUser == null) {
                
    			String str = "insert into tbl_users (contact_no,fname,lname,password) values (" + user.getContactNo() + ",'" + user.getFname() + "','" + user.getLname() + "','" + user.getPassword() + "')";
    			
    			stmt.executeUpdate(str);
    			
    			rs = stmt.executeQuery("select * from tbl_users where contact_no='" + user.getContactNo() + "'");
    			
    			while (rs.next()) {
    				userNew = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
				}
    			
    			conn.close();
//    			boolean isSucess = Database.insertQuery((String)str);
//    			if (isSucess && (rs = Database.selectQuery((String)("select * from tbl_users where contact_no='" + user.getContactNo() + "'"))).next()) {
//    				userNew = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
//    			}
    		}else{
    			conn.close();
    			throw new WebServiceException("User with same Phone number already exist");
    		}
    	} catch (SQLException e) {
    		throw new WebServiceException("Unable to create new user");
		}
    	return Response.ok().entity(userNew).build();
    }

    public User updateUser(User user) throws SQLException, ClassNotFoundException {
    	User userNew = null;
    	Connection conn = Database.getConnection();
		Statement stmt= null;
		ResultSet rs = null;

		stmt = (Statement) conn.createStatement();
		String str = "update tbl_users set contact_no = '" + user.getContactNo() + "', fname = '" + user.getFname() + "'," + " lname = '" + user.getLname() + "', password =  '" + user.getPassword() + "' where id='" + user.getId() + "'";
		stmt.executeUpdate(str);
		
		String strSelectQuery1 = "select * from tbl_users where contact_no='" + user.getId() + "'";
		rs = stmt.executeQuery(((String)strSelectQuery1));
		if (rs.next()) {
             userNew = new User(rs.getLong("id"), rs.getString("fname"), rs.getLong("contact_no"), rs.getString("lname"), rs.getString("password"));
        }
		conn.close();
		return userNew;
//      String str = "update tbl_users set contact_no = '" + user.getContactNo() + "', fname = '" + user.getFname() + "'," + " lname = '" + user.getLname() + "', password =  '" + user.getPassword() + "' where id='" + user.getId() + "'";
// 		boolean isSucess = Database.updateQuery((String)str);
//        if (isSucess) {
//            String strSelectQuery1 = "select * from tbl_users where contact_no='" + user.getId() + "'";
//            ResultSet rs1 = Database.selectQuery((String)strSelectQuery1);
//            if (rs1.next()) {
//                userNew = new User(rs1.getLong("id"), rs1.getString("fname"), rs1.getLong("contact_no"), rs1.getString("lname"), rs1.getString("password"));
//            }
//            return userNew;
//        }
//        return userNew;
    }

    public boolean removeUser(long id) throws SQLException, ClassNotFoundException {
    	Connection conn = Database.getConnection();
		Statement stmt= null;
		stmt = (Statement) conn.createStatement();
        String strDeleteQuery = "delete from tbl_users where id='" + id + "'";
        stmt.executeUpdate(strDeleteQuery);
        conn.close();
        return true;        
//        boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
//        if (isSuccess) {
//            return true;
//        }
//        return false;
    }
}