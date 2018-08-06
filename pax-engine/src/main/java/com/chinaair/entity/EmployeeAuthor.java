package com.chinaair.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="EMPLOYEE_AUTHORITY")
@IdClass(EmployeeAuthorPK.class)
public class EmployeeAuthor {
	
	@Id
	@Column(name="EMP_ID", nullable = false)
	private Long empId;
	
	@Id
	@Column(name="AUTHOR_CODE", nullable = false)
	private String authorCode;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="AUTHOR_CODE", insertable=false, updatable=false)
	private Authority author;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public Authority getAuthor() {
		return author;
	}

	public void setAuthor(Authority author) {
		this.author = author;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
