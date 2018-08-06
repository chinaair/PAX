package com.chinaair.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceSummary implements Serializable {
	
	private static final long serialVersionUID = -651910083187130121L;

	private Date issueDate;
	
	private BigDecimal dailyAmtUsd;
	
	private BigDecimal dailyAmtVnd;

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public BigDecimal getDailyAmtUsd() {
		return dailyAmtUsd;
	}

	public void setDailyAmtUsd(BigDecimal dailyAmtUsd) {
		this.dailyAmtUsd = dailyAmtUsd;
	}

	public BigDecimal getDailyAmtVnd() {
		return dailyAmtVnd;
	}

	public void setDailyAmtVnd(BigDecimal dailyAmtVnd) {
		this.dailyAmtVnd = dailyAmtVnd;
	}

}
