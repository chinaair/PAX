package com.chinaair.webBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Company;
import com.chinaair.services.CompanyServiceBean;

@ConversationScoped
@Named("selectCompanyBean")
public class SelectCompanyBean implements Serializable {
	
	private static final long serialVersionUID = -2694025789743265579L;

	private List<Company> companyList;
	
	private Company selectedCompany;
	
	@EJB
	private CompanyServiceBean companyService;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		companyList = companyService.getAllCompany();
	}
	
	public void selectCompanyFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(selectedCompany);
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

}
