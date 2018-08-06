package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.LazyDataModel;

import com.chinaair.dto.TicketIssueDetailDto;
import com.chinaair.dto.TicketIssueSearchDto;
import com.chinaair.entity.Agent;
import com.chinaair.entity.DailyTicketJournal;
import com.chinaair.entity.Rate;
import com.chinaair.entity.TicketIssue;
import com.chinaair.entity.TicketIssueDetail;
import com.chinaair.services.AgentServiceBean;
import com.chinaair.services.AuthorityServiceBean;
import com.chinaair.services.CommonServiceBean;
import com.chinaair.services.JournalServiceBean;
import com.chinaair.services.RateServiceBean;
import com.chinaair.services.TicketIssueServiceBean;
import com.chinaair.util.DateUtil;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;
import com.chinaair.webDto.TicketIssueModel;

@ConversationScoped
@Named("ticketIssueBean")
public class TicketIssueBean implements Serializable {

	private static final long serialVersionUID = -1953274038082422040L;
	
	@EJB
	private TicketIssueServiceBean ticketIssueService;
	
	@EJB
	private RateServiceBean rateService;
	
	@EJB
	private CommonServiceBean commonService;
	
	@EJB
	private JournalServiceBean journalService;
	
	@EJB
	private AgentServiceBean agentService;
	
	@EJB
	private AuthorityServiceBean authorService;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	private static final DecimalFormat usd = new DecimalFormat("#,##0.00");
	
	private static final DecimalFormat vnd = new DecimalFormat("#,###");
	
	private Date issueDate;
	
	private BigDecimal roe;
	
	private Long refNo;
	
	private String paymentType;
	
	private Agent selectedAgent;
	
	private Rate selectedRate;
	
	private String displayAgentName;
	
	private String inputAgentCode;
	
	private String inputPersonName;
	
	private Date reportDate;
	
	private String taxInvoiceNo;
	
	private Date modifyDatetime;
	
	private String modifyPersonName;
	
	private LazyDataModel<TicketIssue> ticketIssueList;
	
	private List<TicketIssueDetail> ticketIssueDetails;
	
	private List<TicketIssueDetail> inputTicketDetails;
	
	private TicketIssue selectedTicketIssue;
	
	private TicketIssueDetail selectedTicketIssueDetail;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private BigDecimal detailAmtUsd;
	
	private BigDecimal detailAmtVnd;
	
	private String displayTotalUsd;
	
	private String displayTotalVnd;
	
	private String inputTicketNo;
	
	private String inputRoute;
	
	private int inputQuantity;
	
	private BigDecimal inputPrice;
	
	private String screenMode;
	
	private Date searchStartDate;
	
	private Date searchEndDate;
	
	private String searchRefNo;
	
	private String searchAgentCode;
	
	private String searchAgentName;
	
	private Agent searchAgent;
	
	private String searchStatus;
	
	private String searchTicketNo;
	
	private String searchRoute;
	
	private String searchInputSD;
	
	private ResourceBundle bundle;
	
	private boolean journalClosed;
	
	private boolean todayRateNotSet;
	
	private boolean hasEditAuthority;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private TaxInvoiceIssueBean taxInvoiceIssueBean;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		journalClosed = false;
		resetSearchCondition();
		searchTicketIssue(false);
	}
	
	public String gotoNewTicketIssueScreen() {
		resetMainFields();
		ticketIssueDetails = null;
		todayRateNotSet = false;
		selectedRate = rateService.getTodayRate();
		if(selectedRate == null) {
			todayRateNotSet = true;
			selectedRate = rateService.getNearestRate();
		}
		if(selectedRate != null) {
			roe = selectedRate.getRate();
		}
		issueDate = new Date();
		totalAmtUsd = new BigDecimal(0);
		totalAmtVnd = new BigDecimal(0);
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
		if(loginBean != null && loginBean.getLoginUser() != null) {
			inputPersonName = loginBean.getLoginUser().getEmpName();
		}
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public void onchangeIssueDate() {
		selectedRate = rateService.getRateByDate(issueDate);
		if(selectedRate != null) {
			roe = selectedRate.getRate();
		} else {
			roe = null;
		}
	}
	
	public LazyDataModel<TicketIssue> getTicketIssueList() {
		return ticketIssueList;
	}

	public String gotoViewTicketIssueScreen() {
		if(selectedTicketIssue == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		screenMode = VIEW;
		hasEditAuthority = false;
		todayRateNotSet = false;
		if(selectedTicketIssue != null) {
			refNo = selectedTicketIssue.getId();
			issueDate = selectedTicketIssue.getIssueDate();
			if(selectedTicketIssue.getTaxInvoiceIssue() != null) {
				taxInvoiceNo = selectedTicketIssue.getTaxInvoiceIssue().getInvoiceStockDetail().getInvoiceStockNo();
			}
			if(selectedTicketIssue.getRate() != null) {
				roe = selectedTicketIssue.getRate().getRate();
				selectedRate = selectedTicketIssue.getRate();
			}
			paymentType = selectedTicketIssue.getPaymentType();
			selectedAgent = selectedTicketIssue.getAgent();
			if(selectedAgent != null) {
				if("1".equals(selectedAgent.getRetailFlag())) {
					displayAgentName = selectedTicketIssue.getRetailCustomerName();
				} else {
					displayAgentName = selectedAgent.getName();
				}
				inputAgentCode = selectedAgent.getCode();
			}
			ticketIssueDetails = selectedTicketIssue.getTicketIssueDetail();
			totalAmtUsd = selectedTicketIssue.getAmountUsd();
			refreshSumMoney();
			if(selectedTicketIssue.getInputEmp() != null) {
				inputPersonName = selectedTicketIssue.getInputEmp().getEmpName();
			}
			if(selectedTicketIssue.getModifyEmp() != null) {
				modifyPersonName = selectedTicketIssue.getModifyEmp().getEmpName();
			}
			modifyDatetime = selectedTicketIssue.getModifyDatetime();
			reportDate = selectedTicketIssue.getReportedDate();
			//edit button disabled
			if(selectedTicketIssue.getInputEmpId().equals(loginBean.getLoginUser().getId())) {
				hasEditAuthority = true;
			} else if(authorService.findEmpAuthor(loginBean.getLoginUser().getId(), "0001") != null) {//edit ticket issue authority
				hasEditAuthority = true;
			}
		}
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public void setTicketIssueList(LazyDataModel<TicketIssue> ticketIssueList) {
		this.ticketIssueList = ticketIssueList;
	}

	public void editTicketIssue() {
		screenMode = EDIT;
	}
	
	public String useTicketIssue() {
		if(!checkValidTicketIssue(true)) {
			return null;
		}
		selectedTicketIssue.setIssueDate(issueDate);
		selectedTicketIssue.setRate(selectedRate);
		selectedTicketIssue.setPaymentType(paymentType);
		selectedTicketIssue.setAgent(selectedAgent);
		selectedTicketIssue.setTicketIssueDetail(ticketIssueDetails);
		selectedTicketIssue.setStatus("0");//no tax invoice yet
		selectedTicketIssue.setAmountUsd(totalAmtUsd);
		selectedTicketIssue.setModifyDatetime(new Timestamp(new Date().getTime()));
		if("1".equals(selectedAgent.getRetailFlag())) {
			selectedTicketIssue.setRetailCustomerName(displayAgentName);
		}
		ticketIssueService.updateTicketIssue(selectedTicketIssue);
		selectedTicketIssue = null;
		//resetSearchCondition();
		searchTicketIssue(false);
		return "TicketIssueListScreen?faces-redirect=true";
	}
	
	public String updateTicketIssue() {
		boolean checkJournal =true;
		if("3".equals(selectedTicketIssue.getStatus())) {//save temp
			checkJournal = false;
		}
		if(!checkValidTicketIssue(checkJournal)) {
			return null;
		}
		selectedTicketIssue.setIssueDate(issueDate);
		selectedTicketIssue.setRate(selectedRate);
		selectedTicketIssue.setPaymentType(paymentType);
		selectedTicketIssue.setAgent(selectedAgent);
		selectedTicketIssue.setTicketIssueDetail(ticketIssueDetails);
		//selectedTicketIssue.setStatus("0");//no tax invoice yet
		selectedTicketIssue.setAmountUsd(totalAmtUsd);
		if(loginBean.getLoginUser() != null) {
			selectedTicketIssue.setModifyEmpId(loginBean.getLoginUser().getId());
		}
		selectedTicketIssue.setRetailCustomerName(null);
		if("1".equals(selectedAgent.getRetailFlag())) {
			selectedTicketIssue.setRetailCustomerName(displayAgentName);
		}
		selectedTicketIssue.setModifyDatetime(new Timestamp(new Date().getTime()));
		ticketIssueService.updateTicketIssue(selectedTicketIssue);
		selectedTicketIssue = ticketIssueService.findTicketIssueById(selectedTicketIssue.getId());
		return gotoViewTicketIssueScreen();
	}
	
	public String voidTicketIssue() {
		if(selectedTicketIssue != null) {
			selectedTicketIssue.setStatus("2");
			ticketIssueService.updateTicketIssueStatus(selectedTicketIssue);
		}
		selectedTicketIssue = null;
		//resetSearchCondition();
		searchTicketIssue(false);
		return "TicketIssueListScreen?faces-redirect=true";
	}
	
	public void printTicketIssueInfo() {
		if(selectedTicketIssue == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return;
		}
		if("2".equals(selectedTicketIssue.getStatus())) {//voided
			JSFUtil.addError(bundle, null, null, "resource_error_printVoidedRecord");
			return;
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("systemClass", loginBean.getSystemClass());
			params.put("type", getPaymentTypeString(selectedTicketIssue.getPaymentType()));
			if(selectedTicketIssue.getAgent() != null) {
				params.put("agentCode", selectedTicketIssue.getAgent().getCode());
				if("1".equals(selectedTicketIssue.getAgent().getRetailFlag())) {
					params.put("agentName", selectedTicketIssue.getRetailCustomerName());
				} else {
					params.put("agentName", selectedTicketIssue.getAgent().getName());
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			params.put("printDatetime", df.format(selectedTicketIssue.getIssueDate()));
			params.put("refNo", selectedTicketIssue.getFormattedId());
			if(selectedTicketIssue.getInputEmp() != null) {
				params.put("sdName", selectedTicketIssue.getInputEmp().getEmpName());
			}
			if(selectedTicketIssue.getRate() != null) {
				BigDecimal printRoe = selectedTicketIssue.getRate().getRate();
				params.put("roe", printRoe);
				List<TicketIssueDetailDto> detailDto = new ArrayList<>();
				if(printRoe != null) {
					BigDecimal thousandUnit = new BigDecimal(1000);
					BigDecimal printTotalAmtVnd = new BigDecimal(0);
					List<TicketIssueDetail> details = selectedTicketIssue.getTicketIssueDetail();
					for(TicketIssueDetail detail : details) {
						TicketIssueDetailDto dto = new TicketIssueDetailDto();
						dto.setId(detail.getId());
						dto.setQuantity(detail.getQuantity());
						dto.setRoute(detail.getRoute());
						dto.setTicketNo(detail.getTicketNo());
						BigDecimal vndPrice = detail.getPrice().multiply(printRoe);
						BigDecimal vndAmount = vndPrice.divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit).multiply(new BigDecimal(detail.getQuantity()));
						dto.setVndPrice(vndPrice);
						dto.setVndAmount(vndAmount);
						detailDto.add(dto);
						printTotalAmtVnd = printTotalAmtVnd.add(vndAmount);
					}
					params.put("totalAmt", printTotalAmtVnd);
					params.put("ticketIssueDetailList", detailDto);
				}
			}
			ArrayList<String> dummyList = new ArrayList<>();
			dummyList.add("dummy");
			JasperUtil.createReportAndDownloadPDF("TicketIssue", dummyList, params);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void searchTicketIssue(boolean isSearchAll) {
		TicketIssueSearchDto searchDto = new TicketIssueSearchDto();
		if(!isSearchAll){
			if(searchRefNo != null && searchRefNo.length() > 0) {
				if(searchRefNo.length() != 6) {
					JSFUtil.addError(bundle, null, ":frm:searchRefNo", "ticketIssue_error_ReferNumlength");
					return;
				}
				try {
					new Long(searchRefNo);
				} catch(NumberFormatException e) {
					JSFUtil.addError(bundle, null, ":frm:searchRefNo", "ticketIssue_error_searchReferNum");
					return;
				}
			}
			if(searchStartDate != null && searchEndDate != null) {
				if(searchStartDate.compareTo(searchEndDate) > 0) {
					JSFUtil.addError(bundle, null, ":frm:searhStart", "ticketIssue_error_searchDate");
					return;
				}
			}
			searchDto.setStartDate(searchStartDate);
			searchDto.setEndDate(searchEndDate);
			if(searchRefNo != null && searchRefNo.length() > 0) {
				searchDto.setReferNumber(new Long(searchRefNo));
			} 
			if(searchAgent != null) {
				searchDto.setAgentId(searchAgent.getId());
			}
			searchDto.setStatus(searchStatus);
			if(searchTicketNo != null && !searchTicketNo.isEmpty()) {
				searchDto.setTicketNo(searchTicketNo);
			}
			if(searchRoute != null && !searchRoute.isEmpty()) {
				searchDto.setRoute(searchRoute);
			}
			if(searchInputSD != null && !searchInputSD.isEmpty()) {
				searchDto.setInputSD(searchInputSD);
			}
		}else{
			searchDto = null;
		}
		
		ticketIssueList = new TicketIssueModel(ticketIssueService, searchDto);
	}
	
	public void clearSearch() {
		resetSearchCondition();
		searchTicketIssue(false);
	}
	
	public String gotoEditDetailScreen() {
		initDetail();
		return "TicketDetailListScreen?faces-redirect=true";
	}
	
	public void initDetail() {
		resetDetailFields();
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
			detailAmtUsd = totalAmtUsd;
			detailAmtVnd = totalAmtVnd;
			displayTotalUsd = usd.format(detailAmtUsd);
			displayTotalVnd = vnd.format(detailAmtVnd);
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
		if(!checkParam(false)) {
			return;
		}
		TicketIssueDetail detail = new TicketIssueDetail();
		detail.setTicketNo(inputTicketNo);
		detail.setRoute(inputRoute.toUpperCase());
		detail.setQuantity(new Long(inputQuantity));
		detail.setPrice(inputPrice);
		detail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
		inputTicketDetails.add(detail);
		refreshTotalAmount();
		resetDetailFields();
	}
	
	public void updateDetail() {
		//check update
		if(!checkParam(true)) {
			return;
		}
		if(selectedTicketIssueDetail != null) {
			selectedTicketIssueDetail.setTicketNo(inputTicketNo);
			selectedTicketIssueDetail.setRoute(inputRoute.toUpperCase());
			selectedTicketIssueDetail.setQuantity(new Long(inputQuantity));
			selectedTicketIssueDetail.setPrice(inputPrice);
			selectedTicketIssueDetail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
			refreshTotalAmount();
		}
		resetDetailFields();
	}
	
	public void deleteDetail() {
		if(selectedTicketIssueDetail != null) {
			inputTicketDetails.remove(selectedTicketIssueDetail);
			refreshTotalAmount();
			selectedTicketIssueDetail = null;
			resetDetailFields();
		} else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
	}
	
	public String okDetail() {
		ticketIssueDetails = inputTicketDetails;
		inputTicketDetails = null;
		totalAmtUsd = detailAmtUsd;
		totalAmtVnd = detailAmtVnd;
		detailAmtUsd = null;
		detailAmtVnd = null;
		resetDetailFields();
		inputTicketDetails = null;
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public String cancelDetail() {
		inputTicketDetails = null;
		detailAmtUsd = null;
		detailAmtVnd = null;
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
		resetDetailFields();
		return "TicketIssueScreen?faces-redirect=true";
	}
	
	public void clearDetail() {
		resetDetailFields();
	}
	
	public void chooseAgent() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("selectAgent", options, null);
	}
	
	public void onAgentChosen(SelectEvent event) {
		selectedAgent = (Agent)event.getObject();
		if(selectedAgent != null) {
			displayAgentName = null;
			if(!"1".equals(selectedAgent.getRetailFlag())) {
				displayAgentName = selectedAgent.getName();
			}
			inputAgentCode = selectedAgent.getCode();
		}
	}
	
	public void onSearchAgentChosen(SelectEvent event) {
		searchAgent = (Agent)event.getObject();
		if(searchAgent != null) {
			searchAgentName = searchAgent.getName();
			searchAgentCode = searchAgent.getCode();
		}
	}
	
	public String registerTicketIssue() {
		if(!checkValidTicketIssue(true)) {
			return null;
		}
		TicketIssue ticketIssue = new TicketIssue();
		ticketIssue.setIssueDate(issueDate);
		ticketIssue.setRate(selectedRate);
		ticketIssue.setPaymentType(paymentType);
		ticketIssue.setAgent(selectedAgent);
		ticketIssue.setTicketIssueDetail(ticketIssueDetails);
		ticketIssue.setStatus("0");//no tax invoice yet
		ticketIssue.setAmountUsd(totalAmtUsd);
		if(loginBean.getLoginUser() != null) {
			ticketIssue.setInputEmpId(loginBean.getLoginUser().getId());
		}
		ticketIssue.setRetailCustomerName(null);
		if("1".equals(selectedAgent.getRetailFlag())) {
			ticketIssue.setRetailCustomerName(displayAgentName);
		}
		ticketIssueService.insertTicketIssue(ticketIssue);
		selectedTicketIssue = ticketIssueService.findTicketIssueById(ticketIssue.getId());
		return gotoViewTicketIssueScreen();
	}
	
	public String saveTicketIssue() {
		if(!checkValidTicketIssue(false)) {
			return null;
		}
		TicketIssue ticketIssue = new TicketIssue();
		ticketIssue.setIssueDate(issueDate);
		ticketIssue.setRate(selectedRate);
		ticketIssue.setPaymentType(paymentType);
		ticketIssue.setAgent(selectedAgent);
		ticketIssue.setTicketIssueDetail(ticketIssueDetails);
		ticketIssue.setStatus("3");//save temp
		ticketIssue.setAmountUsd(totalAmtUsd);
		if(loginBean.getLoginUser() != null) {
			ticketIssue.setInputEmpId(loginBean.getLoginUser().getId());
		}
		ticketIssue.setRetailCustomerName(null);
		if("1".equals(selectedAgent.getRetailFlag())) {
			ticketIssue.setRetailCustomerName(displayAgentName);
		}
		ticketIssueService.insertTicketIssue(ticketIssue);
		selectedTicketIssue = null;
		//resetSearchCondition();
		searchTicketIssue(false);
		return "TicketIssueListScreen?faces-redirect=true";
	}
	
	public String cancelRegisterTicketIssue() {
		resetMainFields();
		selectedTicketIssue = null;
		ticketIssueDetails = null;
		//resetSearchCondition();
		searchTicketIssue(false);
		return "TicketIssueListScreen?faces-redirect=true";
	}
	
	public String linkTaxInvoice() {
		if(selectedTicketIssue.getTaxInvoiceId() != null) {
			if(taxInvoiceIssueBean != null) {
				taxInvoiceIssueBean.setSelectedTaxInvIssue(selectedTicketIssue.getTaxInvoiceIssue());
				taxInvoiceIssueBean.gotoViewTaxInvIssueScreen();
				taxInvoiceIssueBean.setFromTicketIssue(true);
			}
			return "TaxInvoiceIssueScreen?faces-redirect=true&taxIssueId=" + selectedTicketIssue.getTaxInvoiceId();
		}
		if(authorService.findEmpAuthor(loginBean.getLoginUser().getId(), "0002") == null) {//edit tax invoice authority
			JSFUtil.addError(bundle, null, null, "ticketIssue_error_noCreateTaxInvAuthor");
			return null;
		}
		if(taxInvoiceIssueBean != null) {
			taxInvoiceIssueBean.setRefNo(selectedTicketIssue.getId());
			taxInvoiceIssueBean.loadIssueDataWithRefNo();
			taxInvoiceIssueBean.setFromTicketIssue(true);
		}
		return "TaxInvoiceIssueScreen?faces-redirect=true&refNo=" + selectedTicketIssue.getId();
	}
	
	public void onInputAgentCodeBlur() {
		if(inputAgentCode == null || inputAgentCode.trim().equals("")) {
			displayAgentName = null;
			selectedAgent = null;
			return;
		}
		selectedAgent = agentService.findAgentByCode(inputAgentCode, null, null);
		if(selectedAgent == null) {
			displayAgentName = null;
			return;
		}
		displayAgentName = null;
		if(!"1".equals(selectedAgent.getRetailFlag())) {
			displayAgentName = selectedAgent.getName();
		}
	}
	
	public void onInputSearchAgentCodeBlur() {
		if(searchAgentCode == null || searchAgentCode.trim().equals("")) {
			searchAgentName = null;
			searchAgent = null;
			return;
		}
		searchAgent = agentService.findAgentByCode(searchAgentCode, null, null);
		if(searchAgent == null) {
			searchAgentName = null;
			return;
		}
		searchAgentName = searchAgent.getName();
	}
	
	private boolean checkParam(boolean isUpdate) {
		boolean isValid = true;
		//check duplicate ticket number
		for(TicketIssueDetail detail : inputTicketDetails) {
			if(inputTicketNo.equals(detail.getTicketNo())
					&& (selectedTicketIssueDetail.equals(detail) && !isUpdate)) {
				JSFUtil.addError(bundle, null, ":frm:ticketNo", "ticketIssue_error_TicketNoDuplicate");
				isValid = false;
				break;
			}
		}
		if(inputQuantity <= 0) {
			JSFUtil.addError(bundle, null, ":frm:quantity", "ticketIssue_error_TicketQuantity");
			isValid = false;
		}
		return isValid;
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

	public String getInputAgentCode() {
		return inputAgentCode;
	}

	public void setInputAgentCode(String inputAgentCode) {
		this.inputAgentCode = inputAgentCode;
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

	public BigDecimal getDetailAmtUsd() {
		return detailAmtUsd;
	}

	public void setDetailAmtUsd(BigDecimal detailAmtUsd) {
		this.detailAmtUsd = detailAmtUsd;
	}

	public BigDecimal getDetailAmtVnd() {
		return detailAmtVnd;
	}

	public void setDetailAmtVnd(BigDecimal detailAmtVnd) {
		this.detailAmtVnd = detailAmtVnd;
	}

	public String getDisplayTotalUsd() {
		return displayTotalUsd;
	}

	public void setDisplayTotalUsd(String displayTotalUsd) {
		this.displayTotalUsd = displayTotalUsd;
	}

	public String getDisplayTotalVnd() {
		return displayTotalVnd;
	}

	public void setDisplayTotalVnd(String displayTotalVnd) {
		this.displayTotalVnd = displayTotalVnd;
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

	public String getSearchTicketNo() {
		return searchTicketNo;
	}

	public void setSearchTicketNo(String searchTicketNo) {
		this.searchTicketNo = searchTicketNo;
	}

	public String getSearchRoute() {
		return searchRoute;
	}

	public void setSearchRoute(String searchRoute) {
		this.searchRoute = searchRoute;
	}

	public String getSearchInputSD() {
		return searchInputSD;
	}

	public void setSearchInputSD(String searchInputSD) {
		this.searchInputSD = searchInputSD;
	}

	public TicketIssueServiceBean getTicketIssueService() {
		return ticketIssueService;
	}

	public void setTicketIssueService(TicketIssueServiceBean ticketIssueService) {
		this.ticketIssueService = ticketIssueService;
	}
	
	private void resetDetailFields() {
		selectedTicketIssueDetail = null;
		inputTicketNo = null;
		inputRoute = null;
		inputQuantity = 0;
		inputPrice = null;
	}
	
	private void resetMainFields() {
		screenMode = NEW;
		issueDate = null;
		roe = null;
		paymentType = "0";
		selectedAgent = null;
		displayAgentName = null;
		inputAgentCode = null;
	}
	
	private boolean checkValidTicketIssue(boolean checkJournal) {
		boolean isValid = true;
		if(displayAgentName == null && selectedAgent == null) {
			JSFUtil.addError(bundle, null, ":frm:agentName", "ticketIssue_error_agent");
			JSFUtil.setInvalidComponent(":frm:agentCode");
			isValid = false;
		}
		if(ticketIssueDetails == null || ticketIssueDetails.isEmpty()) {
			JSFUtil.addError(bundle, null, null, "ticketIssue_error_inputDetail");
			isValid = false;
		}
		if(isValid && checkJournal) {
			DailyTicketJournal journal = journalService.findTicketJournalByDate(issueDate);
			if(journal != null && "1".equals(journal.getStatus())) {//closed
				JSFUtil.addError(bundle, null, ":frm:issueDate_input", "ticketIssue_error_journalClosed");
				isValid = false;
			}
		}
		return isValid;
	}
	
	private void refreshTotalAmount() {
		detailAmtUsd = new BigDecimal(0);
		detailAmtVnd = new BigDecimal(0);
		if(roe != null) {
			BigDecimal thousandUnit = new BigDecimal(1000);
			for(TicketIssueDetail detail : inputTicketDetails) {
				BigDecimal quan = new BigDecimal(detail.getQuantity());
				BigDecimal vndPrice = detail.getPrice().multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
				detailAmtUsd = detailAmtUsd.add(detail.getAmount());
				detailAmtVnd = detailAmtVnd.add(vndPrice.multiply(quan));
			}
			//detailAmtVnd = detailAmtUsd.multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
		}
		displayTotalUsd = usd.format(detailAmtUsd);
		displayTotalVnd = vnd.format(detailAmtVnd);
	}
	
	private String getPaymentTypeString(String paymentType) {
		if("0".equals(paymentType)) {//Cash
			return bundle.getString("ticketIssue_paymentType_cash");
		} else if("1".equals(paymentType)) {//Credit card
			return bundle.getString("ticketIssue_paymentType_credit");
		} else if("2".equals(paymentType)) {//Bank transfer
			return bundle.getString("ticketIssue_paymentType_transfer");
		} else if("3".equals(paymentType)) {//Account receivable
			return bundle.getString("ticketIssue_paymentType_account");
		}
		return null;
	}
	
	private void resetSearchCondition() {
		Date searchDate = new Date();
		searchStartDate = DateUtil.getFirstDateOfMonth(searchDate);
		searchEndDate = DateUtil.getLastDateOfMonth(searchDate);
		searchAgent = null;
		searchAgentCode = null;
		searchAgentName = null;
		searchStatus = "ALL";
		searchTicketNo = null;
		searchRoute = null;
		searchInputSD = loginBean.getLoginUser().getUserId();
		searchRefNo = null;
	}
	
	private void refreshSumMoney() {
		totalAmtVnd = new BigDecimal(0);
		if(roe != null) {
			BigDecimal thousandUnit = new BigDecimal(1000);
			for(TicketIssueDetail detail : ticketIssueDetails) {
				BigDecimal vndPrice = detail.getPrice().multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
				totalAmtVnd = totalAmtVnd.add(vndPrice.multiply(new BigDecimal(detail.getQuantity())));
			}
			//totalAmtVnd = totalAmtUsd.multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
		}
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
	}

	public boolean isJournalClosed() {
		return journalClosed;
	}

	public void setJournalClosed(boolean journalClosed) {
		this.journalClosed = journalClosed;
	}

	public boolean isTodayRateNotSet() {
		return todayRateNotSet;
	}

	public void setTodayRateNotSet(boolean todayRateNotSet) {
		this.todayRateNotSet = todayRateNotSet;
	}

	public boolean isHasEditAuthority() {
		return hasEditAuthority;
	}

	public void setHasEditAuthority(boolean hasEditAuthority) {
		this.hasEditAuthority = hasEditAuthority;
	}

}
