package com.chinaair.entity;

public class MasterPK {
	
	private String masterNo;
	private String detailNo;
	
	public MasterPK() {
		
	}
	
	public MasterPK(String masterNo, String detailNo) {
		this.masterNo = masterNo;
		this.detailNo = detailNo;
	}
	@Override
	public int hashCode() {
		return masterNo.hashCode() + detailNo.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MasterPK) {
			MasterPK pk = (MasterPK)obj;
			return this.masterNo.equals(pk.masterNo) && this.detailNo.equals(pk.detailNo);
		}
		return false;
	}

	public String getMasterNo() {
		return masterNo;
	}

	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}

	public String getDetailNo() {
		return detailNo;
	}

	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

}
