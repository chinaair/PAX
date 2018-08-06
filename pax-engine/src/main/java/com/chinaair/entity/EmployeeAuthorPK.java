package com.chinaair.entity;

public class EmployeeAuthorPK {
	
	private Long empId;
	private String authorCode;
	
	public EmployeeAuthorPK() {
		
	}
	
	public EmployeeAuthorPK(Long empId, String authorCode) {
		this.empId = empId;
		this.authorCode = authorCode;
	}
	@Override
	public int hashCode() {
		return empId.intValue() + authorCode.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EmployeeAuthorPK) {
			EmployeeAuthorPK pk = (EmployeeAuthorPK)obj;
			return this.empId.equals(pk.empId) && this.authorCode.equals(pk.authorCode);
		}
		return false;
	}

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

}
