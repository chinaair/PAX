package com.chinaair.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.chinaair.entity.Employee;
import com.chinaair.entity.TicketIssue;

public class EmpTicketIssueDto implements Serializable {
	
	private static final long serialVersionUID = -1003583825909881929L;
	
	private static final DecimalFormat usd = new DecimalFormat("#,##0.00");
	
	private static final DecimalFormat vnd = new DecimalFormat("#,###");

	private Employee emp;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private List<TicketIssue> ticketIssueList;

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
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

}
