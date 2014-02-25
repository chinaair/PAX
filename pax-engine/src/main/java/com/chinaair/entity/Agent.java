package com.chinaair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agent {
	
	@Id
	@Column(name="ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="AgentCode", nullable = false)
	private String agentCode;
	
	@Column(name="AgentName", nullable = false)
	private String agentName;
	
	@Column(name="Company", nullable = false)
	private String company;
	
	@Column(name="TaxCode", nullable = false)
	private String taxCode;
	
	@Column(name="Phone", nullable = false)
	private String phone;
	
	@Column(name="Address", nullable = false)
	private String address;
	
	@Column(name="Email", nullable = false)
	private String email;
	
	@Column(name="Type", nullable = false)
	private String type;
	
	@Column(name="Deposit", nullable = false)
	private String deposit;
	
	@Column(name="Amount", nullable = false)
	private String amount;
	
	@Column(name="ValidDate", nullable = false)
	private String validDate;
	
	@Column(name="Branch", nullable = false)
	private String branch;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


}

