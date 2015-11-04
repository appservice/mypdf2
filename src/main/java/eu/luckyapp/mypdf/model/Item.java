package eu.luckyapp.mypdf.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@Column(length = 18)
	private String index;

	@Column
	private String name;

	@Column
	private String mpk;

	@Column(length = 16)
	private String budget;

	@Column
	private boolean isDispatched;

	@Column
	private double amount;

	@Column(length = 3)
	private String unit;

	@Column
	private Double price;

	@Column(length = 3)
	private String currency;

	@Column
	@Temporal(TemporalType.DATE)
	private Date expectedDeliveryDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date warehouseReleaseDate;

	@Column
	private String warehouseReleasePerson;

	@Column
	private boolean isReleased;

	@Column
	private int positionInOrder;

	@Column
	private double partialDeliveryGoodsAmount;

	@Column(length = 1024)
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMpk() {
		return mpk;
	}

	public void setMpk(String mpk) {
		this.mpk = mpk;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public boolean isDispatched() {
		return isDispatched;
	}

	public void setDispatched(boolean isDispatched) {
		this.isDispatched = isDispatched;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getWarehouseReleaseDate() {
		return warehouseReleaseDate;
	}

	public void setWarehouseReleaseDate(Date warehouseReleaseDate) {
		this.warehouseReleaseDate = warehouseReleaseDate;
	}

	public String getWarehouseReleasePerson() {
		return warehouseReleasePerson;
	}

	public void setWarehouseReleasePerson(String warehouseReleasePerson) {
		this.warehouseReleasePerson = warehouseReleasePerson;
	}

	public boolean isReleased() {
		return isReleased;
	}

	public void setReleased(boolean isReleased) {
		this.isReleased = isReleased;
	}

	public int getPositionInOrder() {
		return positionInOrder;
	}

	public void setPositionInOrder(int positionInOrder) {
		this.positionInOrder = positionInOrder;
	}

	public double getPartialDeliveryGoodsAmount() {
		return partialDeliveryGoodsAmount;
	}

	public void setPartialDeliveryGoodsAmount(double partialDeliveryGoodsAmount) {
		this.partialDeliveryGoodsAmount = partialDeliveryGoodsAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
