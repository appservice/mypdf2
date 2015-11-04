package eu.luckyapp.mypdf.model;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Order
 *
 */
@Entity
@Table(name = "ORDER")
@XmlRootElement
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@Column(unique=true)
	private String number;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column
	private String purchaser;

	@Column
	private String supplier;

	@Column(length = 4)
	private String factory;

	@Column
	private String orderReference;

	@Column(length = 3)
	private String suppliesGroup;
	
	@OneToMany(mappedBy="order")
	private List<Item> items;

	

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

	public String getName() {
		return this.number;
	}

	public void setName(String name) {
		this.number = name;
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

	public String getDeliver() {
		return this.supplier;
	}

	public void setDeliver(String deliver) {
		this.supplier = deliver;
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

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
