package eu.luckyapp.mypdf.rs;

import java.util.Date;

import javax.ws.rs.QueryParam;

public class OrderQueryParam {
	@QueryParam("supplier")
	private String supplier;
	@QueryParam("purchaser")
	String purchaser;
	@QueryParam("factory")
	private String factory;
	@QueryParam("suppliesGroup")
	private String suppliesGroup;
	@QueryParam("orderDateFrom")
	private long orderDateFromLong;
	@QueryParam("orderDateTo")
	private long orderDateToLong;
	@QueryParam("orderReference")
	private String orderReference;
	@QueryParam("itemName")
	private String itemName;
	@QueryParam("itemIndex")
	private String itemIndex;
	@QueryParam("itemDescription")
	private String itemDescription;
	@QueryParam("receivingPerson")
	private String receivingPerson;

	@QueryParam("warehouseReleaseDateFrom")
	private long warehouseReleaseDateFromLong;
	@QueryParam("warehouseReleaseDateTo")
	private long warehouseReleaseDateToLong;
	@QueryParam("warehouseReleasePerson")
	private String warehouseReleasePerson;
	@QueryParam("mpk")
	private String mpk;
	@QueryParam("overdueTime")
	private Integer overdueTime;

	@QueryParam("startPosition")
	private int startPosition;
	@QueryParam("maxResult")
	private int maxResult;

	/**
	 * @return the supplier
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * @return the purchaser
	 */
	public String getPurchaser() {
		return purchaser;
	}

	/**
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * @return the suppliesGroup
	 */
	public String getSuppliesGroup() {
		return suppliesGroup;
	}

	/**
	 * @return the orderDateFromLong
	 */
	public long getOrderDateFromLong() {
		return orderDateFromLong;
	}

	/**
	 * @return the orderDateToLong
	 */
	public long getOrderDateToLong() {
		return orderDateToLong;
	}

	/**
	 * @return the orderReference
	 */
	public String getOrderReference() {
		return orderReference;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the itemIndex
	 */
	public String getItemIndex() {
		return itemIndex;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @return the receivingPerson
	 */
	public String getReceivingPerson() {
		return receivingPerson;
	}

	/**
	 * @return the warehouseReleaseDateFromLong
	 */
	public long getWarehouseReleaseDateFromLong() {
		return warehouseReleaseDateFromLong;
	}

	/**
	 * @return the warehouseReleaseDateToLong
	 */
	public long getWarehouseReleaseDateToLong() {
		return warehouseReleaseDateToLong;
	}

	/**
	 * @return the warehouseReleasePerson
	 */
	public String getWarehouseReleasePerson() {
		return warehouseReleasePerson;
	}

	/**
	 * @return the mpk
	 */
	public String getMpk() {
		return mpk;
	}

	/**
	 * @return the overdueTime
	 */
	public Integer getOverdueTime() {
		return overdueTime;
	}

	/**
	 * @return the startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * @return the maxResult
	 */
	public int getMaxResult() {
		return maxResult;
	}

	/*
	 * Log.info("orderNumber: " + orderNumber); Log.info("supplier: " +
	 * supplier); Log.info("purchaser: " + purchaser); Log.info("factory: " +
	 * factory); Log.info("suppliesGroup: " + suppliesGroup); Log.info(
	 * "itemName: " + itemName);
	 * 
	 * Log.info("orderReference: " + orderReference); Log.info(
	 * "warehouseReleasePerson: " + warehouseReleasePerson);
	 * 
	 */
public	Date orderDateFrom() {
		if (orderDateFromLong > 0) {
			return new Date(orderDateFromLong);
		}
		return null;
	}
	// Log.info("orderDateFrom: " + orderDateFrom);

public	Date orderDateTo() {
		if (orderDateToLong > 0) {
			return new Date(orderDateToLong);
		}
		return null;
	}
	// Log.info("orderDateTo: " + orderDateTo);

public	Date warehouseReleaseDateFrom() {
		if (warehouseReleaseDateFromLong > 0) {
			return new Date(warehouseReleaseDateFromLong);

		}
		return null;

	}
	// Log.info("warehouseReleaseDateFrom: " + warehouseReleaseDateFrom);

public	Date warehouseReleaseDateTo() {
		if (warehouseReleaseDateToLong > 0) {
			return new Date(warehouseReleaseDateToLong);

			// Log.info("warehouseReleaseDateTo: " + warehouseReleaseDateTo);*/

		}
		return null;
	}
}