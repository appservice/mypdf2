package eu.luckyapp.mypdf.rs;

import java.lang.reflect.Field;
import java.util.Date;

import javax.ws.rs.QueryParam;

public class OrderQueryParam {
    @QueryParam("number")
    private String orderNumber;

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


    @QueryParam("budget")
    private String budget;

    @QueryParam("overdueDays")
    private int overdueDays;

    @QueryParam("startPosition")
    private int startPosition;

    @QueryParam("maxResult")
    private int maxResult;

    /**
     * @return the orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

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
     * @return the overdueDays
     */
    public int getOverdueDays() {
        return overdueDays;
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
    public Date orderDateFrom() {
        if (orderDateFromLong > 0) {
            return new Date(orderDateFromLong);
        }
        return null;
    }
    // Log.info("orderDateFrom: " + orderDateFrom);

    public Date orderDateTo() {
        if (orderDateToLong > 0) {
            return new Date(orderDateToLong);
        }
        return null;
    }
    // Log.info("orderDateTo: " + orderDateTo);

    public Date warehouseReleaseDateFrom() {
        if (warehouseReleaseDateFromLong > 0) {
            return new Date(warehouseReleaseDateFromLong);

        }
        return null;

    }
    // Log.info("warehouseReleaseDateFrom: " + warehouseReleaseDateFrom);

    public Date warehouseReleaseDateTo() {
        if (warehouseReleaseDateToLong > 0) {
            return new Date(warehouseReleaseDateToLong);

            // Log.info("warehouseReleaseDateTo: " + warehouseReleaseDateTo);*/

        }
        return null;
    }


    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setSuppliesGroup(String suppliesGroup) {
        this.suppliesGroup = suppliesGroup;
    }

    public void setOrderDateFromLong(long orderDateFromLong) {
        this.orderDateFromLong = orderDateFromLong;
    }

    public void setOrderDateToLong(long orderDateToLong) {
        this.orderDateToLong = orderDateToLong;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setReceivingPerson(String receivingPerson) {
        this.receivingPerson = receivingPerson;
    }

    public void setWarehouseReleaseDateFromLong(long warehouseReleaseDateFromLong) {
        this.warehouseReleaseDateFromLong = warehouseReleaseDateFromLong;
    }

    public void setWarehouseReleaseDateToLong(long warehouseReleaseDateToLong) {
        this.warehouseReleaseDateToLong = warehouseReleaseDateToLong;
    }

    public void setWarehouseReleasePerson(String warehouseReleasePerson) {
        this.warehouseReleasePerson = warehouseReleasePerson;
    }

    public void setMpk(String mpk) {
        this.mpk = mpk;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public String getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "OrderQueryParam{" +
                "orderNumber='" + orderNumber + '\'' +
                ", supplier='" + supplier + '\'' +
                ", purchaser='" + purchaser + '\'' +
                ", factory='" + factory + '\'' +
                ", suppliesGroup='" + suppliesGroup + '\'' +
                ", orderDateFromLong=" + orderDateFromLong +
                ", orderDateToLong=" + orderDateToLong +
                ", orderReference='" + orderReference + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemIndex='" + itemIndex + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", receivingPerson='" + receivingPerson + '\'' +
                ", warehouseReleaseDateFromLong=" + warehouseReleaseDateFromLong +
                ", warehouseReleaseDateToLong=" + warehouseReleaseDateToLong +
                ", warehouseReleasePerson='" + warehouseReleasePerson + '\'' +
                ", mpk='" + mpk + '\'' +
                ", budget='" + budget + '\'' +
                ", overdueDays=" + overdueDays +
                ", startPosition=" + startPosition +
                ", maxResult=" + maxResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderQueryParam that = (OrderQueryParam) o;

        if (orderDateFromLong != that.orderDateFromLong) return false;
        if (orderDateToLong != that.orderDateToLong) return false;
        if (warehouseReleaseDateFromLong != that.warehouseReleaseDateFromLong) return false;
        if (warehouseReleaseDateToLong != that.warehouseReleaseDateToLong) return false;
        if (overdueDays != that.overdueDays) return false;
        if (startPosition != that.startPosition) return false;
        if (maxResult != that.maxResult) return false;
        if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
        if (supplier != null ? !supplier.equals(that.supplier) : that.supplier != null) return false;
        if (purchaser != null ? !purchaser.equals(that.purchaser) : that.purchaser != null) return false;
        if (factory != null ? !factory.equals(that.factory) : that.factory != null) return false;
        if (suppliesGroup != null ? !suppliesGroup.equals(that.suppliesGroup) : that.suppliesGroup != null) return false;
        if (orderReference != null ? !orderReference.equals(that.orderReference) : that.orderReference != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (itemIndex != null ? !itemIndex.equals(that.itemIndex) : that.itemIndex != null) return false;
        if (itemDescription != null ? !itemDescription.equals(that.itemDescription) : that.itemDescription != null) return false;
        if (receivingPerson != null ? !receivingPerson.equals(that.receivingPerson) : that.receivingPerson != null) return false;
        if (warehouseReleasePerson != null ? !warehouseReleasePerson.equals(that.warehouseReleasePerson) : that.warehouseReleasePerson != null) return false;
        if (mpk != null ? !mpk.equals(that.mpk) : that.mpk != null) return false;
        return budget != null ? budget.equals(that.budget) : that.budget == null;

    }

    @Override
    public int hashCode() {
        int result = orderNumber != null ? orderNumber.hashCode() : 0;
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (purchaser != null ? purchaser.hashCode() : 0);
        result = 31 * result + (factory != null ? factory.hashCode() : 0);
        result = 31 * result + (suppliesGroup != null ? suppliesGroup.hashCode() : 0);
        result = 31 * result + (int) (orderDateFromLong ^ (orderDateFromLong >>> 32));
        result = 31 * result + (int) (orderDateToLong ^ (orderDateToLong >>> 32));
        result = 31 * result + (orderReference != null ? orderReference.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (itemIndex != null ? itemIndex.hashCode() : 0);
        result = 31 * result + (itemDescription != null ? itemDescription.hashCode() : 0);
        result = 31 * result + (receivingPerson != null ? receivingPerson.hashCode() : 0);
        result = 31 * result + (int) (warehouseReleaseDateFromLong ^ (warehouseReleaseDateFromLong >>> 32));
        result = 31 * result + (int) (warehouseReleaseDateToLong ^ (warehouseReleaseDateToLong >>> 32));
        result = 31 * result + (warehouseReleasePerson != null ? warehouseReleasePerson.hashCode() : 0);
        result = 31 * result + (mpk != null ? mpk.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + overdueDays;
        result = 31 * result + startPosition;
        result = 31 * result + maxResult;
        return result;
    }

    public void changeWildcardsInStringFieldsToSql() throws IllegalAccessException {
        Field[] allFields = this.getClass().getDeclaredFields();

        for (Field field : allFields) {
            if (field.getType().isAssignableFrom(String.class)) {
               // System.out.println("Field name: " + field.getName()+ " "+field.getType());
                String fromField = (String) field.get(this);
                if (fromField != null) {
                    field.set(this, fromField.replace('*', '%'));
                }


            }
        }
    }

}