package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Rate;
import com.chinaair.services.CommonUtils;
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
	
	private List<Rate> rateInfoList;
	
	private Rate selectedRateInfo;
	
	private Rate rateInfo;

    private Double amount;  

    private Date date;  
	
	public void init() {
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", Locale.ENGLISH); //label, value
		countries.put("Vietnamese", new Locale("vi"));
		rateInfoList = new ArrayList<Rate>();
		rateInfoList = rateService.getRateList();
		rateInfo = new Rate();
		date = null;
		amount = null;
		loadRateList();
	}
	
	public void loadRateList(){
		rateInfoList = new ArrayList<Rate>();
		rateInfoList = rateService.getRateList();
	}
	
	public String insert(){
		if(date!=null && amount!=null){
			Rate rate = new Rate(); 
			rate.setDatetime(date);
			rate.setRate(amount);
			rateService.insert(rate);
			loadRateList();
		}
		date = null;
		amount = null;
		return  "";
	}
	public String update(){
		if(selectedRateInfo!=null && date!=null && amount!=null){
			selectedRateInfo.setDatetime(date);
			selectedRateInfo.setRate(amount);
			rateService.update(selectedRateInfo);
			loadRateList();
		}
		date = null;
		amount = null;
		return  "";
	}
	public String delete(){
		if(selectedRateInfo!=null){
			rateService.delete(selectedRateInfo);
			loadRateList();	
		}
		date = null;
		amount = null;
		return  "";
	}
	public String find(){
		if(date!=null && amount!=null){
			Rate rate = rateService.getRateByDatetime(date);
			date = rate.getDatetime();
			amount = rate.getRate();
		}
		return  "";
	}
	public void onRowSelect(SelectEvent event) {  
		date = rateInfo.getDatetime();
		amount = rateInfo.getRate();
		selectedRateInfo = rateInfo;
    }  
	private boolean checkError(){
		boolean error1 = CommonUtils.isThisDateValid(rateInfo.getDatetime().toString(), "dd/mm/yyyy");
		return error1;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
