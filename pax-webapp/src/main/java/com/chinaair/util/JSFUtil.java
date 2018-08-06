package com.chinaair.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class JSFUtil {
	
	public static void addError(ResourceBundle bundle, String displayTarget, String componentId, String resourceKey) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
						.getString(resourceKey), null));
		setInvalidComponent(componentId);
	}
	
	public static void addError(String displayTarget, String componentId, String resourceKey) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						getResourceBundle().getString(resourceKey), null));
		setInvalidComponent(componentId);
	}
	
	public static void addErrorMessage(String displayTarget, String componentId, String errorMessage) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
						null));
		setInvalidComponent(componentId);
	}
	
	public static void addInfo(ResourceBundle bundle, String displayTarget, String componentId, String resourceKey) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_INFO, bundle
						.getString(resourceKey), null));
		setInvalidComponent(componentId);
	}
	
	public static void addInfo(String displayTarget, String componentId, String resourceKey) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						getResourceBundle().getString(resourceKey), null));
		setInvalidComponent(componentId);
	}
	
	public static void addInfoMessage(String displayTarget, String componentId, String errorMessage) {
		FacesContext.getCurrentInstance().addMessage(
				displayTarget,
				new FacesMessage(FacesMessage.SEVERITY_INFO, errorMessage,
						null));
		setInvalidComponent(componentId);
	}
	
	public static void setInvalidComponent(String componentId) {
		if(componentId == null) {
			return;
		}
		UIComponent invalidComp = FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(componentId);
		if(invalidComp != null && invalidComp instanceof UIInput) {
			((UIInput)invalidComp).setValid(false);
		}
	}
	
	public static void setValidComponent(String componentId) {
		if(componentId == null) {
			return;
		}
		UIComponent invalidComp = FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(componentId);
		if(invalidComp != null && invalidComp instanceof UIInput) {
			((UIInput)invalidComp).setValid(true);
		}
	}
	
	public static ResourceBundle getResourceBundle() {
		return getResourceBundle("com.chinaair.internationalization.AllResourceBundle");
	}
	
	public static ResourceBundle getResourceBundle(String baseName) {
		return ResourceBundle.getBundle(baseName);
	}
}
