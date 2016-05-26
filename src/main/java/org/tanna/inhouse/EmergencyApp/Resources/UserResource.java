package org.tanna.inhouse.EmergencyApp.Resources;


import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.tanna.inhouse.EmergencyApp.Exception.ErrorMessage;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyLocation;
import org.tanna.inhouse.EmergencyApp.Model.GCM;
import org.tanna.inhouse.EmergencyApp.Model.User;
import org.tanna.inhouse.EmergencyApp.Model.UserAccount;
import org.tanna.inhouse.EmergencyApp.Service.EmergencyContactServices;
import org.tanna.inhouse.EmergencyApp.Service.EmergencyLocationServices;
import org.tanna.inhouse.EmergencyApp.Service.GCMServices;
import org.tanna.inhouse.EmergencyApp.Service.UserServices;

@Path(value="/users")
public class UserResource {
    UserServices objUserService = new UserServices();
    EmergencyContactServices objEmergencyContactService = new EmergencyContactServices();
    GCMServices objGCMService = new GCMServices();
    EmergencyLocationServices objEmergencyLocationService = new EmergencyLocationServices();
    
    
    @GET
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON })  
    @Path("/login")
    public Response getLogin(@QueryParam("contactNo") String contactNo, @QueryParam("password") String password) throws SQLException, WebServiceException, ClassNotFoundException {
    	
    	UserAccount objUserAcc = new UserAccount(contactNo, password);
    	
        return this.objUserService.getLogin(objUserAcc);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    public List<User> getUsers() throws SQLException, ClassNotFoundException {
        return this.objUserService.getAllUsers();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON })
    @Consumes({MediaType.APPLICATION_JSON })
    public Response addUser(User user) throws SQLException,WebServiceException, ClassNotFoundException {
        return this.objUserService.addUser(user);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path(value="/{userId}")
    public Response getUser(@PathParam(value="userId") long userId) throws SQLException,WebServiceException, ClassNotFoundException {
        return this.objUserService.getUser(userId);
    }

//    @PUT
//    @Produces(value={"application/json"})
//    @Consumes(value={"application/json"})
//    public User updateUser(User user) throws SQLException {
//        return this.objUserService.updateUser(user);
//    }
//
//    @DELETE
//    @Path(value="/{userId}")
//    public boolean deleteUser(@PathParam(value="userId") long userId) throws SQLException {
//        return this.objUserService.removeUser(userId);
//    }

    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path(value="/{userId}/EmergencyContact")
    public Response getEmergencyContacts(@PathParam(value="userId") long id) throws SQLException, WebServiceException, ClassNotFoundException {
        Response response =  this.objUserService.getUser(id);
    	
    	User tempUser = (User)response.getEntity(); 
        
        return this.objEmergencyContactService.getEmergencyContacts(tempUser.getContactNo());
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path(value="/{userId}/asEmergencyContact")
    public Response getAsEmergencyContact(@PathParam(value="userId") long id) throws SQLException, WebServiceException, ClassNotFoundException {
       Response response = this.objUserService.getUser(id);
       
       User tempUser = (User)response.getEntity(); 
       
       return this.objEmergencyContactService.getAsEmergencyContact(tempUser.getContactNo());
    }

    @POST
    @Path(value="/{userId}/EmergencyContact")
    @Consumes({MediaType.APPLICATION_JSON })
    public Response addEmergencyContact(@PathParam(value="userId") long id, List<EmergencyContact> list) throws SQLException, WebServiceException, ClassNotFoundException {
    	Response response = this.objUserService.getUser(id);
    	
    	User tempUser = (User)response.getEntity(); 
    	
    	boolean isSuccess = this.objEmergencyContactService.removeEmergencyContact(tempUser.getContactNo());
        
    	System.out.println(isSuccess);
    	
    	if(isSuccess){
    		if (list.size() > 0/*!list.isEmpty()*/) {
                boolean isSuccessForAdd = this.objEmergencyContactService.addEmergencyContact(list);
                if (!isSuccessForAdd) {
                	throw new WebServiceException("Failure while adding emergency contacts !!!. Please try again later.");
                }
            }else{
            	
            }
    	}else{
    		throw new WebServiceException("Something went wrong. Please try again later.");
    	}
    	
    	return Response.status(Status.OK).entity(new ErrorMessage("Successfull !!!")).type(MediaType.APPLICATION_JSON).build(); 
         
    }
    
    @POST
    @Path(value="/GcmRegister")
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON })
    public Response registerForGCM (GCM gcm) throws WebServiceException, ClassNotFoundException, SQLException{    	
    	return objGCMService.registerForGCM(gcm);
    }
    
    @POST
    @Path(value="/GcmUnRegister")
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON })
    public Response unRegisterForGCM (GCM gcm) throws WebServiceException, ClassNotFoundException, SQLException{    	
    	return objGCMService.unRegisterForGCM(gcm);
    }
    
    
    @POST
    @Path(value="/DeclareEmergency")
    @Consumes({MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_JSON })
    public Response declareEmergency(EmergencyLocation emergencyLocation) throws SQLException, WebServiceException, ClassNotFoundException{
    	
    	return objEmergencyLocationService.declareEmergency(emergencyLocation);    	
    }
    
}