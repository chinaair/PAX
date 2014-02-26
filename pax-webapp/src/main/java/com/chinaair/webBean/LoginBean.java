package com.chinaair.webBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import com.chinaair.entity.Customer;
import com.chinaair.services.CommonServiceBean;
import com.chinaair.util.JasperUtil;

@ConversationScoped
@Named
public class LoginBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private CommonServiceBean commonService;
	
	private String localeCode;
	
	private static Map<String,Object> countries;
	
	public void init() {
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("Vietnamese", new Locale("vi"));
		System.err.println("jofenquan test");
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getMessage() {
		Customer aCust = commonService.getOneCustomer();
		return aCust.getName();
	}
	
	public String moveToCreateCustPage() {
		return "createCustomer?faces-redirect=true";
	}
	
	public void countryLocaleCodeChanged(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();

		// loop country map to compare the locale code
		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot()
						.setLocale((Locale) entry.getValue());

			}
		}
	}
	
	public void printReport() {
		try {
			JasperUtil.createReportAndDownloadPDF("testing", commonService.getAllCustomer(), new HashMap<String, Object>());
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String getLocaleCode() {
		return localeCode;
	}
 
 
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
}
