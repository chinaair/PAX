package com.chinaair.webDto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxInvReportDto implements Serializable {
	
	private static final long serialVersionUID = 3908625423825605330L;

	private int no;
	
	private String invoiceTemplateCode;
	
	private String invoiceCode;
	
	private String invoiceNumber;
	
	private String issueDate;
	
	private String companyName;
	
	private String vatCode;
	
	private String type;
	
	private BigDecimal amountVnd;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getInvoiceTemplateCode() {
		return invoiceTemplateCode;
	}

	public void setInvoiceTemplateCode(String invoiceTemplateCode) {
		this.invoiceTemplateCode = invoiceTemplateCode;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmountVnd() {
		return amountVnd;
	}

	public void setAmountVnd(BigDecimal amountVnd) {
		this.amountVnd = amountVnd;
	}

}
