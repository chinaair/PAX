package com.chinaair.webDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.chinaair.entity.TaxInvoiceIssue;

public class TaxInvoiceIssueDetailDto implements Serializable {
	
	private static final long serialVersionUID = 3807553011687278596L;

	private Long id;
	
	private String ticketNo;
	
	private Long taxInvoiceIssueId;
	
	private TaxInvoiceIssue taxInvoiceIssue;
	
	private String route;
	
	private Long quantity;
	
	private BigDecimal price;
	
	private BigDecimal amount;
	
	private Timestamp lastUpdate;
	
	private BigDecimal vndAmt;
	
	private BigDecimal vndPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Long getTaxInvoiceIssueId() {
		return taxInvoiceIssueId;
	}

	public void setTaxInvoiceIssueId(Long taxInvoiceIssueId) {
		this.taxInvoiceIssueId = taxInvoiceIssueId;
	}

	public TaxInvoiceIssue getTaxInvoiceIssue() {
		return taxInvoiceIssue;
	}

	public void setTaxInvoiceIssue(TaxInvoiceIssue taxInvoiceIssue) {
		this.taxInvoiceIssue = taxInvoiceIssue;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BigDecimal getVndAmt() {
		return vndAmt;
	}

	public void setVndAmt(BigDecimal vndAmt) {
		this.vndAmt = vndAmt;
	}

	public BigDecimal getVndPrice() {
		return vndPrice;
	}

	public void setVndPrice(BigDecimal vndPrice) {
		this.vndPrice = vndPrice;
	}

}
