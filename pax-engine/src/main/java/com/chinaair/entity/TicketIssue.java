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
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TICKET_ISSUE")
public class TicketIssue implements Serializable {
	
	private static final long serialVersionUID = 5760668219804774494L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name="ISSUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date issueDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RATE_ID", insertable=true, updatable=true)
	private Rate rate;
	
	/**
	 * 0: Cash
	 * 1: Credit card
	 * 2: Bank transfer
	 * 3: Account receivable
	 */
	@Column(name="PAYMENT_TYPE", nullable = false)
	private String paymentType;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="AGENT_ID", insertable=true, updatable=true)
	private Agent agent;
	
	@Column(name="TAX_INVOICE_ID")
	private Long taxInvoiceId;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TAX_INVOICE_ID", insertable=false, updatable=false)
	private TaxInvoiceIssue taxInvoiceIssue;
	
	@Column(name="INPUT_EMP")
	private Long inputEmpId;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="INPUT_EMP", insertable=false, updatable=false)
	private Employee inputEmp;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;
	
	/**
	 * 0: has no tax invoice
	 * 1: has tax invoice
	 * 2: voided
	 * 3: save temp
	 */
	@Column(name="STATUS", nullable = false)
	public String status;
	
	@Column(name="AMOUNT_USD")
	public BigDecimal amountUsd;
	
	@Transient
	public BigDecimal amountVnd;
	
	@Column(name="MODIFYDATETIME")
	private Timestamp modifyDatetime;
	
	@Column(name="MODIFY_EMP")
	private Long modifyEmpId;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="MODIFY_EMP", insertable=false, updatable=false)
	private Employee modifyEmp;
	
	@OneToMany(targetEntity=TicketIssueDetail.class, mappedBy="ticketIssue",fetch=FetchType.LAZY)
	@OrderBy("dispOrder")
	private List<TicketIssueDetail> ticketIssueDetail;
	
	@Column(name="REPORTED_DATE")
	@Temporal(TemporalType.DATE)
	private Date reportedDate;
	
	@Column(name="RETAIL_CUSTNAME")
	private String retailCustomerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFormattedId() {
		return String.format("%06d", id);
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Long getTaxInvoiceId() {
		return taxInvoiceId;
	}

	public void setTaxInvoiceId(Long taxInvoiceId) {
		this.taxInvoiceId = taxInvoiceId;
	}

	public TaxInvoiceIssue getTaxInvoiceIssue() {
		return taxInvoiceIssue;
	}

	public void setTaxInvoiceIssue(TaxInvoiceIssue taxInvoiceIssue) {
		this.taxInvoiceIssue = taxInvoiceIssue;
	}

	public Long getInputEmpId() {
		return inputEmpId;
	}

	public void setInputEmpId(Long inputEmpId) {
		this.inputEmpId = inputEmpId;
	}

	public Employee getInputEmp() {
		return inputEmp;
	}

	public void setInputEmp(Employee inputEmp) {
		this.inputEmp = inputEmp;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmountUsd() {
		return amountUsd;
	}

	public void setAmountUsd(BigDecimal amountUsd) {
		this.amountUsd = amountUsd;
	}
	
	public BigDecimal getAmountVnd() {
		return amountVnd;
	}

	public void setAmountVnd(BigDecimal amountVnd) {
		this.amountVnd = amountVnd;
	}

	public List<TicketIssueDetail> getTicketIssueDetail() {
		return ticketIssueDetail;
	}

	public void setTicketIssueDetail(List<TicketIssueDetail> ticketIssueDetail) {
		this.ticketIssueDetail = ticketIssueDetail;
	}

	public Timestamp getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(Timestamp modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Long getModifyEmpId() {
		return modifyEmpId;
	}

	public void setModifyEmpId(Long modifyEmpId) {
		this.modifyEmpId = modifyEmpId;
	}

	public Employee getModifyEmp() {
		return modifyEmp;
	}

	public void setModifyEmp(Employee modifyEmp) {
		this.modifyEmp = modifyEmp;
	}

	public Date getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	
	public BigDecimal getRoe() {
		if(rate != null) {
			return rate.getRate();
		}
		return new BigDecimal(0);
	}

	public String getRetailCustomerName() {
		return retailCustomerName;
	}

	public void setRetailCustomerName(String retailCustomerName) {
		this.retailCustomerName = retailCustomerName;
	}

}
