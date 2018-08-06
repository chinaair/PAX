package com.chinaair.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="INVOICE_STOCK_DETAIL")
public class InvoiceStockDetail implements Serializable {
	
	private static final long serialVersionUID = 3412693651976981805L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="INVOICE_STOCK_ID")
	private InvoiceStock invoiceStock;
	
	@Column(name="INVOICE_STOCK_NO", nullable = false)
	private String invoiceStockNo;
	
	/**
	 * 0: unused
	 * 1: used
	 * 2: voided
	 */
	@Column(name="STATUS", nullable = false)
	private String status;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastupdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceStockNo() {
		return invoiceStockNo;
	}

	public void setInvoiceStockNo(String invoiceStockNo) {
		this.invoiceStockNo = invoiceStockNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public InvoiceStock getInvoiceStock() {
		return invoiceStock;
	}

	public void setInvoiceStock(InvoiceStock invoiceStock) {
		this.invoiceStock = invoiceStock;
	}
}
