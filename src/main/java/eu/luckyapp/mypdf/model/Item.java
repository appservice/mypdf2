package eu.luckyapp.mypdf.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@Table(name = "item")
@XmlRootElement
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@TableGenerator(name="ItemIdTable",table="ID_ORDER_SEQUENCE",pkColumnName="PK",pkColumnValue="ITEM_PK",initialValue=0,allocationSize=1)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="ItemIdTable")
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JsonBackReference
	@JoinColumn(name = "ORDER_ID",nullable=false)
	private Order order;

	@Column(name="index",length = 18)
	private String itemIndex;
	
	@Column
	private String receivingPerson;

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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date expectedDeliveryDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	private Date deliveryDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(shape=JsonFormat.Shape, pattern="yyyy-MM-dd HH:mm")
	private Date warehouseReleaseDate;

	//osoba odbierająca z magazynu
	@Column
	private String warehouseReleasePerson;
	
	//osoba wydająca
	@Column(length=40)
	private String realisingPerson;

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

	//@XmlTransient
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order myOrder) {
		this.order = myOrder;
	}



	/**
	 * @return the itemIndex
	 */
	public String getItemIndex() {
		return itemIndex;
	}

	/**
	 * @param itemIndex the itemIndex to set
	 */
	public void setItemIndex(String itemIndex) {
		this.itemIndex = itemIndex;
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
	
	/**
	 * @return the realisingPerson
	 */
	public String getRealisingPerson() {
		return realisingPerson;
	}

	/**
	 * @param realisingPerson the realisingPerson to set
	 */
	public void setRealisingPerson(String realisingPerson) {
		this.realisingPerson = realisingPerson;
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
	
	

	/**
	 * @return the receivingPerson
	 */
	public String getReceivingPerson() {
		return receivingPerson;
	}

	/**
	 * @param receivingPerson the receivingPerson to set
	 */
	public void setReceivingPerson(String receivingPerson) {
		this.receivingPerson = receivingPerson;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", version=" + version + ", order=" /*+ order*/ + ", index=" + itemIndex + ", name=" + name;
			/*	+ ", mpk=" + mpk + ", budget=" + budget + ", isDispatched=" + isDispatched + ", amount=" + amount
				+ ", unit=" + unit + ", price=" + price + ", currency=" + currency + ", expectedDeliveryDate="
				+ expectedDeliveryDate + ", deliveryDate=" + deliveryDate + ", warehouseReleaseDate="
				+ warehouseReleaseDate + ", warehouseReleasePerson=" + warehouseReleasePerson + ", isReleased="
				+ isReleased + ", positionInOrder=" + positionInOrder + ", partialDeliveryGoodsAmount="
				+ partialDeliveryGoodsAmount + ", description=" + description + "]";*/
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((budget == null) ? 0 : budget.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((deliveryDate == null) ? 0 : deliveryDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((expectedDeliveryDate == null) ? 0 : expectedDeliveryDate.hashCode());
		result = prime * result + ((itemIndex == null) ? 0 : itemIndex.hashCode());
		result = prime * result + (isDispatched ? 1231 : 1237);
		result = prime * result + (isReleased ? 1231 : 1237);
		result = prime * result + ((mpk == null) ? 0 : mpk.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		temp = Double.doubleToLongBits(partialDeliveryGoodsAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + positionInOrder;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((receivingPerson == null) ? 0 : receivingPerson.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((warehouseReleaseDate == null) ? 0 : warehouseReleaseDate.hashCode());
		result = prime * result + ((warehouseReleasePerson == null) ? 0 : warehouseReleasePerson.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (budget == null) {
			if (other.budget != null)
				return false;
		} else if (!budget.equals(other.budget))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (expectedDeliveryDate == null) {
			if (other.expectedDeliveryDate != null)
				return false;
		} else if (!expectedDeliveryDate.equals(other.expectedDeliveryDate))
			return false;
		if (itemIndex == null) {
			if (other.itemIndex != null)
				return false;
		} else if (!itemIndex.equals(other.itemIndex))
			return false;
		if (isDispatched != other.isDispatched)
			return false;
		if (isReleased != other.isReleased)
			return false;
		if (mpk == null) {
			if (other.mpk != null)
				return false;
		} else if (!mpk.equals(other.mpk))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (Double.doubleToLongBits(partialDeliveryGoodsAmount) != Double
				.doubleToLongBits(other.partialDeliveryGoodsAmount))
			return false;
		if (positionInOrder != other.positionInOrder)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (receivingPerson == null) {
			if (other.receivingPerson != null)
				return false;
		} else if (!receivingPerson.equals(other.receivingPerson))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (warehouseReleaseDate == null) {
			if (other.warehouseReleaseDate != null)
				return false;
		} else if (!warehouseReleaseDate.equals(other.warehouseReleaseDate))
			return false;
		if (warehouseReleasePerson == null) {
			if (other.warehouseReleasePerson != null)
				return false;
		} else if (!warehouseReleasePerson.equals(other.warehouseReleasePerson))
			return false;
		return true;
	}

}
