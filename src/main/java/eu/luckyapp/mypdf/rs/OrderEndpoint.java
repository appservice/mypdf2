package eu.luckyapp.mypdf.rs;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.model.Order;

@Path("/orders")
@Produces(value = { MediaType.APPLICATION_JSON })
public class OrderEndpoint {

	@Inject
	private Logger Log;

	@Inject
	OrderDAO orderDAO;

	@GET
	public Response getAll(@QueryParam("number") String orderNumber) {
		Log.info(orderNumber);
		List<Order> orders;
		if (orderNumber != null) {
			orders = orderDAO.findByNumber(orderNumber);
		} else {
			orders = orderDAO.findAll();
		}
		
		return Response.accepted(orders).build();
	}

	@GET
	@Path("/{orderNumber}")
	public Response getOne(@PathParam("orderNumber") String orderNumber) {
		Order order = orderDAO.findByNumberStrict(orderNumber);
		return Response.accepted(order).build();
	}


}
