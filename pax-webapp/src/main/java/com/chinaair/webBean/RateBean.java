package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import com.chinaair.entity.Rate;
import com.chinaair.services.RateServiceBean;

@ConversationScoped
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
	
	private List<Rate> rateInfoList;
	
	private Rate selectedRateInfo;
	
	private Rate rateInfo;

	public void init() {
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("Vietnamese", new Locale("vi"));
		rateInfoList = new ArrayList<Rate>();
		rateInfoList = rateService.getRateList();
		rateInfo = new Rate();
		loadRateList();
	}
	
	public void loadRateList(){
		rateInfoList = new ArrayList<Rate>();
		rateInfoList = rateService.getRateList();
		System.err.println("jofen test load rate");
	}
	
	public String insert(){
		if(rateInfo!=null && rateInfo.getDatetime()!=null && rateInfo.getRate()!=null){
			rateService.insert(rateInfo);
			loadRateList();
		}
		
		return  "";
	}
	public String update(){
		rateService.update(rateInfo);
		loadRateList();
		return  "";
	}
	public String delete(){
		rateService.delete(rateInfo);
		loadRateList();
		return  "";
	}
	public String find(){
		selectedRateInfo = rateService.getRateByDatetime(rateInfo.getDatetime());
		rateInfo = new Rate();
		rateInfo = selectedRateInfo;
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
	
	public List<Rate> getRateInfoList() {
		return rateInfoList;
	}

	public void setRateInfoList(List<Rate> RateInfoList) {
		this.rateInfoList = RateInfoList;
	}

	public Rate getSelectedRateInfo() {
		return selectedRateInfo;
	}

	public void setSelectedRateInfo(Rate selectedRateInfo) {
		this.selectedRateInfo = selectedRateInfo;
	}

	public Rate getRateInfo() {
		return rateInfo;
	}

	public void setRateInfo(Rate rateInfo) {
		this.rateInfo = rateInfo;
		
	}
}
