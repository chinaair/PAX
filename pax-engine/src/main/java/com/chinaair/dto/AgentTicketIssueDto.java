package com.chinaair.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.chinaair.entity.Agent;
import com.chinaair.entity.TicketIssue;

public class AgentTicketIssueDto implements Serializable {
	
	private static final long serialVersionUID = -881630298438582545L;

	private static final DecimalFormat usd = new DecimalFormat("#,##0.00");
	
	private static final DecimalFormat vnd = new DecimalFormat("#,###");

	private Agent agent;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private List<TicketIssue> ticketIssueList;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public BigDecimal getTotalAmtUsd() {
		return totalAmtUsd;
	}
	
	public String getTotalAmtUsdStr() {
		if(totalAmtUsd != null) {
			return usd.format(totalAmtUsd);
		}
		return "0";
	}

	public void setTotalAmtUsd(BigDecimal totalAmtUsd) {
		this.totalAmtUsd = totalAmtUsd;
	}

	public BigDecimal getTotalAmtVnd() {
		return totalAmtVnd;
	}
	
	public String getTotalAmtVndStr() {
		if(totalAmtVnd != null) {
			return vnd.format(totalAmtVnd);
		}
		return "0";
	}

	public void setTotalAmtVnd(BigDecimal totalAmtVnd) {
		this.totalAmtVnd = totalAmtVnd;
	}

	public List<TicketIssue> getTicketIssueList() {
		return ticketIssueList;
	}

	public void setTicketIssueList(List<TicketIssue> ticketIssueList) {
		this.ticketIssueList = ticketIssueList;
	}
	
	public String getAgentCode() {
		if(agent != null) {
			return agent.getCode();
		}
		return null;
	}
	
	public String getAgentName() {
		if(agent != null) {
			return agent.getName();
		}
		return null;
	}

}
