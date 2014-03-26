package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Agent;
import com.chinaair.entity.TicketIssueDetail;

@SessionScoped
@Named("ticketIssueBean")
public class TicketIssueBean implements Serializable {

	private static final long serialVersionUID = -1953274038082422040L;
	
	private Date issueDate;
	
	private BigDecimal roe;
	
	private String refNo;
	
	private String paymentType;
	
	private Agent selectedAgent;
	
	private String displayAgentName;
	
	private List<TicketIssueDetail> ticketIssueDetails;
	
	private TicketIssueDetail selectedTicketIssueDetail;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	public void init() {
		
	}
	
	public void chooseAgent() {
		RequestContext.getCurrentInstance().openDialog("selectAgent");
	}
	
	public void onAgentChosen(SelectEvent event) {
		selectedAgent = (Agent)event.getObject();
		if(selectedAgent != null) {
			displayAgentName = selectedAgent.getName();
		}
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

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
	}

	public String getDisplayAgentName() {
		return displayAgentName;
	}

	public void setDisplayAgentName(String displayAgentName) {
		this.displayAgentName = displayAgentName;
	}

	public List<TicketIssueDetail> getTicketIssueDetails() {
		return ticketIssueDetails;
	}

	public void setTicketIssueDetails(List<TicketIssueDetail> ticketIssueDetails) {
		this.ticketIssueDetails = ticketIssueDetails;
	}

	public TicketIssueDetail getSelectedTicketIssueDetail() {
		return selectedTicketIssueDetail;
	}

	public void setSelectedTicketIssueDetail(
			TicketIssueDetail selectedTicketIssueDetail) {
		this.selectedTicketIssueDetail = selectedTicketIssueDetail;
	}

	public BigDecimal getTotalAmtUsd() {
		return totalAmtUsd;
	}

	public void setTotalAmtUsd(BigDecimal totalAmtUsd) {
		this.totalAmtUsd = totalAmtUsd;
	}

	public BigDecimal getTotalAmtVnd() {
		return totalAmtVnd;
	}

	public void setTotalAmtVnd(BigDecimal totalAmtVnd) {
		this.totalAmtVnd = totalAmtVnd;
	}

}
