package com.chinaair.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity(name="TICKET_ISSUE_DETAIL")
public class TicketIssueDetail implements Serializable {

	private static final long serialVersionUID = 1122796517955938489L;
	
	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="TICKET_NO", nullable = false)
	private String ticketNo;
	
	@Column(name="TICKET_ISSUE_ID", nullable = false)
	private Long ticketIssueId;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TICKET_ISSUE_ID", insertable=false, updatable=false)
	private TicketIssue ticketIssue;
	
	@Column(name="ROUTE")
	private String route;
	
	@Column(name="QUANTITY", nullable = false)
	private Long quantity;
	
	@Column(name="PRICE", nullable = false)
	private BigDecimal price;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
