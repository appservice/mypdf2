package eu.luckyapp.mypdf.rs;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.luckyapp.mypdf.model.Order;

@Path("/orders")
@Produces(value={MediaType.APPLICATION_JSON})
public class OrderEndpoint {
	
	@Inject
	EntityManager em;
	
	@GET
	public Response getAll(){
		List<Order> orders=em.createNamedQuery(Order.FIND_ALL).getResultList();
		return Response.accepted(orders).build();
	}
	

}
