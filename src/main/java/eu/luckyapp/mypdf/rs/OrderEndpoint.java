package eu.luckyapp.mypdf.rs;

import java.net.URI;
import java.util.List;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.model.Order;
import org.slf4j.Logger;

@Path("/orders")
@Produces(value = {MediaType.APPLICATION_JSON})
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
    public Response getAll(@BeanParam OrderQueryParam orderQueryParam) {


        List<Order> orders = null;
        try {
            orders = orderDAO.findByNumber(orderQueryParam);
        } catch (IllegalAccessException e) {
        }
        // Log.info(orders.get(1).toString());
        return Response.accepted(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOne(@PathParam("orderId") Long id) {
        Order order = orderDAO.find(id);// orderDAO.findByNumberStrict(orderNumber);

        return Response.accepted(order).build();
    }


    @GET
    @Path("/for-pickup")
    public Response getOrdersForPickUpByDepartment(@QueryParam("department") @DefaultValue("") String department,
                                                   @QueryParam("startPosition") @DefaultValue("0") int startPosition,
                                                   @QueryParam("maxResult") @DefaultValue("10") int maxResult) {


        List<Order> orders = orderDAO.findOrderForPickUp("%" + department + "%", startPosition, maxResult);
        Log.debug(orders.toString());
        return Response.accepted(orders).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    public Response saveOrder(@Context UriInfo uriInfo, Order order, @PathParam("orderId") Long orderId) {

        Log.debug(order.toString());
        Order o = orderDAO.edit(order);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        URI orderUri = builder.path(o.getId().toString()).build();
        Log.debug(o.toString());
        return Response.accepted(o).header("path", orderUri).build();

    }

/*	@DELETE
    @Path("/{orderId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteOrder( @PathParam("orderId") Long orderId,Order order) {
		order.setId(orderId);
		orderDAO.remove(order);
		Log.info("delete with order object");
		return Response.noContent().build();
	}*/


    @DELETE
    @Path("/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("orderId") Long orderId) {
        //order.setId(orderId);
        Log.debug("delete without order object");
        Order order = orderDAO.find(orderId);
        orderDAO.remove(order);
        return Response.noContent().build();
    }


    @Path("/{orderId}/items")
    public ItemsEndpoint getItemsEndpoint() {
        return itemsEndpoint;// itemsEndpoint;
    }

}
