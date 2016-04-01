/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package eu.luckyapp.mypdf.service;

import eu.luckyapp.mypdf.dao.OrderRepository;
import eu.luckyapp.mypdf.dao.OrderSpecification;
import eu.luckyapp.mypdf.dao.PickupOrderSpecification;
import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.rs.OrderQueryParam;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lukasz Mochel
 * Private company LuckyApp
 * Created by lmochel on 2016-03-29.
 */
@Stateless
@Path("paged-orders")
public class OrderService {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    Logger Log;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    OrderRepository orderRepository;

    @Resource
    SessionContext context;

    @Inject
    CvsCreator cvsCreator;

    public List<Order> getAll() {
        orderRepository.findAll();
        return orderRepository.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Order> getAllInPage(@BeanParam OrderQueryParam orderQueryParam, @QueryParam("page") int page, @QueryParam("size") int size, @DefaultValue("ASC") @QueryParam("sort") Sort.Direction sort) {

        Log.debug("OrderQueryParam {}", orderQueryParam.toString());
        try {
            orderQueryParam.changeWildcardsInStringFieldsToSql();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
        PageRequest pageRequest = new PageRequest(page, size, sort, "number");
        OrderSpecification orderSpecification = new OrderSpecification(orderQueryParam, context);

        return orderRepository.findAll(orderSpecification, pageRequest);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id:\\d+}")
    public Order findOne(@PathParam("id") Long id) {
        return orderRepository.findOne(id);
    }

    @GET
    @Path("/for-pickup")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<Order> getOrdersForPickUpByDepartment(@QueryParam("department") @DefaultValue("") String department,
                                                      @QueryParam("startPosition") @DefaultValue("0") int page,
                                                      @QueryParam("maxResult") @DefaultValue("10") int size) {

        Specification<Order> specification = new PickupOrderSpecification(department);
        PageRequest pageRequest = new PageRequest(page, size);
        Page<Order> orders = orderRepository.findAll(specification, pageRequest);


        return filterItemsInOrder(orders);
    }

    private Page<Order> filterItemsInOrder(Page<Order> orders) {
        orders.forEach(order -> order.setItems(order.getItems()
                .stream()
                .filter(item -> item.getWarehouseReleasePerson() == null || item.getWarehouseReleasePerson().isEmpty())
                .filter(item -> !item.getBudget().isEmpty())
                .filter(Item::isDispatched)
                .collect(Collectors.toList())));

        return orders;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        return Response.accepted(savedOrder).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id:\\d+}")
    public Response deleteOrder(@PathParam("id") Long id, Order order) {
        order.setId(id);
        orderRepository.delete(order);

        return Response.noContent().build();
    }


    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id:\\d+}")
    public Response deleteOrder(@PathParam("id") Long id) {
        orderRepository.delete(id);
        return Response.noContent().build();
    }


    @GET
    @Path("/cvs")
    @Produces("text/csv")
    //@RolesAllowed(value = "ADMIN")
    public Response createFile(@BeanParam OrderQueryParam orderQueryParam) {

        if (context.isCallerInRole("ADMIN")) {

            try {
                orderQueryParam.changeWildcardsInStringFieldsToSql();
            } catch (IllegalAccessException e) {
                Response.serverError().header("error", e.getMessage()).build();
                Log.error(e.getMessage());
            }


            Specification<Order> specification = new OrderSpecification(orderQueryParam, context);
            StreamingOutput streamingOutput = output -> {
                cvsCreator.getData(specification, output);
                output.flush();
            };


            return Response.ok(streamingOutput).header("Content-Disposition", "attachment; filename=\"zestawienie_zamowien.csv\"").build();
        }
        return Response.noContent().header("error","User have not sufficient permissions for this functional!").entity("Brak uprawnień do tej transakcji!").build();

    }
}
