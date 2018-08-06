package com.chinaair.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TAX_INVOICE_ISSUE")
public class TaxInvoiceIssue implements Serializable {
	
	private static final long serialVersionUID = -64664719858355976L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name="ISSUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date issueDate;
	
	@Column(name="TICKET_ISSUE_ID")
	private Long ticketIssueId;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TICKET_ISSUE_ID", insertable=false, updatable=false)
	private TicketIssue ticketIssue;
	
	@Column(name="INVOICE_STOCK_DETAIL_ID", nullable = false)
	private Long invoiceStockDetailId;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="INVOICE_STOCK_DETAIL_ID", insertable=false, updatable=false)
	private InvoiceStockDetail invoiceStockDetail;

	@Column(name="COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name="VAT_CODE", nullable = false)
	private String vatCode;
	
	@Column(name="ADDRESS", nullable = false)
	private String address;
	
	/**
	 * 0: Cash
	 * 1: Credit card
	 * 2: Bank transfer
	 * 3: Account receivable
	 */
	@Column(name="PAYMENT_TYPE", nullable = false)
	private String paymentType;
	
	@Column(name="AMT_USD", nullable = false)
	public BigDecimal amtUsd;
	
	@Column(name="RATE_ID")
	private Long rateId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RATE_ID", insertable=false, updatable=false)
	private Rate rate;
	
	@Column(name="CREATE_EMP_ID")
	private Long createEmpId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CREATE_EMP_ID", insertable=false, updatable=false)
	private Employee createEmp;
	
	/**
	 * 0: normal
	 * 2: voided
	 * 3: stored
	 */
	@Column(name="STATUS", nullable = false)
	public String status;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;
	
	/**
	 * 0: not round up
	 * 1: round up
	 */
	@Column(name="ROUNDUP", nullable = false)
	private String roundUp;
	
	@Column(name="CARGO")
	private String cargo;
	
	@Column(name="USE_MANUAL_RATE")
	private String useManualRate;
	
	@Column(name="MANUAL_RATE")
	private BigDecimal manualRate;
	
	@OneToMany(targetEntity=TaxInvoiceIssueDetail.class, mappedBy="taxInvoiceIssue",fetch=FetchType.LAZY)
	@OrderBy("dispOrder")
	private List<TaxInvoiceIssueDetail> taxInvoiceIssueDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Long getTicketIssueId() {
		return ticketIssueId;
	}

	public void setTicketIssueId(Long ticketIssueId) {
		this.ticketIssueId = ticketIssueId;
	}

	public TicketIssue getTicketIssue() {
		return ticketIssue;
	}

	public void setTicketIssue(TicketIssue ticketIssue) {
		this.ticketIssue = ticketIssue;
	}

	public Long getInvoiceStockDetailId() {
		return invoiceStockDetailId;
	}

	public void setInvoiceStockDetailId(Long invoiceStockDetailId) {
		this.invoiceStockDetailId = invoiceStockDetailId;
	}

	public InvoiceStockDetail getInvoiceStockDetail() {
		return invoiceStockDetail;
	}

	public void setInvoiceStockDetail(InvoiceStockDetail invoiceStockDetail) {
		this.invoiceStockDetail = invoiceStockDetail;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmtUsd() {
		return amtUsd;
	}

	public void setAmtUsd(BigDecimal amtUsd) {
		this.amtUsd = amtUsd;
	}

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
	}

	public Long getCreateEmpId() {
		return createEmpId;
	}

	public void setCreateEmpId(Long createEmpId) {
		this.createEmpId = createEmpId;
	}

	public Employee getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(Employee createEmp) {
		this.createEmp = createEmp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getRoundUp() {
		return roundUp;
	}

	public void setRoundUp(String roundUp) {
		this.roundUp = roundUp;
	}

	public List<TaxInvoiceIssueDetail> getTaxInvoiceIssueDetail() {
		return taxInvoiceIssueDetail;
	}

	public void setTaxInvoiceIssueDetail(
			List<TaxInvoiceIssueDetail> taxInvoiceIssueDetail) {
		this.taxInvoiceIssueDetail = taxInvoiceIssueDetail;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public String getFormattedTicketIssueId() {
		if(ticketIssueId == null) {
			return "";
		}
		return String.format("%06d", ticketIssueId);
	}

	public String getUseManualRate() {
		return useManualRate;
	}

	public void setUseManualRate(String useManualRate) {
		this.useManualRate = useManualRate;
	}

	public BigDecimal getManualRate() {
		return manualRate;
	}

	public void setManualRate(BigDecimal manualRate) {
		this.manualRate = manualRate;
	}
	
}
