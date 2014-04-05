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
import javax.faces.application.FacesMessage;
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
	
	private boolean isSelectRow = false;

	private Date searchDate;
	
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
		rateInfo = null;
	}
	
	public String insert(){
		if(selectedRateInfo!=null){
			List<Rate> list = rateService.getRateByDatetime(selectedRateInfo.getDatetime());
			if(list == null || list.isEmpty()) {
				Rate rate = new Rate();
				rate.setDatetime(selectedRateInfo.getDatetime());
				rate.setRate(selectedRateInfo.getRate());
				rateService.insert(rate);
				loadRateList();
				setSelectRow(false);
				selectedRateInfo = new Rate();
			} else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Insert Rate", "Đã tồn tại ngày tỷ giá"));  
			}
		}
		return  "";
	}
	public String update(){
		if(selectedRateInfo!=null){
			rateService.update(selectedRateInfo);
			loadRateList();
			setSelectRow(false);
			selectedRateInfo = new Rate();
		}
		return  "";
	}
	public String delete(){
		if(selectedRateInfo!=null){
			rateService.delete(selectedRateInfo);
			loadRateList();	
			setSelectRow(false);
			selectedRateInfo = new Rate();
		}
		return  "";
	}
	public String find(){
		if(selectedRateInfo!=null && selectedRateInfo.getDatetime()!=null){
			rateInfoList = new ArrayList<Rate>();
			rateInfoList = rateService.getRateByDatetime(selectedRateInfo.getDatetime());
			if(rateInfoList != null && !rateInfoList.isEmpty()){
				selectedRateInfo = new Rate();
				rateInfo = null;
				setSelectRow(false);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Rate", "Chưa có tỷ giá ngày được chọn! "));
			}
			
		} else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Find Rate", "Vui lòng chọn ngày cần tìm! "));
		}
		return  "";
	}
	public void clear(){
		selectedRateInfo = new Rate();
		loadRateList();	
		setSelectRow(false);
		rateInfo = null;
	}
	public void onRowSelect(SelectEvent event) {  
		selectedRateInfo = rateInfo;
		setSelectRow(true);
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

	public boolean isSelectRow() {
		return isSelectRow;
	}

	public void setSelectRow(boolean isSelectRow) {
		this.isSelectRow = isSelectRow;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

}
