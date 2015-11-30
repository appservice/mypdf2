package eu.luckyapp.mypdf.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableViewToOrderParser {

//	@Inject
//	Logger Log;


	public Order parseOrder(TableView tableView) {

		Order order = new Order();
		order.setNumber(tableView.getOrderId());
		order.setPurchaser(tableView.getOrderedPerson());
		order.setDate(tableView.getOrderDate());
		order.setSuppliesGroup(tableView.getFactory());
		order.setOrderReference(tableView.getOrderReference());
		order.setSupplier(tableView.getSupplier());
		

		return order;
	}

	public Item parseItem(TableView tableView) {
		Item item = new Item();
		item.setAmount(tableView.getGoodsAmount());
		item.setBudget(tableView.getBudget());
		item.setCurrency(tableView.getGoodsPriceCurrency());
		item.setDeliveryDate(tableView.getDeliveryDate());
		item.setDescription(tableView.getDescription());
		item.setDispatched(tableView.isIsDispatched());
		item.setExpectedDeliveryDate(tableView.getExpectedDeliveryDate());
		item.setItemIndex(tableView.getIndex());
		item.setMpk(tableView.getMpk());
		item.setName(tableView.getGoodsName());
		item.setPartialDeliveryGoodsAmount(tableView.getPartialDeliveryGoodsAmount());
		item.setPositionInOrder(Integer.parseInt(tableView.getGoodsIdOnOrder()));
		item.setPrice(tableView.getGoodsPrice());
		// item.setReceivingPerson();
		item.setReleased(tableView.isIsReleased());
		item.setUnit(tableView.getGoodsUnit());
		item.setWarehouseReleaseDate(tableView.getWarehouseReleaseDate());
		item.setWarehouseReleasePerson(tableView.getWarehouseReleasePerson());

		return item;
	}

	public Set<Order> parseOrdersList(List<TableView> tableList) {
		Set<Order> ordersList =new HashSet<>();
		TableView preTableView = null;
		Order o = null;

		for (TableView t : tableList) {
			Item item = parseItem(t);
		
			// Order o=null;
			if (preTableView == null) {
				o = parseOrder(t);
			
				ordersList.add(o);

			} else {

				if (!t.getOrderId().equals(preTableView.getOrderId())) {
					o = parseOrder(t);
					
					if(!ordersList.contains(o)){
					ordersList.add(o);
					}

				}
			}
			
			item.setOrder(o);
			o.getItems().add(item);
			preTableView = t;
		}

		return ordersList;
	}
}
