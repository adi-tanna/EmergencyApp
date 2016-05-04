package org.tanna.inhouse.EmergencyApp.Resources;


import java.sql.SQLException;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;
import org.tanna.inhouse.EmergencyApp.Model.User;
import org.tanna.inhouse.EmergencyApp.Model.UserAccount;
import org.tanna.inhouse.EmergencyApp.Service.EmergencyContactServices;
import org.tanna.inhouse.EmergencyApp.Service.UserServices;

@Path(value="/users")
public class UserResource {
    UserServices objUserService = new UserServices();
    EmergencyContactServices objEmergencyContactService = new EmergencyContactServices();

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })  
    @Path("/login")
    public Response getLogin(@QueryParam("contactNo") String contactNo, @QueryParam("password") String password) throws SQLException, WebServiceException {
    	
    	UserAccount objUserAcc = new UserAccount(contactNo, password);
    	
        return this.objUserService.getLogin(objUserAcc);
    }
    
    @GET
    @Produces(value={"application/json"})
    public List<User> getUsers() throws SQLException {
        return this.objUserService.getAllUsers();
    }

    @POST
    @Produces(value={"application/json"})
    @Consumes(value={"application/json"})
    public Response addUser(User user) throws SQLException,WebServiceException {
        return this.objUserService.addUser(user);
    }

    @GET
    @Produces(value={"application/json"})
    @Path(value="/{userId}")
    public Response getUser(@PathParam(value="userId") long userId) throws SQLException,WebServiceException {
        return this.objUserService.getUser(userId);
    }

    @PUT
    @Produces(value={"application/json"})
    @Consumes(value={"application/json"})
    public User updateUser(User user) throws SQLException {
        return this.objUserService.updateUser(user);
    }

    @DELETE
    @Path(value="/{userId}")
    public boolean deleteUser(@PathParam(value="userId") long userId) throws SQLException {
        return this.objUserService.removeUser(userId);
    }

    @GET
    @Produces(value={"application/json"})
    @Path(value="/{userId}/EmergencyContact")
    public List<EmergencyContact> getEmergencyContacts(@PathParam(value="userId") long id) throws SQLException, WebServiceException {
        Response response =  this.objUserService.getUser(id);
    	
    	User tempUser = (User) response.readEntity(User.class);
        
        return this.objEmergencyContactService.getEmergencyContacts(tempUser.getContactNo());
    }

    @GET
    @Produces(value={"application/json"})
    @Path(value="/{userId}/asEmergencyContact")
    public List<EmergencyContact> getAsEmergencyContact(@PathParam(value="userId") long id) throws SQLException, WebServiceException {
       Response response = this.objUserService.getUser(id);
        User tempUser = response.readEntity(User.class);
        return this.objEmergencyContactService.getAsEmergencyContact(tempUser.getContactNo());
    }

    @POST
    @Path(value="/{userId}/EmergencyContact")
    @Consumes(value={"application/json"})
    public String addEmergencyContact(@PathParam(value="userId") long id, List<EmergencyContact> list) throws SQLException, WebServiceException {
    	 Response response = this.objUserService.getUser(id);
         User tempUser = response.readEntity(User.class);
        this.objEmergencyContactService.removeEmergencyContact(tempUser.getContactNo());
        if (!list.isEmpty()) {
            boolean isSuccessForAdd = this.objEmergencyContactService.addEmergencyContact(list);
            if (isSuccessForAdd) {
                return "Successful !!!";
            }
            return "Failure while adding !!!";
        }
        return "Body Content not found !";
    }
}