package edu.ucam;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


// http://localhost:8080/contexto/rest/hola/miguelangel/guillen?min=1234&token=FSDFASD%·FGSDFG
@Path("/hola/{username}/{surname}")
public class Hello {
	
	
	@DefaultValue("true") @QueryParam("min") boolean hasMin;
	@DefaultValue("XXXXX") @QueryParam("token") String userToken;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello(@PathParam("username") String userName,
									@PathParam("surname") String surname) {
		
		return "Hola " + userName + " " + surname + ", min:"+ this.hasMin + ", usertoken:"+ this.userToken;
	}
	
	@POST
	public void hola(){
		
		
	}
	
	@DELETE
	public void adios(){
		
	}

} 
