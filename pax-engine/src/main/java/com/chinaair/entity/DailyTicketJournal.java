package com.chinaair.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DAILY_TICKET_JOURNAL")
public class DailyTicketJournal implements Serializable {
	
	private static final long serialVersionUID = 1732911381466110007L;

	@Id
	@Column(name="PBSR_DATE", unique = true, nullable = false)
	@Temporal(TemporalType.DATE)
	private Date pbsrDate;
	
	@Column(name="TOTAL_AMT_USD", nullable = false)
	private BigDecimal totalAmtUsd;
	
	@Column(name="TOTAL_AMT_VND")
	private BigDecimal totalAmtVnd;
	
	@Column(name="CLOSE_EMP_ID")
	private Long closeEmpId;
	
	/**
	 * 0: opened
	 * 1: closed
	 */
	@Column(name="STATUS", nullable = false)
	private String status;

	public Date getPbsrDate() {
		return pbsrDate;
	}

	public void setPbsrDate(Date pbsrDate) {
		this.pbsrDate = pbsrDate;
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

	public Long getCloseEmpId() {
		return closeEmpId;
	}

	public void setCloseEmpId(Long closeEmpId) {
		this.closeEmpId = closeEmpId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
