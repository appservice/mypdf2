package eu.luckyapp.mypdf.dao;

import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Item_;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.Order_;
import eu.luckyapp.mypdf.rs.OrderQueryParam;
import org.hibernate.criterion.*;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Stateless
public class OrderDAO extends AbstractDAO<Order> {
    @Inject
    Logger Log;

    @Inject
    EntityManager em;

    @Resource
    SessionContext context;

    private CriteriaBuilder cb;
    private CriteriaQuery<Order> cq;
    private Root<Order> orderEntity;
    private Join<Order, Item> items;
    private List<Predicate> predicates;
    private OrderQueryParam orderQueryParam;

    public OrderDAO() {
        super(Order.class);

    }


    @PostConstruct
    public void init() {
        initCriteriaObjects();
    }

    protected void initCriteriaObjects() {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(Order.class);
        orderEntity = cq.from(Order.class);

        items = orderEntity.join(Order_.items, JoinType.INNER);
        // items =(Join) orderEntity.fetch(Order_.items);
        //  orderEntity.fetch(Order_.items);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Order findByNumberStrict(String orderNumber) {

        try {
            return (Order) em.createNamedQuery(Order.FIND_BY_NUMBER).setParameter("orderNumber", orderNumber)
                    .getSingleResult();
        } catch (NoResultException ex) {
            Log.error(ex.getMessage());

            return null;
        }

    }


    /**
     * @param orderQueryParam
     * @return List of found  orders by orderQueryParam
     */
    public List<Order> findByNumber(OrderQueryParam orderQueryParam) throws IllegalAccessException {
        orderQueryParam.changeWildcardsInStringFieldsToSql();
        this.orderQueryParam = orderQueryParam;
        // Log.info(orderQueryParam.toString());

        predicates = new ArrayList<>();

        addOrderNumberToPredicates();
        addSupplierToPredicates();
        addPurchaserToPredicates();
        addFactoryToPredicates();
        addSuppliesGroupToPredicates();
        addDymekRoleToPredicates();
        addOrderReferenceToPredicates();
        addOrderDataFromToPredicates();
        addOrderDateToToPredicates();
        addItemNameToPredicates();
        addItemIndexToPredicates();
        addItemDescriptionToPredicates();
        addWarehouseReleaseDateFromToPredicates();
        addWarehouseReleaseDateToToPredicates();
        addWarehouseReleasePersonToPredicates();
        addMpkToPredicates();
        addBudgetToPredicates();
        addReceivingPersonToPredicates();
        addOverdueDaysToPredicates();

        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        cq.orderBy(cb.desc(orderEntity.get(Order_.number)));
        cq.distinct(true);

      /*  Log.info(String.valueOf(predicates.size()));
        if(predicates.size()<1){
            this.items=orderEntity.join(Order_.items);
        }*/


        TypedQuery<Order> query = em.createQuery(cq);
        //  Log.info(String.valueOf(query.getResultList().size()));
        setFirstPosition(query);
        setMaxResults(query);
        //Log.info(String.valueOf(query.getResultList().size()));
        return query.getResultList();
    }


    public List<Order> findOrderForPickUp(String department, int startPosition, int maxResult) {

        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Order.class);
        Root<Order> orderEntity = cq.from(Order.class);
        Join<Order, Item> localeItems = (Join) orderEntity.fetch(Order_.items);

        Predicate p1 = cb.and(cb.like(cb.upper(orderEntity.get(Order_.factory)), "D705"));
        Predicate p2 = cb.and(cb.notLike(cb.upper(orderEntity.get(Order_.suppliesGroup)), "GZH"));
        Predicate p3 = cb.and(cb.like(cb.upper(orderEntity.get(Order_.orderReference)), department.toUpperCase()));
        Predicate p4 = cb.and(cb.isNotNull(localeItems.get(Item_.budget)));
        Predicate p5 = cb.and(cb.notLike(localeItems.get(Item_.budget), ""));
        Predicate p6 = cb.and(cb.isTrue(localeItems.get(Item_.isDispatched)));
        Predicate p7 = cb.and(cb.isNull(localeItems.get(Item_.realisingPerson)));


        predicates.add(p1);
        predicates.add(p2);
        predicates.add(p3);
        predicates.add(p4);
        predicates.add(p5);
        predicates.add(p6);
        predicates.add(p7);


        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        cq.orderBy(cb.desc(orderEntity.get(Order_.number)));
        cq.distinct(true);


        TypedQuery<Order> query = em.createQuery(cq);

        query.setFirstResult(startPosition);
        query.setMaxResults(maxResult);
        // Log.info();

        return query.getResultList();
    }

    private void addOrderNumberToPredicates() {
        String orderNumber = orderQueryParam.getOrderNumber();
        if (orderNumber != null && !orderNumber.isEmpty()) {
            Predicate pByNumber = cb.and(cb.like(cb.upper(orderEntity.get(Order_.number)), orderNumber));
            predicates.add(pByNumber);
        }
    }

    private void addDymekRoleToPredicates() {
        if (context.isCallerInRole("DYMEK")) {
            Predicate p = cb.and(cb.like(cb.upper(orderEntity.get(Order_.suppliesGroup)), "GZH"));
            predicates.add(p);
        }
    }


    private void addSupplierToPredicates() {
        String supplier = orderQueryParam.getSupplier();
        if (supplier != null && !supplier.isEmpty()) {
            Predicate pBySupplier = cb.and(cb.like(cb.upper(orderEntity.get(Order_.supplier)), supplier.toUpperCase()));
            predicates.add(pBySupplier);
        }
    }

    private void addOverdueDaysToPredicates() {
        if (orderQueryParam.getOverdueDays() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -orderQueryParam.getOverdueDays());
            Date myDate = calendar.getTime();

            // Log.info(myDate.toLocaleString());
            Predicate predicate = cb.and(cb.lessThan(items.get(Item_.expectedDeliveryDate), myDate));
            Predicate predicate2 = cb.and(cb.isFalse(items.get(Item_.isDispatched)));
            //predicate.
            predicates.add(predicate);
            predicates.add(predicate2);
        }
    }

    private void addReceivingPersonToPredicates() {
        String receivingPerson = orderQueryParam.getReceivingPerson();
        if (receivingPerson != null && !receivingPerson.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.receivingPerson)), receivingPerson.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }
    }

    private void addBudgetToPredicates() {
        String budget = orderQueryParam.getBudget();
        if (budget != null && !budget.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.budget)), budget.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }
    }

    private void addMpkToPredicates() {
        String mpk = orderQueryParam.getMpk();
        if (mpk != null && !mpk.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.mpk)), mpk.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }
    }

    private void addWarehouseReleasePersonToPredicates() {
        String warehouseReleasePerson = orderQueryParam.getWarehouseReleasePerson();
        if (warehouseReleasePerson != null && !warehouseReleasePerson.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.warehouseReleasePerson)), warehouseReleasePerson.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }
    }

    private void addWarehouseReleaseDateToToPredicates() {
        Date warehouseReleaseDateTo = orderQueryParam.warehouseReleaseDateTo();
        if (warehouseReleaseDateTo != null) {
            Predicate p = cb
                    .and(cb.lessThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateTo));
            predicates.add(p);
        }
    }

    private void addWarehouseReleaseDateFromToPredicates() {
        Date warehouseReleaseDateFrom = orderQueryParam.warehouseReleaseDateFrom();
        if (warehouseReleaseDateFrom != null) {
            Predicate p = cb.and(cb.greaterThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateFrom));
            predicates.add(p);
        }
    }

    private void addItemDescriptionToPredicates() {
        String itemDescription = orderQueryParam.getItemDescription();
        if (itemDescription != null && !itemDescription.isEmpty()) {
            Predicate pByItemDescription = cb.and(cb.like(cb.upper(items.get(Item_.description)), itemDescription.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(pByItemDescription);
        }
    }

    private void addItemIndexToPredicates() {
        String itemIndex = orderQueryParam.getItemIndex();
        if (itemIndex != null && !itemIndex.isEmpty()) {
            Predicate pByItemIndex = cb.and(cb.like(cb.upper(items.get(Item_.itemIndex)), itemIndex.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(pByItemIndex);
        }
    }

    private void addItemNameToPredicates() {
        String itemName = orderQueryParam.getItemName();
        if (itemName != null && !itemName.isEmpty()) {

            Predicate pByItemName = cb.and(cb.like(cb.upper(items.get(Item_.name)), itemName.toUpperCase()));// cb.and(cb.like(cb.upper(orderEntity.get(Order_.items)),
            // itemName.toUpperCase()));
            predicates.add(pByItemName);
        }
    }

    private void addOrderDateToToPredicates() {
        Date orderDateTo = orderQueryParam.orderDateTo();
        if (orderDateTo != null) {
            Predicate pByOrderDateTo = cb
                    .and(cb.lessThanOrEqualTo(orderEntity.get(Order_.date), orderDateTo));
            predicates.add(pByOrderDateTo);
        }
    }

    private void addOrderDataFromToPredicates() {
        Date orderDateFrom = orderQueryParam.orderDateFrom();
        if (orderDateFrom != null) {
            Predicate pByOrderReference = cb
                    .and(cb.greaterThanOrEqualTo(orderEntity.get(Order_.date), orderDateFrom));
            predicates.add(pByOrderReference);
        }
    }

    private void addOrderReferenceToPredicates() {
        String orderReference = orderQueryParam.getOrderReference();
        if (orderReference != null && !orderReference.isEmpty()) {
            Predicate pByOrderReference = cb
                    .and(cb.like(cb.upper(orderEntity.get(Order_.orderReference)), orderReference.toUpperCase()));
            predicates.add(pByOrderReference);
        }
    }

    private void addSuppliesGroupToPredicates() {
        String suppliesGroup = orderQueryParam.getSuppliesGroup();
        if (suppliesGroup != null && !suppliesGroup.isEmpty()) {
            Predicate pBySuppliesGroup = cb.and(cb.like(cb.upper(orderEntity.get(Order_.suppliesGroup)), suppliesGroup.toUpperCase()));
            predicates.add(pBySuppliesGroup);
        }
    }

    private void addFactoryToPredicates() {
        String factory = orderQueryParam.getFactory();
        if (factory != null && !factory.isEmpty()) {
            Predicate pByFactory = cb.and(cb.like(cb.upper(orderEntity.get(Order_.factory)), factory.toUpperCase()));
            predicates.add(pByFactory);
        }
    }

    private void addPurchaserToPredicates() {
        String purchaser = orderQueryParam.getPurchaser();
        if (purchaser != null && !purchaser.isEmpty()) {
            Predicate pByPurchaser = cb
                    .and(cb.like(cb.upper(orderEntity.get(Order_.purchaser)), purchaser.toUpperCase()));
            predicates.add(pByPurchaser);
        }
    }

    private void setFirstPosition(TypedQuery<Order> query) {
        int startPosition = orderQueryParam.getStartPosition();
        if (startPosition > 0) {
            query.setFirstResult(startPosition);

        }
    }

    private void setMaxResults(TypedQuery<Order> query) {
        int maxResult = orderQueryParam.getMaxResult();
        if (maxResult > 0) {
            query.setMaxResults(maxResult);
        }
    }
}
