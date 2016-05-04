package org.tanna.inhouse.EmergencyApp.Exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseFilter;

@Provider
public class UserWebServiceExceptionHandler implements ExceptionMapper<WebServiceException>{

	@Override
	public Response toResponse(WebServiceException exception) {
		System.out.println(""+exception.getMessage());
		System.out.println("Error Message print :"+new ErrorMessage(exception.getMessage()).getError());
        return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
	}

}
