package eu.luckyapp.mypdf.rs;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.model.Order;

@Path("/orders")
@Produces(value = { MediaType.APPLICATION_JSON })
// @Encoded
// @Stateless
public class OrderEndpoint {

	@Inject
	private Logger Log;

	@Inject
	OrderDAO orderDAO;

	@Inject
	ItemsEndpoint itemsEndpoint;

	@GET
	public Response getAll(@QueryParam("number") String orderNumber, 
			@QueryParam("supplier") String supplier,
			@QueryParam("purchaser") String purchaser, 
			@QueryParam("factory") String factory,
			@QueryParam("suppliesGroup") String suppliesGroup, 
			@QueryParam("orderDateFrom") long orderDateFromLong,
			@QueryParam("orderDateTo") long orderDateToLong, 
			@QueryParam("orderReference") String orderReference,
			@QueryParam("itemName") String itemName, 
			@QueryParam("itemIndex") String itemIndex,
			@QueryParam("itemDescription") String itemDescription,
			@QueryParam("receivingPerson") String receivingPerson,
	
			@QueryParam("warehouseReleaseDateFrom") long warehouseReleaseDateFromLong,
			@QueryParam("warehouseReleaseDateTo") long warehouseReleaseDateToLong,
			@QueryParam("warehouseReleasePerson") String warehouseReleasePerson,
			@QueryParam("mpk") String mpk,
			@QueryParam("overdueTime") Integer overdueTime,
			
			
			@QueryParam("startPosition") int startPosition,
			@QueryParam("maxResult") int maxResult) {

		Log.info("orderNumber: " + orderNumber);
		Log.info("supplier: " + supplier);
		Log.info("purchaser: " + purchaser);
		Log.info("factory: " + factory);
		Log.info("suppliesGroup: " + suppliesGroup);
		Log.info("itemName: " + itemName);

		Log.info("orderReference: " + orderReference);
		Log.info("warehouseReleasePerson: " + warehouseReleasePerson);
		

		Date orderDateFrom = null;
		if (orderDateFromLong > 0) {
			orderDateFrom = new Date(orderDateFromLong);
		}
		Log.info("orderDateFrom: " + orderDateFrom);

		Date orderDateTo = null;
		if (orderDateToLong > 0) {
			orderDateTo = new Date(orderDateToLong);
		}
		Log.info("orderDateTo: " + orderDateTo);

		Date warehouseReleaseDateFrom = null;
		if (warehouseReleaseDateFromLong > 0) {
			warehouseReleaseDateFrom = new Date(warehouseReleaseDateFromLong);
		}
		Log.info("warehouseReleaseDateFrom: " + warehouseReleaseDateFrom);


		Date warehouseReleaseDateTo = null;
		if (warehouseReleaseDateToLong > 0) {
			warehouseReleaseDateTo = new Date(warehouseReleaseDateToLong);
		}
		Log.info("warehouseReleaseDateTo: " + warehouseReleaseDateTo);

		

		List<Order> orders = orderDAO.findByNumber(orderNumber, supplier, purchaser, factory, suppliesGroup,
				orderDateFrom, orderDateTo, orderReference, itemName, itemIndex, itemDescription,warehouseReleaseDateFrom,warehouseReleaseDateTo,warehouseReleasePerson,mpk,overdueTime,
				startPosition,	maxResult);
		// Log.info(orders.get(1).toString());
		return Response.accepted(orders).build();
	}

	@GET
	@Path("/{orderId}")
	public Response getOne(@PathParam("orderId") Long id) {
		Order order = orderDAO.find(id);// orderDAO.findByNumberStrict(orderNumber);

		return Response.accepted(order).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{orderId}")
	public Response saveOrder(@Context UriInfo uriInfo, Order order, @PathParam("orderId") Long orderId) {

		Log.info(order.toString());
		Order o = orderDAO.edit(order);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		URI orderUri = builder.path(o.getId().toString()).build();
		Log.info(o.toString());
		return Response.accepted(o).header("path", orderUri).build();

	}

	@DELETE
	@Path("/{orderId}")
	public Response deleteOrder(Order order, @PathParam("orderId") Long orderId) {
		order.setId(orderId);
		orderDAO.remove(order);
		return Response.noContent().build();
	}

	@Path("/{orderId}/items")
	public ItemsEndpoint getItemsEndpoint() {
		return itemsEndpoint;// itemsEndpoint;
	}

}
