package com.chinaair.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3604228958713114645L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="EMPLOYEENAME", nullable = false)
	private String empName;
	
	
	@Column(name="POSITION", nullable = false)
	private String position;
	
	@Column(name="USERID", unique = true, nullable = false)
	private String userId;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="AUTHORITY")
	private String authority;
	
	@Column(name="TAXCODE")
	private String taxCode;

	
	@Column(name="USAGEFLAG")
	private boolean usageFlag;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isUsageFlag() {
		return usageFlag;
	}

	public void setUsageFlag(boolean usageFlag) {
		this.usageFlag = usageFlag;
	}

}

