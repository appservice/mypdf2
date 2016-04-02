/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package eu.luckyapp.mypdf.service;

import eu.luckyapp.mypdf.dao.OrderRepository;
import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Order;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import javax.inject.Inject;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Lukasz Mochel
 * Private company LuckyApp
 * Created by LMochel on 2016-03-30.
 */
@SuppressWarnings("CdiInjectionPointsInspection")
public class CvsCreator {


    @Inject
    private OrderRepository orderRepository;


    @Inject
    private Logger Log;

    private Locale locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
    private NumberFormat numberFormat = NumberFormat.getInstance(locale);


    PrintWriter getData(Specification<Order> specification, OutputStream os) {


        List<Order> orders = orderRepository.findAll(specification);

        try (PrintWriter printWriter = new PrintWriter(os)) {

            printWriter.println(prepareHeaderCvsRow());

            orders.stream()
                    .flatMap(order -> order.getItems().stream()
                            .map(item -> prepareCvsRow(order, item)))
                    .forEach(printWriter::println);
            return printWriter;

        }


    }

    private String prepareHeaderCvsRow() {

        return "Zamówienie" + ";" +
                "Poz." + ";" +
                "Index" + ";" +
                "Materiał" + ";" +
                "Ilość" + ";" +
                "JM" + ";" +
                "Wartość" + ";" +
                "Waluta" + ";" +
                "Spodziewana data dost." + ";" +
                "Data dost." + ";" +
                "MPK" + ";" +
                "Zlec." + ";" +
                "Przyjmujący" + ";" +
                "Os. odbierjąca" + ";" +
                "Data odbioru" + ";" +
                "Wydawał" + ";" +
                "Dostarczone" + ";" +
                "Opis dod." + ";" +

                "Fabryka" + ";" +
                "Dostawca" + ";" +
                "Grupa zaop.;" +
                "Referencja" + ";";
    }


    private String prepareCvsRow(Order order, Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(order.getNumber()).append(";")
                .append(item.getPositionInOrder()).append(";").append("=\"")
                .append(item.getItemIndex()).append("\";")
                .append(item.getName().replace(';', ',')).append(";")
                .append(numberFormat.format(item.getAmount())).append(";")
                .append(item.getUnit()).append(";")
                .append(numberFormat.format(item.getPrice())).append(";")
                .append(item.getCurrency()).append(";")
                .append(item.getExpectedDeliveryDate()).append(";");
        //item.getDeliveryDate() != null?sb.append(item.getDeliveryDate()):sb.append(";");
        if (item.getDeliveryDate() != null) {
            sb.append(simpleDateFormat.format(item.getDeliveryDate()));
        } else {
            sb.append("");
        }
        sb.append(";");

        sb.append(item.getMpk()).append(";")
                .append(item.getBudget()).append(";");

        String receivingPerson = item.getReceivingPerson() != null ? item.getReceivingPerson() : "";
        sb.append(receivingPerson).append(";");

        String warehouseRealesePerson = item.getWarehouseReleasePerson() != null ? item.getWarehouseReleasePerson() : "";
        sb.append(warehouseRealesePerson).append(";");

        if (item.getWarehouseReleaseDate() != null) {
            sb.append(simpleDateFormat.format(item.getWarehouseReleaseDate()));
        } else {
            sb.append("");
        }
        sb.append(";");

        String realisingPerson = item.getRealisingPerson() != null ? item.getReceivingPerson() : "";
        sb.append(realisingPerson).append(";");


        if (item.isDispatched()) {
            sb.append("x");
        } else {
            sb.append("");
        }
        sb.append(";");

        String description = item.getDescription() != null ? item.getDescription().replace(';', ',').replaceAll("(\\r\\n|\\n)", "   ") : "";
        //  Log.info(description);
        sb.append(description).append(";")

                .append(order.getSuppliesGroup()).append(";")
                .append(order.getFactory()).append(";")
                .append(order.getSupplier()).append(";")
                .append(order.getOrderReference().replace(';', ',').replaceAll("(\\r\\n|\\n)", "   "));


        return sb.toString();
    }
}
