package eu.luckyapp.mypdf.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Item_;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.Order_;

@Stateless
public class OrderDAO extends AbstractDAO<Order> {
	@Inject
	Logger Log;

	@Inject
	EntityManager em;
	
	@Resource
	SessionContext context;

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

	/**
	 * 
	 * @param orderNumber
	 * @param supplier
	 * @param purchaser
	 * @param factory
	 * @return
	 */
	public List<Order> findByNumber(String orderNumber, String supplier, String purchaser, String factory,String suppliesGroup,Date orderDateFrom,Date orderDateTo,
			String orderReference, String itemName, String itemIndex, String itemDescription,Date warehouseReleaseDateFrom,
			Date warehouseReleaseDateTo,String warehouseReleasePerson,String mpk,Integer overdueTime, int startPosition,	int maxResult) {
		// orderNumber.replaceAll("*","%");
		Log.info(orderNumber);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> orderEntity = cq.from(Order.class);

		Join<Order, Item> items = orderEntity.join(Order_.items, JoinType.INNER);
		List<Predicate> predList = new ArrayList<>();

		if (orderNumber != null && !orderNumber.isEmpty()) {
			Predicate pByNumber = cb.and(cb.like(cb.upper(orderEntity.get(Order_.number)), orderNumber));
			predList.add(pByNumber);
		}

		if (supplier != null && !supplier.isEmpty()) {
			Predicate pBySupplier = cb.and(cb.like(cb.upper(orderEntity.get(Order_.supplier)), supplier.toUpperCase()));
			predList.add(pBySupplier);
		}

		if (purchaser != null && !purchaser.isEmpty()) {
			Predicate pByPurchaser = cb
					.and(cb.like(cb.upper(orderEntity.get(Order_.purchaser)), purchaser.toUpperCase()));
			predList.add(pByPurchaser);
		}

		if (factory != null && !factory.isEmpty()) {
			Predicate pByFactory = cb.and(cb.like(cb.upper(orderEntity.get(Order_.factory)), factory.toUpperCase()));
			predList.add(pByFactory);
		}
		if (suppliesGroup != null && !suppliesGroup.isEmpty()) {
			Predicate pBySuppliesGroup = cb.and(cb.like(cb.upper(orderEntity.get(Order_.suppliesGroup)), suppliesGroup.toUpperCase()));
			predList.add(pBySuppliesGroup);
		}
		
		
		if (context.isCallerInRole("DYMEK")) {
			Predicate p = cb.and(cb.like(cb.upper(orderEntity.get(Order_.suppliesGroup)), "GZH"));
			predList.add(p);
		}
		
		if (orderReference != null && !orderReference.isEmpty()) {
			Predicate pByOrderReference = cb
					.and(cb.like(cb.upper(orderEntity.get(Order_.orderReference)), orderReference.toUpperCase()));
			predList.add(pByOrderReference);
		}
		
	//	Date orderDateFrom=null;
		if (orderDateFrom != null ) {
			Predicate pByOrderReference = cb
					.and(cb.greaterThanOrEqualTo(orderEntity.get(Order_.date), orderDateFrom));
			predList.add(pByOrderReference);
		}

		if (orderDateTo != null ) {
			Predicate pByOrderDateTo = cb
					.and(cb.lessThanOrEqualTo(orderEntity.get(Order_.date), orderDateTo));
			predList.add(pByOrderDateTo);
		}


	
		if (itemName != null && !itemName.isEmpty()) {

			Predicate pByItemName = cb.and(cb.like(cb.upper(items.get(Item_.name)), itemName.toUpperCase()));// cb.and(cb.like(cb.upper(orderEntity.get(Order_.items)),
																												// itemName.toUpperCase()));
			predList.add(pByItemName);
		}

		if (itemIndex != null && !itemIndex.isEmpty()) {
			Predicate pByItemIndex = cb.and(cb.like(cb.upper(items.get(Item_.itemIndex)), itemIndex.toUpperCase())); // itemName.toUpperCase()));
			predList.add(pByItemIndex);
		}

		if (itemDescription != null && !itemDescription.isEmpty()) {
			Predicate pByItemDescription = cb
					.and(cb.like(cb.upper(items.get(Item_.description)), itemDescription.toUpperCase())); // itemName.toUpperCase()));
			predList.add(pByItemDescription);
		}
		
		
		
		if (warehouseReleaseDateFrom != null ) {
		Predicate p = cb
				.and(cb.greaterThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateFrom));
		predList.add(p);
	}

	if (warehouseReleaseDateTo != null ) {
		Predicate p = cb
				.and(cb.lessThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateTo));
		predList.add(p);
	}

	if (warehouseReleasePerson != null && !warehouseReleasePerson.isEmpty()) {
		Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.warehouseReleasePerson)), warehouseReleasePerson.toUpperCase())); // itemName.toUpperCase()));
		predList.add(p);
	}
	
	if (mpk != null && !mpk.isEmpty()) {
		Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.mpk)), mpk.toUpperCase())); // itemName.toUpperCase()));
		predList.add(p);
	}


	if(overdueTime!=null){
		//Path<Date> deliveryDate=items.get(Item_.deliveryDate);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC+1"));//Munich time 
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -overdueTime);//substract the number of days to look back 		
		Date dateToLookBackAfter = calendar.getTime();
		
		Path<Date> expectedDeliveryDate=items.get(Item_.expectedDeliveryDate);
		//Expression<java.sql.Date>nowDate=cb.currentDate();
		
		Predicate p=cb.and(cb.isTrue(items.get(Item_.isDispatched)));
		Predicate p2=cb.and(cb.greaterThanOrEqualTo(expectedDeliveryDate, dateToLookBackAfter));
		
		predList.add(p);
		predList.add(p2);
	}
	
	
		cq.where(predList.toArray(new Predicate[predList.size()]));
		cq.orderBy(cb.desc(orderEntity.get(Order_.number)));
		cq.distinct(true);

		TypedQuery<Order> query = em.createQuery(cq);

		if (startPosition > 0) {
			query.setFirstResult(startPosition);

		}
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		List<Order> orders = query.getResultList();
		// Log.info(orders.toString());
		return orders;
	}

}
