package com.chinaair.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="INVOICE_STOCK")
public class InvoiceStock implements Serializable {
	
	private static final long serialVersionUID = 4539856495899785929L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CREATE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Column(name="INVOICE_CODE_ID", nullable = false)
	private Long invoiceCodeId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="INVOICE_CODE_ID", insertable=false, updatable=false)
	private TaxInvoiceCode taxInvoiceCode;
	
	@Column(name="PREFIX", nullable = false)
	private String prefix;
	
	@Column(name="STARTNO", nullable = false)
	private Long startNo;
	
	@Column(name="QUANTITY", nullable = false)
	public Long quantity;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	public Timestamp lastUpdate;
	
	@Column(name="CARGO")
	private String cargo;
	
	@OneToMany(targetEntity=InvoiceStockDetail.class, mappedBy="invoiceStock",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@OrderBy("invoiceStockNo")
	private List<InvoiceStockDetail> invoiceStockDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getInvoiceCodeId() {
		return invoiceCodeId;
	}

	public void setInvoiceCodeId(Long invoiceCodeId) {
		this.invoiceCodeId = invoiceCodeId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getStartNo() {
		return startNo;
	}

	public void setStartNo(Long startNo) {
		this.startNo = startNo;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public TaxInvoiceCode getTaxInvoiceCode() {
		return taxInvoiceCode;
	}

	public void setTaxInvoiceCode(TaxInvoiceCode taxInvoiceCode) {
		this.taxInvoiceCode = taxInvoiceCode;
	}

	public List<InvoiceStockDetail> getInvoiceStockDetails() {
		return invoiceStockDetails;
	}

	public void setInvoiceStockDetails(List<InvoiceStockDetail> invoiceStockDetails) {
		this.invoiceStockDetails = invoiceStockDetails;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}
