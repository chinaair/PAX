package com.chinaair.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.chinaair.entity.Agent;

public class AgentSummaryDto implements Serializable {
	
	private static final long serialVersionUID = -7909395402026364147L;
	private Agent agent;
	private List<InvoiceSummary> invoiceSummary;
	private BigDecimal summaryAmtUsd;
	private BigDecimal summaryAmtVnd;
	
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public BigDecimal getSummaryAmtUsd() {
		return summaryAmtUsd;
	}
	public void setSummaryAmtUsd(BigDecimal summaryAmtUsd) {
		this.summaryAmtUsd = summaryAmtUsd;
	}
	public List<InvoiceSummary> getInvoiceSummary() {
		return invoiceSummary;
	}
	public void setInvoiceSummary(List<InvoiceSummary> invoiceSummary) {
		this.invoiceSummary = invoiceSummary;
	}
	public BigDecimal getSummaryAmtVnd() {
		return summaryAmtVnd;
	}
	public void setSummaryAmtVnd(BigDecimal summaryAmtVnd) {
		this.summaryAmtVnd = summaryAmtVnd;
	}

}
