package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import com.chinaair.entity.RateInfo;
import com.chinaair.services.RateServiceBean;

@SessionScoped
@Named
public class RateBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private RateServiceBean rateService;
	
	private String localeCode;
	
	private static Map<String,Object> countries;
	
	private List<RateInfo> rateInfoList;
	
	private RateInfo selectedRateInfo;
	
	private RateInfo rateInfo;

	@PostConstruct
	public void init() {
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("Vietnamese", new Locale("vi"));
		rateInfoList = new ArrayList<RateInfo>();
		rateInfoList = rateService.getRateList();
		loadRateList();
	}
	
	public void loadRateList(){
		rateInfoList = new ArrayList<RateInfo>();
		rateInfoList = rateService.getRateList();
	}
	
	public String insert(){
		rateService.insert(rateInfo);
		return  "";
	}
	public String update(){
		rateService.update(rateInfo);
		return  "";
	}
	public String delete(){
		rateService.delete(rateInfo);
		return  "";
	}
	public String find(){
		selectedRateInfo = rateService.getRateByDatetime(rateInfo.getDatetime());
		return  "";
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public Map<String, Object> getCountriesInMap() {
		return countries;
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
	
	public String getLocaleCode() {
		return localeCode;
	}
 
 
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	
	public List<RateInfo> getRateInfoList() {
		return rateInfoList;
	}

	public void setRateInfoList(List<RateInfo> RateInfoList) {
		this.rateInfoList = RateInfoList;
	}

	public RateInfo getSelectedRateInfo() {
		return selectedRateInfo;
	}

	public void setSelectedRateInfo(RateInfo selectedRateInfo) {
		this.selectedRateInfo = selectedRateInfo;
	}

	public RateInfo getRateInfo() {
		return rateInfo;
	}

	public void setRateInfo(RateInfo rateInfo) {
		this.rateInfo = rateInfo;
	}
}
