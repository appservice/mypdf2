package eu.luckyapp.mypdf.rs;

import eu.luckyapp.mypdf.model.User;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/users")
//@Stateless
public class UsersEndpoint {

	@Inject
	Logger Log;
	
	
/*	@Resource
	private SessionContext context2;*/
	
	@Path("/logged")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User loggedUser(@Context SecurityContext context){
		User user=new User();
		
		user.setName(context.getUserPrincipal().getName());
	
		
		return user;
	}
	
	@Path("/logout")
	@GET	
	public Response logoutUser(@Context  HttpServletRequest request){
		String auth=request.getHeader("Authorization");
		Log.info(auth);
		try {
			request.logout();
		} catch (ServletException e) {
			Log.error("Cant Logout :"+e.getMessage());
			e.printStackTrace();
		}
		//context.getMessageContext().
		return Response.ok().build();
	}

}
