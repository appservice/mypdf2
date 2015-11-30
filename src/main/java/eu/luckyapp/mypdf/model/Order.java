package eu.luckyapp.mypdf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name = "PURCHASE_ORDER")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = Order.FIND_ALL, query = "SELECT o FROM Order  o "),
		@NamedQuery(name = Order.FIND_BY_NUMBER, query = "SELECT o FROM Order  o where o.number =:orderNumber") })
public class Order implements Serializable {
	public static final String FIND_ALL = "findAll";
	public static final String FIND_BY_NUMBER = "findByNumber";

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@TableGenerator(name = "OrderIdTable", table = "ID_ORDER_SEQUENCE", pkColumnName = "PK", pkColumnValue = "ORDER_PK", initialValue = 0, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OrderIdTable")
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@Column(unique = true)
	private String number;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;

	@Column
	private String purchaser;

	@Column
	private String supplier;

	@Column(length = 4)
	private String factory;

	@Column(length = 1024)
	private String orderReference;

	@Column // (length = 4)
	private String suppliesGroup;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
	// mappedBy="myOrder"
	// ,
	@JsonManagedReference
	private List<Item> items = new ArrayList<>();

	@Override
	public String toString() {
		return "Order [id=" + id + ", version=" + version + ", number=" + number + ", date=" + date + ", purchaser="
				+ purchaser + ", supplier=" + supplier + ", factory=" + factory + ", orderReference=" + orderReference
				+ ", suppliesGroup=" + suppliesGroup
				+ /* ", items=" + items + */"]";
	}

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

	private static final long serialVersionUID = 1L;

	public Order() {
		super();
	}

	public String setNumber() {
		return this.number;
	}

	public void geNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPurchaser() {
		return this.purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getFactory() {
		return this.factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public String getSuppliesGroup() {
		return suppliesGroup;
	}

	public void setSuppliesGroup(String suppliesGroup) {
		this.suppliesGroup = suppliesGroup;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String lier) {
		this.supplier = supplier;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Order other = (Order) obj;
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

	
}
