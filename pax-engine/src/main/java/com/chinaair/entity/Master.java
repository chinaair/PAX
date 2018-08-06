package com.chinaair.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Master implements Serializable {
	
	private static final long serialVersionUID = -5522835854770775943L;

	@Id
	@Column(name="MASTERNO", unique = true, nullable = false)
	private String masterNo;
	
	
	@Column(name="MASTERPARENT")
	private String masterParent;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="VALUE")
	private String value;

	@Column(name="LEVEL")
	private Long level;

	private transient String parentMasterName;
	public String getMasterNo() {
		return masterNo;
	}

	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}

	public String getMasterParent() {
		return masterParent;
	}

	public void setMasterParent(String masterParent) {
		this.masterParent = masterParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	/**
	 * @return the parentMasterName
	 */
	public String getParentMasterName() {
		return parentMasterName;
	}

	/**
	 * @param parentMasterName the parentMasterName to set
	 */
	public void setParentMasterName(String parentMasterName) {
		this.parentMasterName = parentMasterName;
	}

}
