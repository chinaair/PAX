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
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Agent;
import com.chinaair.entity.TicketIssue;
import com.chinaair.entity.TicketIssueDetail;
import com.chinaair.services.TicketIssueServiceBean;

@ConversationScoped
@Named("ticketIssueBean")
public class TicketIssueBean implements Serializable {

	private static final long serialVersionUID = -1953274038082422040L;
	
	@EJB
	private TicketIssueServiceBean ticketIssueService;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	private Date issueDate;
	
	private BigDecimal roe;
	
	private Long refNo;
	
	private String paymentType;
	
	private Agent selectedAgent;
	
	private String displayAgentName;
	
	private String inputPersonName;
	
	private Date reportDate;
	
	private String taxInvoiceNo;
	
	private Date modifyDatetime;
	
	private String modifyPersonName;
	
	private List<TicketIssue> ticketIssueList;
	
	private List<TicketIssueDetail> ticketIssueDetails;
	
	private List<TicketIssueDetail> inputTicketDetails;
	
	private TicketIssue selectedTicketIssue;
	
	private TicketIssueDetail selectedTicketIssueDetail;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private String inputTicketNo;
	
	private String inputRoute;
	
	private int inputQuantity;
	
	private BigDecimal inputPrice;
	
	private String screenMode;
	
	private Date searchStartDate;
	
	private Date searchEndDate;
	
	private String searchRefNo;
	
	private String searchAgentName;
	
	private Agent searchAgent;
	
	private String searchStatus;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		ResourceBundle bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		bundle.getString("selectAgent_companyName");
		ticketIssueList = ticketIssueService.findTicketIssueList();
	}
	
	public String gotoNewTicketIssueScreen() {
		screenMode = NEW;
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public String gotoViewTicketIssueScreen() {
		screenMode = VIEW;
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public void printTicketIssueInfo() {
		
	}
	
	public void searchTicketIssue() {
		ticketIssueList = ticketIssueService.findTicketIssueList();
	}
	
	public String gotoEditDetailScreen() {
		initDetail();
		return "TicketDetailListScreen?faces-redirect=true";
	}
	
	public void initDetail() {
		resetFields();
		if(inputTicketDetails == null) {
			inputTicketDetails = new ArrayList<>();
		}
		if(ticketIssueDetails != null && !ticketIssueDetails.isEmpty()) {
			for(TicketIssueDetail item : ticketIssueDetails) {
				TicketIssueDetail newItem = new TicketIssueDetail();
				newItem.setAmount(item.getAmount());
				//newItem.setId(item.getId());
				//newItem.setLastUpdate(item.getLastUpdate());
				newItem.setPrice(item.getPrice());
				newItem.setQuantity(item.getQuantity());
				newItem.setRoute(item.getRoute());
				//newItem.setTicketIssue(item.getTicketIssue());
				newItem.setTicketNo(item.getTicketNo());
				inputTicketDetails.add(newItem);
			}
		}
	}
	
	public void onSelectTicketDetail(SelectEvent event) {
		if(selectedTicketIssueDetail != null) {
			inputTicketNo = selectedTicketIssueDetail.getTicketNo().toString();
			inputRoute = selectedTicketIssueDetail.getRoute();
			inputQuantity = selectedTicketIssueDetail.getQuantity().intValue();
			inputPrice = selectedTicketIssueDetail.getPrice();
		}
	}
	
	public void insertDetail() {
		//check insert
		TicketIssueDetail detail = new TicketIssueDetail();
		detail.setTicketNo(inputTicketNo);
		detail.setRoute(inputRoute);
		detail.setQuantity(new Long(inputQuantity));
		detail.setPrice(inputPrice);
		detail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
		inputTicketDetails.add(detail);
		resetFields();
	}
	
	public void updateDetail() {
		if(selectedTicketIssueDetail != null) {
			selectedTicketIssueDetail.setTicketNo(inputTicketNo);
			selectedTicketIssueDetail.setRoute(inputRoute);
			selectedTicketIssueDetail.setQuantity(new Long(inputQuantity));
			selectedTicketIssueDetail.setPrice(inputPrice);
			selectedTicketIssueDetail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
		}
		resetFields();
	}
	
	public void deleteDetail() {
		if(selectedTicketIssueDetail != null) {
			inputTicketDetails.remove(selectedTicketIssueDetail);
			selectedTicketIssueDetail = null;
		}
		resetFields();
	}
	
	public String okDetail() {
		ticketIssueDetails = inputTicketDetails;
		inputTicketDetails = null;
		resetFields();
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public String cancelDetail() {
		inputTicketDetails = null;
		resetFields();
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public void clearDetail() {
		resetFields();
	}
	
	public void chooseAgent() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("selectAgent", options, null);
	}
	
	public void onAgentChosen(SelectEvent event) {
		selectedAgent = (Agent)event.getObject();
		if(selectedAgent != null) {
			displayAgentName = selectedAgent.getName();
		}
	}
	
	public void onSearchAgentChosen(SelectEvent event) {
		searchAgent = (Agent)event.getObject();
		if(searchAgent != null) {
			searchAgentName = searchAgent.getName();
		}
	}
	
	public String registerTicketIssue() {
		TicketIssue ticketIssue = new TicketIssue();
		ticketIssue.setIssueDate(issueDate);
		ticketIssue.setRoe(roe);
		ticketIssue.setPaymentType(paymentType);
		ticketIssue.setAgent(selectedAgent);
		ticketIssue.setTicketIssueDetail(ticketIssueDetails);
		ticketIssueService.insertTicketIssue(ticketIssue);
		return "TicketIssueListScreen?faces-redirect=true";
	}
	
	private void resetFields() {
		selectedTicketIssueDetail = null;
		inputTicketNo = null;
		inputRoute = null;
		inputQuantity = 0;
		inputPrice = null;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public BigDecimal getRoe() {
		return roe;
	}

	public void setRoe(BigDecimal roe) {
		this.roe = roe;
	}

	public Long getRefNo() {
		return refNo;
	}
	
	public String getRefNoFormatted() {
		return String.format("%06d", refNo);
	}

	public void setRefNo(Long refNo) {
		this.refNo = refNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
	}

	public String getDisplayAgentName() {
		return displayAgentName;
	}

	public void setDisplayAgentName(String displayAgentName) {
		this.displayAgentName = displayAgentName;
	}

	public String getInputPersonName() {
		return inputPersonName;
	}

	public void setInputPersonName(String inputPersonName) {
		this.inputPersonName = inputPersonName;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getTaxInvoiceNo() {
		return taxInvoiceNo;
	}

	public void setTaxInvoiceNo(String taxInvoiceNo) {
		this.taxInvoiceNo = taxInvoiceNo;
	}

	public Date getModifyDatetime() {
		return modifyDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public String getModifyPersonName() {
		return modifyPersonName;
	}

	public void setModifyPersonName(String modifyPersonName) {
		this.modifyPersonName = modifyPersonName;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public List<TicketIssueDetail> getTicketIssueDetails() {
		return ticketIssueDetails;
	}

	public void setTicketIssueDetails(List<TicketIssueDetail> ticketIssueDetails) {
		this.ticketIssueDetails = ticketIssueDetails;
	}

	public TicketIssueDetail getSelectedTicketIssueDetail() {
		return selectedTicketIssueDetail;
	}

	public void setSelectedTicketIssueDetail(
			TicketIssueDetail selectedTicketIssueDetail) {
		this.selectedTicketIssueDetail = selectedTicketIssueDetail;
	}

	public BigDecimal getTotalAmtUsd() {
		return totalAmtUsd;
	}

	public void setTotalAmtUsd(BigDecimal totalAmtUsd) {
		this.totalAmtUsd = totalAmtUsd;
	}

	public BigDecimal getTotalAmtVnd() {
		return totalAmtVnd;
	}

	public void setTotalAmtVnd(BigDecimal totalAmtVnd) {
		this.totalAmtVnd = totalAmtVnd;
	}

	public List<TicketIssueDetail> getInputTicketDetails() {
		return inputTicketDetails;
	}

	public void setInputTicketDetails(List<TicketIssueDetail> inputTicketDetails) {
		this.inputTicketDetails = inputTicketDetails;
	}

	public String getInputTicketNo() {
		return inputTicketNo;
	}

	public void setInputTicketNo(String inputTicketNo) {
		this.inputTicketNo = inputTicketNo;
	}

	public String getInputRoute() {
		return inputRoute;
	}

	public void setInputRoute(String inputRoute) {
		this.inputRoute = inputRoute;
	}

	public int getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(int inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public BigDecimal getInputPrice() {
		return inputPrice;
	}

	public void setInputPrice(BigDecimal inputPrice) {
		this.inputPrice = inputPrice;
	}

	public List<TicketIssue> getTicketIssueList() {
		return ticketIssueList;
	}

	public void setTicketIssueList(List<TicketIssue> ticketIssueList) {
		this.ticketIssueList = ticketIssueList;
	}

	public TicketIssue getSelectedTicketIssue() {
		return selectedTicketIssue;
	}

	public void setSelectedTicketIssue(TicketIssue selectedTicketIssue) {
		this.selectedTicketIssue = selectedTicketIssue;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
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

	public String getSearchRefNo() {
		return searchRefNo;
	}

	public void setSearchRefNo(String searchRefNo) {
		this.searchRefNo = searchRefNo;
	}

	public String getSearchAgentName() {
		return searchAgentName;
	}

	public void setSearchAgentName(String searchAgentName) {
		this.searchAgentName = searchAgentName;
	}

	public Agent getSearchAgent() {
		return searchAgent;
	}

	public void setSearchAgent(Agent searchAgent) {
		this.searchAgent = searchAgent;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public TicketIssueServiceBean getTicketIssueService() {
		return ticketIssueService;
	}

	public void setTicketIssueService(TicketIssueServiceBean ticketIssueService) {
		this.ticketIssueService = ticketIssueService;
	}

}
