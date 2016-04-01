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
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lukasz Mochel
 * Private company LuckyApp
 * Created by LMochel on 2016-03-29.
 */
public class PickupOrderSpecification implements Specification<Order> {
    private String department;

    public PickupOrderSpecification(String deparment) {
        this.department = deparment;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        criteriaQuery.distinct(true);

       Join<Order, Item> localeItems =root.join(Order_.items,JoinType.INNER);//(Join)root.fetch(Order_.items);//root.join(Order_.items,JoinType.INNER);//(Join)root.fetch(Order_.items); /*root.join(Order_.items,JoinType.INNER);//*///(Join) root.fetch(Order_.items);
      // Fetch<Order,Item>localeItems= root.fetch(Order_.items,JoinType.INNER);
        List<Predicate> predicates = new ArrayList<>();
//cb.conjunction();

        Predicate p1 = cb.and(cb.like(cb.upper(root.get(Order_.factory)), "D705"));
        Predicate p2 = cb.and(cb.notLike(cb.upper(root.get(Order_.suppliesGroup)), "GZH"));
        Predicate p3 = cb.and(cb.like(cb.upper(root.get(Order_.orderReference)), "%" + department + "%".toUpperCase()));
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


        return andTogether(predicates,cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
