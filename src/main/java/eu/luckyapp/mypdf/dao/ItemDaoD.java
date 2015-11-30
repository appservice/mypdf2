package eu.luckyapp.mypdf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import eu.luckyapp.mypdf.model.Item;

/**
 *  DAO for Item
 */
@Stateless
public class ItemDaoD {
	@PersistenceContext(unitName = "mypdf-persistence-unit")
	private EntityManager em;

	public void create(Item entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Item entity = em.find(Item.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Item findById(Long id) {
		return em.find(Item.class, id);
	}

	public Item update(Item entity) {
		return em.merge(entity);
	}

	public List<Item> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Item> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.order ORDER BY i.id",
						Item.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
