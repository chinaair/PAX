package com.chinaair.dto;

import java.io.Serializable;
import java.util.Date;

public class FinalReportSearchDto implements Serializable {

	private static final long serialVersionUID = -1803154686143872434L;
	
	private Date startDate;
	
	private Date endDate;
	
	private String paymentType;

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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
