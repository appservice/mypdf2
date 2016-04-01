/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package eu.luckyapp.mypdf.dao;

import eu.luckyapp.mypdf.model.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;


/**
 * Lukasz Mochel
 * Private company LuckyApp
 * Created by lmochel on 2016-03-29.
 */
public interface OrderRepository extends JpaRepository<Order,Long>,JpaSpecificationExecutor<Order> {

    @Query("select o from Order o")
    Stream<Order> getAllAsStream();//Specification<Order> specification

}
