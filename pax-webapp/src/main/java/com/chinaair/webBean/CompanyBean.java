package com.chinaair.webBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Company;
import com.chinaair.services.CompanyServiceBean;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;

@ConversationScoped
@Named("companyBean")
public class CompanyBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	@EJB
	private CompanyServiceBean companyService;
	
	private List<Company> companyList;
	
	private Company selectedCompany;
	
	
	
	/*******Input Screen variables********/
	
	private String screenMode;
	
	
	private String companyName;
	
	private String vat;
	
	private String phone;
	
	private String fax;
	
	private String address;
	
	private String email;
		
	private String searchCompanyName ;
	
	private String searchCompanyVat;
	
	private boolean isUpdate ;
	/*******End of Screen variables********/
	
	private ResourceBundle bundle;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoginBean loginBean;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		initValueItem();
		setCompanyList(companyService.getAllCompany());
	}
	
	public void selectCompanyFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(selectedCompany);
	}
	
	public void initValueItem() {
		setScreenMode(NEW);
		this.address = "";
		this.companyName = "";
		this.email = "";
		this.fax = "";
		this.phone = "";
		this.vat = "";
	}
	/*private boolean checkError(boolean isUpdate){
		return true;
	}*/
	public String registerCompany() {
		/*if(checkError(false)) {
			return "";
		}*/
		Company newCompany = new Company();
		newCompany.setAddress(this.address);
		newCompany.setCompany(this.companyName);
		newCompany.setEmail(this.email);
		newCompany.setFax(this.fax);
		newCompany.setPhone(this.phone);
		newCompany.setVat(this.vat);
		companyService.insert(newCompany);
		searchCompany();
		JSFUtil.addInfo(bundle, null, null, "company_saveSuccessfully");
		return "CompanyListScreen?faces-redirect=true";
	}
	public String gotoNewCompanyScreen() {
		initValueItem();
		return "CompanyScreen?faces-redirect=true";
	}
	
	public String gotoViewCompanyScreen() {
		if(selectedCompany == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		if(selectedCompany != null) {
			this.address = selectedCompany.getAddress();
			this.companyName = selectedCompany.getCompany();
			this.email = selectedCompany.getEmail();
			this.fax = selectedCompany.getFax();
			this.phone = selectedCompany.getPhone();
			this.vat = selectedCompany.getVat();
		}
		setScreenMode(VIEW);
		return "CompanyScreen?faces-redirect=true";
	}
	
	public void editCompany() {
		setScreenMode(EDIT);
	}
	
	public String updateCompany() {
		/*if(checkError(true)) {
			return null;
		}*/
		selectedCompany.setAddress(this.address);
		selectedCompany.setCompany(this.companyName);
		selectedCompany.setEmail(this.email);
		selectedCompany.setFax(this.fax);
		selectedCompany.setPhone(this.phone);
		selectedCompany.setVat(this.vat);
		companyService.update(selectedCompany);
		selectedCompany = null;
		searchCompany();
		JSFUtil.addInfo(bundle, null, null, "company_updateSuccessfully");
		return "CompanyListScreen?faces-redirect=true";
	}
	public String deleteCompany() {
		if(selectedCompany != null) {
			companyService.delete(selectedCompany);
			initValueItem();
			searchCompany();
			JSFUtil.addInfo(bundle, null, null, "company_deleteSuccessfully");
			return "CompanyListScreen?faces-redirect=true";
		}else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
		return "";
	}
	public String cancelRegisterCompany() {
		initValueItem();
		selectedCompany = null;
		searchCompany();
		return "CompanyListScreen?faces-redirect=true";
	}
	
	public void printCompanyInfo() {
		if(companyList == null || companyList.isEmpty()) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return;
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("systemClass", loginBean.getSystemClass());
			params.put("CompanyDetailList", companyList);
			ArrayList<String> dummyList = new ArrayList<>();
			dummyList.add("dummy");
			JasperUtil.createReportAndDownloadPDF("Company", dummyList, params);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void searchCompany() {
		setCompanyList(companyService.searchCompanyByCondition(searchCompanyName, searchCompanyVat));
	}
	public void refresh() {
		initValueItem();
	}

	
	public void onRowSelectCompany(){
		
	}
	/***************From there, get set methods only******************/
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}


	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * @return the screenMode
	 */
	public String getScreenMode() {
		return screenMode;
	}

	/**
	 * @param screenMode the screenMode to set
	 */
	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getSearchCompanyName() {
		return searchCompanyName;
	}

	public void setSearchCompanyName(String searchCompanyName) {
		this.searchCompanyName = searchCompanyName;
	}

	public String getSearchCompanyVat() {
		return searchCompanyVat;
	}

	public void setSearchCompanyVat(String searchCompanyVat) {
		this.searchCompanyVat = searchCompanyVat;
	}

}
