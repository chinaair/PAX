package com.chinaair.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Menu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3604228958713114645L;

	@Id
	@Column(name="ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="MENU", unique = true,nullable = false)
	private String menu;
	
	@Column(name="MENU_VI", unique = true,nullable = false)
	private String menu_vi;
	
	@Column(name="LEVEL", nullable = false)
	private int level;
	
	@Column(name="PARENTID")
	private Long parentId;
	

	@Column(name="USEFLAG")
	private boolean useFlag;
	
	@Column(name="LINK")
	private String link;
	
	@Column(name="ICON")
	private String icon;
	
	private transient String parentMenu;
	
	public void setId(Long id) {
		this.id = id;
	}


	public String getMenu() {
		return menu;
	}


	public void setMenu(String menu) {
		this.menu = menu;
	}


	public String getMenu_vi() {
		return menu_vi;
	}


	public void setMenu_vi(String menu_vi) {
		this.menu_vi = menu_vi;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getId() {
		return id;
	}


	public boolean getUseFlag() {
		return useFlag;
	}


	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getParentMenu() {
		return parentMenu;
	}


	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

}

