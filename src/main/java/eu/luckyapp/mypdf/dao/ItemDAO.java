package eu.luckyapp.mypdf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Item_;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.Order_;

@Stateless
public class ItemDAO extends AbstractDAO<Item> {

	public ItemDAO() {
		super(Item.class);

	}

	@Inject
	EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<Item> findAllByOrder(Long order) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Item> cq = cb.createQuery(Item.class);
		Root<Item> itemEntity = cq.from(Item.class);
		Join<Item, Order> purchaseOrder = itemEntity.join(Item_.order);

		cq.where(cb.equal(purchaseOrder.get(Order_.id),order ));//cb.like(cb.upper(purchaseOrder.get(Order_.number)), order.toUpperCase())
		TypedQuery<Item> query = em.createQuery(cq);

		return query.getResultList();
	}

}
