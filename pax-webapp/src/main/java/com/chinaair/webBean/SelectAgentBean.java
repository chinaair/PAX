package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.dto.AgentSearchDto;
import com.chinaair.entity.Agent;
import com.chinaair.services.AgentServiceBean;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;

@ConversationScoped
@Named("selectAgentBean")
public class SelectAgentBean implements Serializable {

	private static final long serialVersionUID = -4000495264925962145L;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	/**
	 * LOCATION_HCM = "0";
	 */
	private final static String LOCATION_HCM = "0";
	/**
	 * LOCATION_HN = "1";
	 */
	private final static String LOCATION_HN = "1";
	/**
	 * TYPE_PASSENGER = "0";
	 */
	private final static String TYPE_PASSENGER = "0";
	/**
	 * TYPE_CARGO = "1";
	 */
	private final static String TYPE_CARGO = "1";
	/**
	 * DEPOSIT_TYPE_SECURITY_DEPOSIT = "0";
	 */
	private final static String DEPOSIT_TYPE_SECURITY_DEPOSIT = "0";
	/**
	 * DEPOSIT_TYPE_BANK_GUARANTEE = "1";
	 */
	private final static String DEPOSIT_TYPE_BANK_GUARANTEE = "1";
	@EJB
	private AgentServiceBean agentService;
	
	private List<Agent> agentList;
	
	private Agent selectedAgent;
	
	
	
	/*******Input Screen variables********/
	
	private String screenMode;
	
	private Agent agentInfo;
	
	private String code;
	
	private boolean retail;
	
	private String name;
	
	private String company;
	
	private String vat_code;
	
	private String phone;
	
	private String fax;
	
	private String address;
	
	private String email;
	
	private String location;
	
	private String type;
	
	private String deposit_type;
	
	private BigDecimal deposit_amt;
	
	private Date valid_date;
	
	@Size(max=8)
	private String searchAgentCode ;
	
	private String searchAgentName ;
	
	private String searchVAT;
	
	private boolean isUpdate ;
	
	private String searchLocationCode;
	
	private String searchAgentType;
	
	private SelectItem[] locationOptions;
	
	private SelectItem[] typeOptions;
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
		Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		initValueItem();
		if(requestParams.containsKey("type")) {
			if("cargo".equals(requestParams.get("type"))) {
				searchAgentType = "1";
			} else if("pax".equals(requestParams.get("type"))) {
				searchAgentType = "0";
			}
		}
		AgentSearchDto searchDto = new AgentSearchDto();
		searchDto.setType(searchAgentType);
		agentList = agentService.findAgentByCodition(searchDto);
		locationOptions = new SelectItem[2];
		locationOptions[0] = new SelectItem("0", "SGN");
		locationOptions[1] = new SelectItem("1", "HN");
		typeOptions = new SelectItem[2];
		typeOptions[0] = new SelectItem("0", "PAX");
		typeOptions[1] = new SelectItem("1", "CGO");
	}
	
	public void selectAgentFromDialog(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(selectedAgent);
	}
	
	public void initValueItem() {
		setScreenMode(NEW);
		this.address = "";
		this.code = "";
		this.retail = false;
		this.company = "";
		this.deposit_amt = null;
		this.deposit_type = DEPOSIT_TYPE_SECURITY_DEPOSIT;
		this.email = "";
		this.fax = "";
		this.location = LOCATION_HCM;
		this.name = "";
		this.phone = "";
		this.type = TYPE_PASSENGER;
		this.vat_code = "";
		this.valid_date = new Date();
	}
	private boolean checkError(boolean isUpdate){
		//check formar code
		boolean error = false;
		if(this.code.contains("-")){
			String[] arr = this.code.split("-");
			if (arr.length == 2 && arr[0].length() == 3 && arr[1].length() == 4) {
				error = false;
			} else{
				JSFUtil.addError(bundle, null, ":frm:code", "agent_invalidCode");
				error = true;
			}
		}else{
			JSFUtil.addError(bundle, null, ":frm:code", "agent_invalidCode");
			error = true;
		}
		// check dupticate Code
		Agent agent = null;
		if(isUpdate && selectedAgent != null){
			agent = agentService.findAgentByCode(this.code, selectedAgent.getLocation(),selectedAgent.getId());
		} else{
			agent = agentService.findAgentByCode(this.code, this.location, null);
		}
		if(agent != null){
			JSFUtil.addError(bundle, null, ":frm:code", "agent_dupticateCode");
			error = true;
		}	
		return error;
	}
	public String registerAgent() {
		if(checkError(false)) {
			return "";
		}
		Agent newAgent = new Agent();
		newAgent.setAddress(this.address);
		newAgent.setCode(this.code);
		if(retail) {
			newAgent.setRetailFlag("1");
		}
		newAgent.setCompany(this.company);
		newAgent.setDeposit_amt(this.deposit_amt);
		newAgent.setDeposit_type(this.deposit_type);
		newAgent.setEmail(this.email);
		newAgent.setFax(this.fax);
		newAgent.setLocation(this.location);
		newAgent.setName(this.name);
		newAgent.setPhone(this.phone);
		newAgent.setType(this.type);
		newAgent.setVat_code(this.vat_code);
		newAgent.setValid_date(this.valid_date);
		agentService.insert(newAgent);
		searchAgent();
		JSFUtil.addInfo(bundle, null, null, "agent_saveSuccessfully");
		return "AgentListScreen?faces-redirect=true";
	}
	
	public String gotoNewAgentScreen() {
		initValueItem();
		return "AgentScreen?faces-redirect=true";
	}
	
	public String gotoViewAgentScreen() {
		if(selectedAgent == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		if(selectedAgent != null) {
			this.address = selectedAgent.getAddress();
			this.code = selectedAgent.getCode();
			this.retail = false;
			if("1".equals(selectedAgent.getRetailFlag())) {
				this.retail = true;
			}
			this.company = selectedAgent.getCompany();
			this.deposit_amt = selectedAgent.getDeposit_amt();
			this.deposit_type = selectedAgent.getDeposit_type();
			this.email = selectedAgent.getEmail();
			this.fax = selectedAgent.getFax();
			this.location = selectedAgent.getLocation();
			this.name = selectedAgent.getName();
			this.phone = selectedAgent.getPhone();
			this.type = selectedAgent.getType();
			this.vat_code = selectedAgent.getVat_code();
			this.valid_date = selectedAgent.getValid_date();
		}
		setScreenMode(VIEW);
		return "AgentScreen?faces-redirect=true";
	}
	
	public void editAgent() {
		setScreenMode(EDIT);
	}
	
	public String updateAgent() {
		if(checkError(true)) {
			return null;
		}
		selectedAgent.setAddress(this.address);
		selectedAgent.setCode(this.code);
		selectedAgent.setCompany(this.company);
		selectedAgent.setDeposit_amt(this.deposit_amt);
		selectedAgent.setDeposit_type(this.deposit_type);
		selectedAgent.setEmail(this.email);
		selectedAgent.setFax(this.fax);
		selectedAgent.setLocation(this.location);
		selectedAgent.setName(this.name);
		selectedAgent.setPhone(this.phone);
		selectedAgent.setType(this.type);
		selectedAgent.setVat_code(this.vat_code);
		selectedAgent.setValid_date(this.valid_date);
		agentService.update(selectedAgent);
		selectedAgent = null;
		searchAgent();
		JSFUtil.addInfo(bundle, null, null, "agent_updateSuccessfully");
		return "AgentListScreen?faces-redirect=true";
	}
	public String deleteAgent() {
		if(selectedAgent != null) {
			agentService.delete(selectedAgent);
			initValueItem();
			searchAgent();
			JSFUtil.addInfo(bundle, null, null, "agent_deleteSuccessfully");
			return "AgentListScreen?faces-redirect=true";
		}else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
		return "";
	}
	public String cancelRegisterAgent() {
		initValueItem();
		selectedAgent = null;
		searchAgent();
		return "AgentListScreen?faces-redirect=true";
	}
	
	public void printAgentInfo() {
		if(agentList == null || agentList.isEmpty()) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return;
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("systemClass", loginBean.getSystemClass());
			params.put("AgentDetailList", agentList);
			ArrayList<String> dummyList = new ArrayList<>();
			dummyList.add("dummy");
			JasperUtil.createReportAndDownloadPDF("Agent", dummyList, params);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void searchAgent() {
		AgentSearchDto searchDto = new AgentSearchDto();
		searchDto.setAgentCode(searchAgentCode);
		searchDto.setAgentName(searchAgentName);
		searchDto.setVatCode(searchVAT);
		searchDto.setType(searchAgentType);
		searchDto.setLocation(searchLocationCode);
		agentList = agentService.findAgentByCodition(searchDto);
	}
	public void refresh() {
		initValueItem();
	}

	
	public void onRowSelectAgent(){
		
	}
	/***************From there, get set methods only******************/
	
	public List<Agent> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<Agent> agentList) {
		this.agentList = agentList;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
	}

	public AgentServiceBean getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentServiceBean agentService) {
		this.agentService = agentService;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isRetail() {
		return retail;
	}

	public void setRetail(boolean retail) {
		this.retail = retail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVat_code() {
		return vat_code;
	}

	public void setVat_code(String vat_code) {
		this.vat_code = vat_code;
	}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeposit_type() {
		return deposit_type;
	}

	public void setDeposit_type(String deposit_type) {
		this.deposit_type = deposit_type;
	}

	public BigDecimal getDeposit_amt() {
		return deposit_amt;
	}

	public void setDeposit_amt(BigDecimal deposit_amt) {
		this.deposit_amt = deposit_amt;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Agent getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(Agent agentInfo) {
		this.agentInfo = agentInfo;
	}

	public Date getValid_date() {
		return valid_date;
	}

	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}

	public String getSearchAgentCode() {
		return searchAgentCode;
	}

	public void setSearchAgentCode(String searchAgentCode) {
		this.searchAgentCode = searchAgentCode;
	}

	public String getSearchAgentName() {
		return searchAgentName;
	}

	public void setSearchAgentName(String searchAgentName) {
		this.searchAgentName = searchAgentName;
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

	public String getSearchVAT() {
		return searchVAT;
	}

	public void setSearchVAT(String searchVAT) {
		this.searchVAT = searchVAT;
	}

	public SelectItem[] getLocationOptions() {
		return locationOptions;
	}

	public void setLocationOptions(SelectItem[] locationOptions) {
		this.locationOptions = locationOptions;
	}

	public SelectItem[] getTypeOptions() {
		return typeOptions;
	}

	public void setTypeOptions(SelectItem[] typeOptions) {
		this.typeOptions = typeOptions;
	}

	public String getSearchLocationCode() {
		return searchLocationCode;
	}

	public void setSearchLocationCode(String searchLocationCode) {
		this.searchLocationCode = searchLocationCode;
	}

	public String getSearchAgentType() {
		return searchAgentType;
	}

	public void setSearchAgentType(String searchAgentType) {
		this.searchAgentType = searchAgentType;
	}

}
