package com.chinaair.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Authority implements Serializable {
	
	private static final long serialVersionUID = 522290956534377301L;

	@Id
	@Column(name="CODE", unique = true, nullable = false)
	private String code;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Version
	@Column(name="LASTUPDATE", nullable = false)
	private Timestamp lastUpdate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
