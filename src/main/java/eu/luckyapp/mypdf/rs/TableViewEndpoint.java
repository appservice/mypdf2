package eu.luckyapp.mypdf.rs;

import eu.luckyapp.mypdf.model.TableView;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

/**
 * 
 */
@Stateless
@Path("/tableviews")
public class TableViewEndpoint {

	@Inject
	private EntityManager em;

	@POST
	@Consumes({ "application/json", "application/xml" })
	public Response create(TableView entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(TableViewEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		TableView entity = em.find(TableView.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<TableView> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT t FROM TableView t WHERE t.id = :entityId ORDER BY t.id",
						TableView.class);
		findByIdQuery.setParameter("entityId", id);
		TableView entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity.getMpk()).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public List<TableView> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<TableView> findAllQuery = em.createQuery(
				"SELECT DISTINCT t FROM TableView t ORDER BY t.id",
				TableView.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes({ "application/json", "application/xml" })
	 @Produces({ "application/json", "application/xml" })
	public Response update(TableView entity) {
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}
}
