package eu.luckyapp.mypdf.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.Override;

@Entity
@Table(name = "TABLE_VIEW")
@XmlRootElement
public class TableView implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -4820835501727776045L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
   @Version
   @Column(name = "version")
   private int version;

   @Column
   private String nbOfOrder;

   @Column
   private String supplier;

   @Column(length = 4)
   private String factory;

   @Column
   private String orderingPerson;

   @Column(length = 18)
   private String goodsIndex;

   @Column(length = 512)
   private String goodsName;

   @Column(length = 10)
   private String mpk;

   @Column(length = 16)
   private String budget;

   @Column
   private boolean isDispatched;

   @Column
   private double goodsAmount;

   @Column(length = 3)
   private String goodsUnit;

   @Column
   private Double goodsPrice;

   @Column(length = 3)
   private String goodsPriceCurrency;

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
   private String goodsIdOnOrder;

   @Column
   private boolean isReleased;

   @Column
   private double partialDeliveryGoodsAmount;

   @Column(length = 1024)
   private String description;

   @Column
   private String orderReference;

   @Column(name = "ORDER_DATE")
   @Temporal(TemporalType.DATE)
   private Date orderDate;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof TableView))
      {
         return false;
      }
      TableView other = (TableView) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   public String getOrderId()
   {
      return nbOfOrder;
   }

   public void setOrderId(String orderId)
   {
      this.nbOfOrder = orderId;
   }

   public String getSupplier()
   {
      return supplier;
   }

   public void setSupplier(String supplier)
   {
      this.supplier = supplier;
   }

   public String getFactory()
   {
      return factory;
   }

   public void setFactory(String factory)
   {
      this.factory = factory;
   }

   public String getOrderedPerson()
   {
      return orderingPerson;
   }

   public void setOrderedPerson(String orderedPerson)
   {
      this.orderingPerson = orderedPerson;
   }

   public String getIndex()
   {
      return goodsIndex;
   }

   public void setIndex(String index)
   {
      this.goodsIndex = index;
   }

   public String getGoodsName()
   {
      return goodsName;
   }

   public void setGoodsName(String goodsName)
   {
      this.goodsName = goodsName;
   }

   public String getMpk()
   {
      return mpk;
   }

   public void setMpk(String mpk)
   {
      this.mpk = mpk;
   }

   public String getBudget()
   {
      return budget;
   }

   public void setBudget(String budget)
   {
      this.budget = budget;
   }

   public boolean isIsDispatched()
   {
      return isDispatched;
   }

   public void setIsDispatched(boolean isDispatched)
   {
      this.isDispatched = isDispatched;
   }

   public double getGoodsAmount()
   {
      return goodsAmount;
   }

   public void setGoodsAmount(double goodsAmount)
   {
      this.goodsAmount = goodsAmount;
   }

   public Date getExpectedDeliveryDate()
   {
      return expectedDeliveryDate;
   }

   public void setExpectedDeliveryDate(Date expectedDeliveryDate)
   {
      this.expectedDeliveryDate = expectedDeliveryDate;
   }

   public Date getDeliveryDate()
   {
      return deliveryDate;
   }

   public void setDeliveryDate(Date deliveryDate)
   {
      this.deliveryDate = deliveryDate;
   }

   public Date getWarehouseReleaseDate()
   {
      return warehouseReleaseDate;
   }

   public void setWarehouseReleaseDate(Date warehouseReleaseDate)
   {
      this.warehouseReleaseDate = warehouseReleaseDate;
   }

   public String getWarehouseReleasePerson()
   {
	   if(warehouseReleasePerson==null)
		   return "";
      return warehouseReleasePerson;
   }

   public void setWarehouseReleasePerson(String warehouseReleasePerson)
   {
      this.warehouseReleasePerson = warehouseReleasePerson;
   }

   public String getGoodsIdOnOrder()
   {
      return goodsIdOnOrder;
   }

   public void setGoodsIdOnOrder(String goodsIdOnOrder)
   {
      this.goodsIdOnOrder = goodsIdOnOrder;
   }

   public boolean isIsReleased()
   {
      return isReleased;
   }

   public void setIsReleased(boolean isReleased)
   {
      this.isReleased = isReleased;
   }

   public double getPartialDeliveryGoodsAmount()
   {
      return partialDeliveryGoodsAmount;
   }

   public void setPartialDeliveryGoodsAmount(double partialDeliveryGoodsAmount)
   {
      this.partialDeliveryGoodsAmount = partialDeliveryGoodsAmount;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getGoodsUnit()
   {
      return goodsUnit;
   }

   public void setGoodsUnit(String goodsUnit)
   {
      this.goodsUnit = goodsUnit;
   }

   public Double getGoodsPrice()
   {
      return goodsPrice;
   }

   public void setGoodsPrice(Double goodsPrice)
   {
      this.goodsPrice = goodsPrice;
   }

   public String getGoodsPriceCurrency()
   {
      return goodsPriceCurrency;
   }

   public void setGoodsPriceCurrency(String goodsPriceCurrency)
   {
      this.goodsPriceCurrency = goodsPriceCurrency;
   }

   public String getOrderReference()
   {
      return orderReference;
   }

   public void setOrderReference(String orderReference)
   {
      this.orderReference = orderReference;
   }

   public Date getOrderDate()
   {
      return orderDate;
   }

   public void setOrderDate(Date orderDate)
   {
      this.orderDate = orderDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (nbOfOrder != null && !nbOfOrder.trim().isEmpty())
         result += "nbOfOrder: " + nbOfOrder;
      if (supplier != null && !supplier.trim().isEmpty())
         result += ", supplier: " + supplier;
      if (factory != null && !factory.trim().isEmpty())
         result += ", factory: " + factory;
      if (orderingPerson != null && !orderingPerson.trim().isEmpty())
         result += ", orderingPerson: " + orderingPerson;
      if (goodsIndex != null && !goodsIndex.trim().isEmpty())
         result += ", goodsIndex: " + goodsIndex;
      if (goodsName != null && !goodsName.trim().isEmpty())
         result += ", goodsName: " + goodsName;
      if (mpk != null && !mpk.trim().isEmpty())
         result += ", mpk: " + mpk;
      if (budget != null && !budget.trim().isEmpty())
         result += ", budget: " + budget;
      result += ", isDispatched: " + isDispatched;
      result += ", goodsAmount: " + goodsAmount;
      if (goodsUnit != null && !goodsUnit.trim().isEmpty())
         result += ", goodsUnit: " + goodsUnit;
      if (goodsPrice != null)
         result += ", goodsPrice: " + goodsPrice;
      if (goodsPriceCurrency != null && !goodsPriceCurrency.trim().isEmpty())
         result += ", goodsPriceCurrency: " + goodsPriceCurrency;
      if (warehouseReleasePerson != null
            && !warehouseReleasePerson.trim().isEmpty())
         result += ", warehouseReleasePerson: " + warehouseReleasePerson;
      if (goodsIdOnOrder != null && !goodsIdOnOrder.trim().isEmpty())
         result += ", goodsIdOnOrder: " + goodsIdOnOrder;
      result += ", isReleased: " + isReleased;
      result += ", partialDeliveryGoodsAmount: " + partialDeliveryGoodsAmount;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (orderReference != null && !orderReference.trim().isEmpty())
         result += ", orderReference: " + orderReference;
      if (orderDate != null )
          result += ", orderDate: " + orderDate.toString();
      if(expectedDeliveryDate!=null)
    	  result+=" ,expectedDeliveryDate: "+expectedDeliveryDate.toString();
      return result;
   }
}