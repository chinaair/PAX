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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TICKET_ISSUE")
public class TicketIssue implements Serializable {
	
	private static final long serialVersionUID = 5760668219804774494L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ISSUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	@Column(name="ROE", nullable = false)
	private BigDecimal roe;
	
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
	
	@Column(name="REPORTDATE")
	@Temporal(TemporalType.DATE)
	private Date reportDate;
	
	//input employee
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;
	
	/**
	 * 0: has no tax invoice
	 * 1: has tax invoice
	 * 2: voided
	 */
	@Column(name="STATUS", nullable = false)
	private String status;
	
	@Column(name="AMT_USD")
	private BigDecimal amount_usd;
	
	@OneToMany(targetEntity=TicketIssueDetail.class, mappedBy="ticketIssue")
	@OrderBy("id")
	private List<TicketIssueDetail> ticketIssueDetail;

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

	public BigDecimal getRoe() {
		return roe;
	}

	public void setRoe(BigDecimal roe) {
		this.roe = roe;
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

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
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

	public BigDecimal getAmount_usd() {
		return amount_usd;
	}

	public void setAmount_usd(BigDecimal amount_usd) {
		this.amount_usd = amount_usd;
	}

	public List<TicketIssueDetail> getTicketIssueDetail() {
		return ticketIssueDetail;
	}

	public void setTicketIssueDetail(List<TicketIssueDetail> ticketIssueDetail) {
		this.ticketIssueDetail = ticketIssueDetail;
	}

}
