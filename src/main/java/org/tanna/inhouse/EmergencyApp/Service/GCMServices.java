package org.tanna.inhouse.EmergencyApp.Service;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Exception.ErrorMessage;
import org.tanna.inhouse.EmergencyApp.Exception.WebServiceException;
import org.tanna.inhouse.EmergencyApp.Model.GCM;

public class GCMServices {
	
	public Response registerForGCM(GCM gcm) throws WebServiceException{	
		
		String strQueryDelete = "delete from tbl_gcm_registration where contact_no ="+gcm.getContactNo();

		boolean success = Database.deleteQuery(strQueryDelete);
		
		String strQueryInsert = "insert into tbl_gcm_registration (registrationId, contact_no)"
				+ " values ('"+gcm.getRegistrationId()+"',"+gcm.getContactNo()+")";
		
		success = Database.insertQuery(strQueryInsert);
		
		if (!success) {
			throw new WebServiceException("Fail while Registering for GCM");
		}
		return Response.status(Status.OK).entity(new ErrorMessage("Successfull !!!")).type(MediaType.APPLICATION_JSON).build();
	}
	
	public Response unRegisterForGCM(GCM gcm) throws WebServiceException{	

		String strQueryDelete = "delete from tbl_gcm_registration where registrationId = '"+gcm.getRegistrationId()+"' and  contact_no ="+gcm.getContactNo();

		boolean success = Database.deleteQuery(strQueryDelete);

		if (!success) {
			throw new WebServiceException("Fail while UnRegistering for GCM");
		}
		return Response.status(Status.OK).entity(new ErrorMessage("Successfull !!!")).type(MediaType.APPLICATION_JSON).build();
	}

}
