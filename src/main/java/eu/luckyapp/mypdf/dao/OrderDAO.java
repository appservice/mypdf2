package eu.luckyapp.mypdf.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.bouncycastle.asn1.isismtt.x509.Restriction;

import eu.luckyapp.mypdf.model.Order;

@Stateless
public class OrderDAO extends AbstractDAO<Order> {
	@Inject
	Logger Log;

	@Inject
	EntityManager em;

	public OrderDAO() {
		super(Order.class);

	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Order findByNumberStrict(String orderNumber) {

		try {
			Order order = (Order) em.createNamedQuery(Order.FIND_BY_NUMBER).setParameter("orderNumber", orderNumber)
					.getSingleResult();
			return order;
		} catch (NoResultException ex) {
			Log.info(ex.getMessage());
		
			return null;
		}

	}
	
	public List<Order> findByNumber(String orderNumber){
		//orderNumber.replaceAll("*","%");
		Log.info(orderNumber);
		CriteriaBuilder cb=em.getCriteriaBuilder();
		CriteriaQuery<Order> cq=cb.createQuery(Order.class);
		Root<Order>orderEntity=cq.from(Order.class);
		
		cq.where(cb.like(orderEntity.get("number"),orderNumber));	

		TypedQuery<Order>query=em.createQuery(cq);
		List<Order>orders=query.getResultList();
		
		return orders;
	}

}
