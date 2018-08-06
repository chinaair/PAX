package com.chinaair.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TicketIssueDetailDto implements Serializable {
	
	private static final long serialVersionUID = -3848676220338696587L;

	private Long id;
	
	private String ticketNo;
	
	private String route;
	
	private Long quantity;
	
	private BigDecimal vndPrice;
	
	private BigDecimal vndAmount;

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

	public BigDecimal getVndPrice() {
		return vndPrice;
	}

	public void setVndPrice(BigDecimal vndPrice) {
		this.vndPrice = vndPrice;
	}

	public BigDecimal getVndAmount() {
		return vndAmount;
	}

	public void setVndAmount(BigDecimal vndAmount) {
		this.vndAmount = vndAmount;
	}

}
