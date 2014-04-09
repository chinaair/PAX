package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.chinaair.entity.Rate;
import com.chinaair.services.RateServiceBean;
import com.chinaair.util.DateUtil;
import com.chinaair.util.JSFUtil;

@ConversationScoped
@Named
public class RateBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private RateServiceBean rateService;
	
	private List<Rate> rateInfoList;
	
	private Rate currentRateInfo;
	
	private Date searchStartDate;
	
	private Date searchEndDate;
	
	private Date rateDate;
	
	private BigDecimal rateAmount;
	
	private ResourceBundle bundle;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}

	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		initInputRateScreen();
	}
	
	public String initInputRateScreen() {
		currentRateInfo = rateService.getTodayRate();
		if(currentRateInfo != null) {
			rateDate = currentRateInfo.getDatetime();
			rateAmount = currentRateInfo.getRate();
		} else {
			rateDate = new Date();
			rateAmount = null;
		}
		return "InputRate?faces-redirect=true";
	}
	
	public String initRateListScreen() {
		Date searchDate = new Date();
		searchStartDate = DateUtil.getFirstDateOfMonth(searchDate);
		searchEndDate = DateUtil.getLastDateOfMonth(searchDate);
		rateInfoList = rateService.getRateList(searchStartDate, searchEndDate);
		return "RateList?faces-redirect=true";
	}
	
	public void update(){
		if(rateAmount == null || rateAmount.intValue() <= 0) {
			JSFUtil.addError(bundle, ":frm:rate", "inputRate_invalidRate");
			return;
		}
		if(currentRateInfo != null) {
			currentRateInfo.setRate(rateAmount);
			rateService.update(currentRateInfo);
		} else {
			Rate newRate = new Rate();
			newRate.setDatetime(rateDate);
			newRate.setRate(rateAmount);
			currentRateInfo = rateService.insert(newRate);
		}
		JSFUtil.addInfo(bundle, null, "inputRate_updateSuccessfully");
	}
	
	public void delete(){
		if(currentRateInfo!=null){
			rateService.delete(currentRateInfo);
			currentRateInfo = null;
			rateDate = new Date();
			rateAmount = null;
		}
	}
	public void find(){
		if(searchStartDate == null) {
			JSFUtil.addError(bundle, ":frm:searhStart", "inputRate_invalidSearchStartDate");
			return;
		} else if(searchEndDate != null && searchEndDate.compareTo(searchStartDate) <= 0) {
			JSFUtil.addError(bundle, ":frm:searhEnd", "inputRate_invalidSearchEndDate");
			return;
		}
		rateInfoList = rateService.getRateList(searchStartDate, searchEndDate);
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public List<Rate> getRateInfoList() {
		return rateInfoList;
	}

	public void setRateInfoList(List<Rate> RateInfoList) {
		this.rateInfoList = RateInfoList;
	}

	public Date getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

}
