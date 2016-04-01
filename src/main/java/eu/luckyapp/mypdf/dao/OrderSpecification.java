/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package eu.luckyapp.mypdf.dao;

import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Item_;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.Order_;
import eu.luckyapp.mypdf.rs.OrderQueryParam;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Lukasz Mochel
 * Private company LuckyApp
 * Created by LMochel on 2016-03-29.
 */
public class OrderSpecification implements Specification<Order> {



    private OrderQueryParam orderQueryParam;
    private  SessionContext context;

    public OrderSpecification(OrderQueryParam orderQueryParam, SessionContext context) {
        this.orderQueryParam = orderQueryParam;
        this.context=context;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

        Join<Order, Item> items = root.join(Order_.items, JoinType.INNER);

        List<Predicate> predicates =  new ArrayList<>();

        addOrderNumberPredicate(root, cb, predicates);
        addSupplierPredicate(root, cb, predicates);
        addOverdueDaysPredicate(cb, items, predicates);
        addReceivingPersonPredicate(cb, items, predicates);

        String budget = orderQueryParam.getBudget();
        if (budget != null && !budget.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.budget)), budget.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }

        String mpk = orderQueryParam.getMpk();
        if (mpk != null && !mpk.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.mpk)), mpk.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }

        String warehouseReleasePerson = orderQueryParam.getWarehouseReleasePerson();
        if (warehouseReleasePerson != null && !warehouseReleasePerson.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.warehouseReleasePerson)), warehouseReleasePerson.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }

        Date warehouseReleaseDateTo = orderQueryParam.warehouseReleaseDateTo();
        if (warehouseReleaseDateTo != null) {
            Predicate p = cb
                    .and(cb.lessThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateTo));
            predicates.add(p);
        }

        Date warehouseReleaseDateFrom = orderQueryParam.warehouseReleaseDateFrom();
        if (warehouseReleaseDateFrom != null) {
            Predicate p = cb.and(cb.greaterThanOrEqualTo(items.get(Item_.warehouseReleaseDate), warehouseReleaseDateFrom));
            predicates.add(p);
        }

        String itemDescription = orderQueryParam.getItemDescription();
        if (itemDescription != null && !itemDescription.isEmpty()) {
            Predicate pByItemDescription = cb.and(cb.like(cb.upper(items.get(Item_.description)), itemDescription.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(pByItemDescription);
        }

        String itemIndex = orderQueryParam.getItemIndex();
        if (itemIndex != null && !itemIndex.isEmpty()) {
            Predicate pByItemIndex = cb.and(cb.like(cb.upper(items.get(Item_.itemIndex)), itemIndex.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(pByItemIndex);
        }

        String itemName = orderQueryParam.getItemName();
        if (itemName != null && !itemName.isEmpty()) {

            Predicate pByItemName = cb.and(cb.like(cb.upper(items.get(Item_.name)), itemName.toUpperCase()));// cb.and(cb.like(cb.upper(orderEntity.get(Order_.items)),
            // itemName.toUpperCase()));
            predicates.add(pByItemName);
        }

        Date orderDateTo = orderQueryParam.orderDateTo();
        if (orderDateTo != null) {
            Predicate pByOrderDateTo = cb
                    .and(cb.lessThanOrEqualTo(root.get(Order_.date), orderDateTo));
            predicates.add(pByOrderDateTo);
        }

        Date orderDateFrom = orderQueryParam.orderDateFrom();
        if (orderDateFrom != null) {
            Predicate pByOrderReference = cb
                    .and(cb.greaterThanOrEqualTo(root.get(Order_.date), orderDateFrom));
            predicates.add(pByOrderReference);
        }

        String orderReference = orderQueryParam.getOrderReference();
        if (orderReference != null && !orderReference.isEmpty()) {
            Predicate pByOrderReference = cb
                    .and(cb.like(cb.upper(root.get(Order_.orderReference)), orderReference.toUpperCase()));
            predicates.add(pByOrderReference);
        }

        String suppliesGroup = orderQueryParam.getSuppliesGroup();
        if (suppliesGroup != null && !suppliesGroup.isEmpty()) {
            Predicate pBySuppliesGroup = cb.and(cb.like(cb.upper(root.get(Order_.suppliesGroup)), suppliesGroup.toUpperCase()));
            predicates.add(pBySuppliesGroup);
        }

        String factory = orderQueryParam.getFactory();
        if (factory != null && !factory.isEmpty()) {
            Predicate pByFactory = cb.and(cb.like(cb.upper(root.get(Order_.factory)), factory.toUpperCase()));
            predicates.add(pByFactory);
        }

        String purchaser = orderQueryParam.getPurchaser();
        if (purchaser != null && !purchaser.isEmpty()) {
            Predicate pByPurchaser = cb
                    .and(cb.like(cb.upper(root.get(Order_.purchaser)), purchaser.toUpperCase()));
            predicates.add(pByPurchaser);
        }

        if (context.isCallerInRole("DYMEK")) {
            Predicate p = cb.and(cb.like(cb.upper(root.get(Order_.suppliesGroup)), "GZH"));
            predicates.add(p);
        }

        criteriaQuery.distinct(true);

        return andTogether(predicates, cb);
    }

    private void addOrderNumberPredicate(Root<Order> root, CriteriaBuilder cb, List<Predicate> predicates) {
        String orderNumber = orderQueryParam.getOrderNumber();
        if (orderNumber != null && !orderNumber.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(root.get(Order_.number)), orderNumber.toUpperCase()));
            predicates.add(p);
        }
    }

    private void addOverdueDaysPredicate(CriteriaBuilder cb, Join<Order, Item> items, List<Predicate> predicates) {
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


    private void addSupplierPredicate(Root<Order> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

        String supplier = orderQueryParam.getSupplier();
        if (supplier != null && !supplier.isEmpty()) {
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get(Order_.supplier)), supplier.toUpperCase()));
            predicates.add(predicate);
        }
    }

    private void addReceivingPersonPredicate(CriteriaBuilder cb, Join<Order, Item> items, List<Predicate> predicates) {
        String receivingPerson = orderQueryParam.getReceivingPerson();
        if (receivingPerson != null && !receivingPerson.isEmpty()) {
            Predicate p = cb.and(cb.like(cb.upper(items.get(Item_.receivingPerson)), receivingPerson.toUpperCase())); // itemName.toUpperCase()));
            predicates.add(p);
        }
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }


    private Predicate createPredicateFromString(String predicateCriterium, SingularAttribute<Order,String> attribute, CriteriaBuilder criteriaBuilder,Root<Order> root) {
        if(predicateCriterium!=null &&!predicateCriterium.isEmpty()){
            return criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get(attribute)),predicateCriterium.toUpperCase()));
        }
        return null;
    }


}
