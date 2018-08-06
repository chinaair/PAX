package com.chinaair.dto;

import java.io.Serializable;
import java.util.Date;

public class TaxInvoiceIssueSearchDto implements Serializable {
	
	private static final long serialVersionUID = -5205907759859772877L;

	private Date startDate;
	
	private Date endDate;
	
	private String referNumber;
	
	private String vatCode;
	
	private String status;
	
	private boolean cargo;
	
	private String ticketNo;
	
	private String companyName;
	
	private String taxInvNo;
	
	private String sortField;
	
	private String sortOrder;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReferNumber() {
		return referNumber;
	}

	public void setReferNumber(String referNumber) {
		this.referNumber = referNumber;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isCargo() {
		return cargo;
	}

	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}

	public Long getReferNumberAsNumber() {
		try {
			return new Long(referNumber);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTaxInvNo() {
		return taxInvNo;
	}

	public void setTaxInvNo(String taxInvNo) {
		this.taxInvNo = taxInvNo;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
