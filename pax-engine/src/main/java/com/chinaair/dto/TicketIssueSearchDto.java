package com.chinaair.dto;

import java.io.Serializable;
import java.util.Date;

public class TicketIssueSearchDto implements Serializable {
	
	private static final long serialVersionUID = -7272323178682674306L;

	private Date startDate;
	
	private Date endDate;
	
	private Long referNumber;
	
	private Long agentId;
	
	private String status;
	
	private String ticketNo;
	
	private String route;
	
	private String inputSD;

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

	public Long getReferNumber() {
		return referNumber;
	}

	public void setReferNumber(Long referNumber) {
		this.referNumber = referNumber;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getInputSD() {
		return inputSD;
	}

	public void setInputSD(String inputSD) {
		this.inputSD = inputSD;
	}

}
