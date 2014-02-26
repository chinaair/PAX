package com.chinaair.entity;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class RateInfo {
	
	@Id
	@Column(name="ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="Date", nullable = false)
	private Date datetime;
	
	@Column(name="Rate", nullable = false)
	private Double rate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}


}