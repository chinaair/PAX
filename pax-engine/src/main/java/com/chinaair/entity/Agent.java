package com.chinaair.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3604228958713114645L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CODE", unique = true, nullable = false)
	private String code;
	
	/**
	 * 0: Hochiminh
	 * 1: Hanoi
	 */
	@Column(name="LOCATION", nullable = false)
	private String location;
	
	/**
	 * 0: passenger
	 * 1: cargo
	 */
	@Column(name="TYPE", nullable = false)
	private String type;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="COMPANY")
	private String company;
	
	@Column(name="VAT_CODE")
	private String vat_code;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="FAX")
	private String fax;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="EMAIL")
	private String email;
	
	/**
	 * 0: security deposit
	 * 1: bank guarantee
	 */
	@Column(name="DEPOSIT_TYPE")
	private String deposit_type;
	
	@Column(name="DEPOSIT_AMT")
	private BigDecimal deposit_amt;
	
	@Column(name="VALID_DATE")
	private Date valid_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVat_code() {
		return vat_code;
	}

	public void setVat_code(String vat_code) {
		this.vat_code = vat_code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getDeposit_type() {
		return deposit_type;
	}

	public void setDeposit_type(String deposit_type) {
		this.deposit_type = deposit_type;
	}

	public BigDecimal getDeposit_amt() {
		return deposit_amt;
	}

	public void setDeposit_amt(BigDecimal deposit_amt) {
		this.deposit_amt = deposit_amt;
	}

	public Date getValid_date() {
		return valid_date;
	}

	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}

}

