package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.chinaair.dto.TaxInvoiceIssueSearchDto;
import com.chinaair.entity.Agent;
import com.chinaair.entity.Company;
import com.chinaair.entity.InvoiceStockDetail;
import com.chinaair.entity.Rate;
import com.chinaair.entity.TaxInvoiceIssue;
import com.chinaair.entity.TaxInvoiceIssueDetail;
import com.chinaair.entity.TicketIssue;
import com.chinaair.entity.TicketIssueDetail;
import com.chinaair.services.AgentServiceBean;
import com.chinaair.services.AuthorityServiceBean;
import com.chinaair.services.CompanyServiceBean;
import com.chinaair.services.RateServiceBean;
import com.chinaair.services.TaxInvoiceIssueServiceBean;
import com.chinaair.services.TaxInvoiceServiceBean;
import com.chinaair.services.TicketIssueServiceBean;
import com.chinaair.util.DateUtil;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;
import com.chinaair.util.MoneyUtil;
import com.chinaair.webDto.TaxInvoiceIssueDetailDto;
import com.chinaair.webDto.TaxInvoiceIssueModel;

@ConversationScoped
@Named("taxInvIssueBean")
public class TaxInvoiceIssueBean implements Serializable {
	
	private static final long serialVersionUID = -1354249683414464554L;
	
	private static final String NEW = "0";
	
	private static final String VIEW = "1";
	
	private static final String EDIT = "2";
	
	private static final DecimalFormat usd = new DecimalFormat("#,##0.00");
	
	private static final DecimalFormat vnd = new DecimalFormat("#,###");

	private ResourceBundle bundle;
	
	private Date searchStartDate;
	
	private Date searchEndDate;
	
	private String searchRefNo;
	
	private String searchVatCode;
	
	private Agent selectedAgent;
	
	private Company selectedCompany;
	
	private String searchStatus;
	
	private String searchTicketNo;
	
	private String searchCompanyName;
	
	private String searchTaxInvNo;
	
	private LazyDataModel<TaxInvoiceIssue> taxInvIssueList;
	
	private TaxInvoiceIssue selectedTaxInvIssue;
	
	private String screenMode;
	
	private Date issueDate;
	
	private Rate selectedRate;
	
	private BigDecimal roe;
	
	private String paymentType;
	
	private Long refNo;
	
	private Long invoiceStockDetailId;
	
	private String invoiceStockDetailNo;
	
	private List<InvoiceStockDetail> invStockDetails;
	
	private String companyName;
	
	private String vatCode;
	
	private String address;
	
	private List<TaxInvoiceIssueDetail> taxInvoiceIssueDetails;
	
	private String displayTotalUsd;
	
	private String displayTotalVnd;
	
	private String inputTicketNo;
	
	private String inputRoute;
	
	private int inputQuantity;
	
	private BigDecimal inputPrice;
	
	private List<TaxInvoiceIssueDetail> inputTaxInvoiceIssueDetails;
	
	private TaxInvoiceIssueDetail selectedTaxInvoiceIssueDetail;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private BigDecimal detailAmtUsd;
	
	private BigDecimal detailAmtVnd;
	
	private String roundUp;
	
	private boolean fromTicketIssue;
	
	private boolean cargo;
	
	private boolean todayRateNotSet;
	
	private boolean hasEditAuthority;
	
	private boolean useManualRate;
	
	@EJB
	private TaxInvoiceIssueServiceBean taxInvoiceIssueService;
	
	@EJB
	private CompanyServiceBean companyService;
	
	@EJB
	private AgentServiceBean agentService;
	
	@EJB
	private TaxInvoiceServiceBean taxInvoiceService;
	
	@EJB
	private RateServiceBean rateService;
	
	@EJB
	private TicketIssueServiceBean ticketIssueService;
	
	@EJB
	private AuthorityServiceBean authorService;
	
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
		hasEditAuthority = false;
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		initSearchParams();
		fromTicketIssue = false;
		Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(requestParams.containsKey("cargo") && "true".equals(requestParams.get("cargo"))) {
			cargo = true;
		}
		if(requestParams.containsKey("refNo")) {
			refNo = new Long(requestParams.get("refNo"));
			loadIssueDataWithRefNo();
			fromTicketIssue = true;
		} else if(requestParams.containsKey("taxIssueId")) {
			Long issueId = new Long(requestParams.get("taxIssueId"));
			selectedTaxInvIssue = taxInvoiceIssueService.findTaxInvoiceIssueById(issueId);
			gotoViewTaxInvIssueScreen();
			fromTicketIssue = true;
		} else {
			searchTaxInvoiceIssue(false);
		}
		if(authorService.findEmpAuthor(loginBean.getLoginUser().getId(), "0002") != null) {
			hasEditAuthority = true;
		}
	}
	
	public String gotoNewTaxInvIssueScreen() {
		screenMode = NEW;
		refNo = null;
		issueDate = new Date();
		useManualRate = false;
		todayRateNotSet = false;
		selectedRate = rateService.getTodayRate();
		if(selectedRate == null) {
			todayRateNotSet = true;
			selectedRate = rateService.getNearestRate();
		}
		roe = null;
		if(selectedRate != null) {
			roe = selectedRate.getRate();
		}
		if(cargo) {
			paymentType = "2";//transfer
			roundUp = "0";
		} else {
			paymentType = "0";//cash type
			roundUp = "1";
		}
		invoiceStockDetailId = null;
		companyName = null;
		vatCode = null;
		address = null;
		taxInvoiceIssueDetails = null;
		totalAmtUsd = new BigDecimal(0);
		totalAmtVnd = new BigDecimal(0);
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
		invStockDetails = taxInvoiceService.findUsableInvoiceStockDetail(cargo);
		return "TaxInvoiceIssueScreen?faces-redirect=true";
	}
	
	public String gotoViewTaxInvIssueScreen() {
		if(selectedTaxInvIssue == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		screenMode = VIEW;
		todayRateNotSet = false;
		roundUp = selectedTaxInvIssue.getRoundUp();
		selectedTaxInvIssue = taxInvoiceIssueService.findTaxInvoiceIssueById(selectedTaxInvIssue.getId());
		invoiceStockDetailNo = selectedTaxInvIssue.getInvoiceStockDetail().getInvoiceStockNo();
		refNo = selectedTaxInvIssue.getTicketIssueId();
		issueDate = selectedTaxInvIssue.getIssueDate();
		useManualRate = false;
		if("1".equals(selectedTaxInvIssue.getUseManualRate())) {
			useManualRate = true;
			roe = selectedTaxInvIssue.getManualRate();
			selectedRate = null;
		} else {
			selectedRate = selectedTaxInvIssue.getRate();
			roe = null;
			if(selectedRate != null) {
				roe = selectedRate.getRate();
			}
		}
		paymentType = selectedTaxInvIssue.getPaymentType();
		invoiceStockDetailId = selectedTaxInvIssue.getInvoiceStockDetailId();
		companyName = selectedTaxInvIssue.getCompanyName();
		vatCode = selectedTaxInvIssue.getVatCode();
		address = selectedTaxInvIssue.getAddress();
		taxInvoiceIssueDetails = selectedTaxInvIssue.getTaxInvoiceIssueDetail();
		totalAmtUsd = selectedTaxInvIssue.getAmtUsd();
		changeRoundUp();
		return "TaxInvoiceIssueScreen?faces-redirect=true";
	}
	
	public String storeTaxInvoice() {
		if(!checkTaxInvoiceIssue()) {
			return null;
		}
		TaxInvoiceIssue newInvoiceIssue = prepareTaxInvoiceForInsert();
		newInvoiceIssue.setStatus("3");
		taxInvoiceIssueService.insertTaxInvoiceIssue(newInvoiceIssue);
		//searchTaxInvoiceIssue();
		selectedTaxInvIssue = newInvoiceIssue;
		return gotoViewTaxInvIssueScreen();
	}
	
	public void printTaxInvIssueInfo() {
		if(selectedTaxInvIssue == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return;
		}
		if("2".equals(selectedTaxInvIssue.getStatus())) {//voided
			JSFUtil.addError(bundle, null, null, "resource_error_printVoidedRecord");
			return;
		}
		TaxInvoiceIssue issue = taxInvoiceIssueService.findTaxInvoiceIssueById(selectedTaxInvIssue.getId());
		Map<String, Object> params = new HashMap<>();
		params.put("systemClass", loginBean.getSystemClass());
		params.put("isCargo", issue.getCargo());
		params.put("companyName", issue.getCompanyName());
		params.put("vatCode", issue.getVatCode());
		params.put("address", issue.getAddress());
		
		List<TaxInvoiceIssueDetailDto> detailDtos = new ArrayList<>();
		BigDecimal reportROE = null;
		if("1".equals(issue.getUseManualRate())) {
			reportROE = issue.getManualRate();
		} else {
			reportROE = issue.getRate().getRate();
		}
		params.put("roe", reportROE);
		BigDecimal thousandUnit = new BigDecimal(1000);
		BigDecimal totalVnd = new BigDecimal(0);
		for(TaxInvoiceIssueDetail detail : issue.getTaxInvoiceIssueDetail()) {
			TaxInvoiceIssueDetailDto detailDto = new TaxInvoiceIssueDetailDto();
			detailDto.setTicketNo(detail.getTicketNo());
			detailDto.setAmount(detail.getAmount());
			detailDto.setQuantity(detail.getQuantity());
			detailDto.setPrice(detail.getPrice());
			detailDto.setRoute(detail.getRoute());
			BigDecimal vndAmt = new BigDecimal(0);
			BigDecimal vndPrice = new BigDecimal(0);
			if("1".equals(issue.getRoundUp())) {
				vndPrice = detail.getPrice().multiply(reportROE).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
			} else {
				vndPrice = detail.getPrice().multiply(reportROE).setScale(0, RoundingMode.HALF_UP);
			}
			vndAmt = vndPrice.multiply(new BigDecimal(detail.getQuantity()));
			detailDto.setVndAmt(vndAmt);
			detailDto.setVndPrice(vndPrice);
			detailDtos.add(detailDto);
			totalVnd = totalVnd.add(vndAmt);
			
		}
		params.put("totalAmount", totalVnd);
		String moneyText = MoneyUtil.tranlate(totalVnd.toPlainString());
		if(moneyText != null && !moneyText.isEmpty()) {
			moneyText = moneyText.substring(0, 1).toUpperCase() + moneyText.substring(1);
		}
		params.put("totalAmountText", moneyText);
		
		params.put("paymentType", getPaymentType(issue.getPaymentType()));
		params.put("printDate", issue.getIssueDate());
		params.put("detail", detailDtos);
		params.put(JRParameter.REPORT_LOCALE, Locale.ITALY);
		if(issue.getCreateEmp() != null) {
			params.put("inputEmpName", issue.getCreateEmp().getEmpName());
		}
		try {
			ArrayList<String> dummyList = new ArrayList<>();
			dummyList.add("dummy");
			if("1".equals(issue.getCargo())) {
				JasperUtil.createReportAndDownloadPDF("TaxInvoiceIssueCgo", dummyList, params);
			} else {
				JasperUtil.createReportAndDownloadPDF("TaxInvoiceIssue", dummyList, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void searchTaxInvoiceIssue(boolean isSearchAll) {
		//kiem tra dieu kiem search
		TaxInvoiceIssueSearchDto dto = new TaxInvoiceIssueSearchDto();
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
			
			dto.setStartDate(searchStartDate);
			dto.setEndDate(searchEndDate);
			if(searchRefNo != null && !searchRefNo.isEmpty()) {
				dto.setReferNumber(searchRefNo);
			}
			if(searchVatCode != null && !searchVatCode.isEmpty()) {
				dto.setVatCode(searchVatCode);
			}
			if(searchTicketNo != null && !searchTicketNo.isEmpty()) {
				dto.setTicketNo(searchTicketNo);
			}
			dto.setStatus(searchStatus);
			dto.setCompanyName(searchCompanyName);
			dto.setTaxInvNo(searchTaxInvNo);
		}
		dto.setCargo(cargo);
		
		taxInvIssueList = new TaxInvoiceIssueModel(taxInvoiceIssueService,dto);
	}
	
	public void clearSearch() {
		initSearchParams();
		searchTaxInvoiceIssue(false);
	}
	
	public void chooseAgent() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        String selectType = "";
        if(!cargo) {
        	selectType = "pax";
        } else {
        	selectType = "cargo";
        }
        Map<String, List<String>> params = new HashMap<>();
        List<String> valArr = new ArrayList<>();
        valArr.add(selectType);
        params.put("type", valArr);
		RequestContext.getCurrentInstance().openDialog("selectAgent", options, params);
	}
	
	public void chooseCompany() {
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("selectCompany", options, null);
	}
	
	public void onAgentChosen(SelectEvent event) {
		selectedAgent = (Agent)event.getObject();
		if(selectedAgent != null) {
			companyName = selectedAgent.getCompany();
			vatCode = selectedAgent.getVat_code();
			address = selectedAgent.getAddress();
		}
	}
	
	public void onCompanyChosen(SelectEvent event) {
		selectedCompany = (Company)event.getObject();
		if(selectedCompany != null) {
			companyName = selectedCompany.getCompany();
			vatCode = selectedCompany.getVat();
			address = selectedCompany.getAddress();
		}
	}
	
	public void onchangeUserManualRate() {
		if(useManualRate) {
			if(roe == null) {
				selectedRate = rateService.getNearestRate();
				if(selectedRate != null) {
					roe = selectedRate.getRate();
				}
			}
			selectedRate = null;
		} else {
			selectedRate = rateService.getRateByDate(issueDate);
			roe = null;
			if(selectedRate != null) {
				roe = selectedRate.getRate();
			}
		}
		refreshTotalAmount();
	}
	
	public void onchangeIssueDate() {
		/*if(!useManualRate) {
			selectedRate = rateService.getRateByDate(issueDate);
			roe = null;
			if(selectedRate != null) {
				roe = selectedRate.getRate();
			}
		}*/
	}
	
	public void updateCompanyInfo(ActionEvent actionEvent) {
		if(vatCode.length() < 10 || vatCode.length() > 14) {
			JSFUtil.addInfo(bundle, null, ":frm:vatCode", "taxInvoice_m_vatCodeLength");
			return;
		}
		try {
			if(selectedAgent != null) {
				selectedAgent.setVat_code(vatCode);
				selectedAgent.setCompany(companyName);
				selectedAgent.setAddress(address);
				agentService.update(selectedAgent);
			} else {
				if(selectedCompany != null) {
					selectedCompany.setVat(vatCode);
					selectedCompany.setCompany(companyName);
					selectedCompany.setAddress(address);
					companyService.update(selectedCompany);
				} else {
					Company newComp = new Company();
					newComp.setCompany(companyName);
					newComp.setVat(vatCode);
					newComp.setAddress(address);
					companyService.insert(newComp);
				}
			}
			JSFUtil.addInfo(bundle, null, null, "taxInvoice_m_companyUpdated");
		} catch(Exception e) {
			JSFUtil.addInfo(bundle, null, null, "taxInvoice_m_companyFailUpdated");
		}
	}
	
	public String gotoEditDetailScreen() {
		if((useManualRate && roe == null) || (!useManualRate && selectedRate == null)) {
			JSFUtil.addError(bundle, null, null, "taxInvoice_m_rateError");
			return null;
		}
		initDetail();
		return "TaxInvoiceIssueDetailListScreen?faces-redirect=true";
	}
	
	public void initDetail() {
		resetDetailFields();
		if(inputTaxInvoiceIssueDetails == null) {
			inputTaxInvoiceIssueDetails = new ArrayList<>();
		}
		if(taxInvoiceIssueDetails != null && !taxInvoiceIssueDetails.isEmpty()) {
			for(TaxInvoiceIssueDetail item : taxInvoiceIssueDetails) {
				TaxInvoiceIssueDetail newItem = new TaxInvoiceIssueDetail();
				newItem.setAmount(item.getAmount());
				//newItem.setId(item.getId());
				//newItem.setLastUpdate(item.getLastUpdate());
				newItem.setPrice(item.getPrice());
				newItem.setQuantity(item.getQuantity());
				newItem.setRoute(item.getRoute());
				//newItem.setTicketIssue(item.getTicketIssue());
				newItem.setTicketNo(item.getTicketNo());
				inputTaxInvoiceIssueDetails.add(newItem);
			}
			detailAmtUsd = totalAmtUsd;
			detailAmtVnd = totalAmtVnd;
			displayTotalUsd = usd.format(detailAmtUsd);
			displayTotalVnd = vnd.format(detailAmtVnd);
		}
	}
	
	public String registerTaxInvoice() {
		if(!checkTaxInvoiceIssue()) {
			return null;
		}
		TaxInvoiceIssue newInvoiceIssue = prepareTaxInvoiceForInsert();
		taxInvoiceIssueService.insertTaxInvoiceIssue(newInvoiceIssue);
		//searchTaxInvoiceIssue();
		selectedTaxInvIssue = newInvoiceIssue;
		return gotoViewTaxInvIssueScreen();
		//return "TaxInvoiceIssueListScreen?faces-redirect=true";
	}
	
	public void editTaxInvoice() {
		screenMode = EDIT;
		invStockDetails = taxInvoiceService.findUsableInvoiceStockDetail(cargo);
		invStockDetails.add(0, selectedTaxInvIssue.getInvoiceStockDetail());
		/*todayRateNotSet = false;
		selectedRate = rateService.getTodayRate();
		if(selectedRate == null) {
			todayRateNotSet = true;
			selectedRate = rateService.getNearestRate();
		}
		roe = null;
		if(selectedRate != null) {
			roe = selectedRate.getRate();
		}*/
	}
	
	public String updateTaxInvoice() {
		if(!checkTaxInvoiceIssue()) {
			return null;
		}
		prepareTaxInvoiceForUpdate();
		taxInvoiceIssueService.updateTaxInvoiceIssue(selectedTaxInvIssue);
		/*searchTaxInvoiceIssue();
		return "TaxInvoiceIssueListScreen?faces-redirect=true";*/
		return gotoViewTaxInvIssueScreen();
	}
	
	public String useTaxInvoice() {
		prepareTaxInvoiceForUpdate();
		selectedTaxInvIssue.setStatus("0");
		taxInvoiceIssueService.updateTaxInvoiceIssue(selectedTaxInvIssue);
		/*searchTaxInvoiceIssue();
		return "TaxInvoiceIssueListScreen?faces-redirect=true";*/
		return gotoViewTaxInvIssueScreen();
	}
	
	public String voidTaxInvoice() {
		taxInvoiceIssueService.voidTaxInvoiceIssue(selectedTaxInvIssue.getId());
		searchTaxInvoiceIssue(true);
		return "TaxInvoiceIssueListScreen?faces-redirect=true";
	}
	
	public String cancelRegisterTaxInvoice() {
		searchTaxInvoiceIssue(false);
		if(fromTicketIssue) {
			return "TicketIssueScreen?faces-redirect=true";
		}
		return "TaxInvoiceIssueListScreen?faces-redirect=true";
	}
	
	public void changeRoundUp() {
		totalAmtVnd = new BigDecimal(0);
		if(roe != null) {
			BigDecimal thousandUnit = new BigDecimal(1000);
			for(TaxInvoiceIssueDetail detail : taxInvoiceIssueDetails) {
				BigDecimal vndPrice = new BigDecimal(0);
				if("1".equals(roundUp)) {
					vndPrice = detail.getPrice().multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
				} else {
					vndPrice = detail.getPrice().multiply(roe).setScale(0, RoundingMode.HALF_UP);
				}
				totalAmtVnd = totalAmtVnd.add(vndPrice.multiply(new BigDecimal(detail.getQuantity())));
			}
		}
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
	}
	
	public String okDetail() {
		if(inputTaxInvoiceIssueDetails == null
				|| inputTaxInvoiceIssueDetails.isEmpty()) {
			JSFUtil.addError(bundle, null, null, "ticketIssue_error_TicketNoDetail");
			return null;
		}
		taxInvoiceIssueDetails = inputTaxInvoiceIssueDetails;
		inputTaxInvoiceIssueDetails = null;
		totalAmtUsd = detailAmtUsd;
		totalAmtVnd = detailAmtVnd;
		detailAmtUsd = null;
		detailAmtVnd = null;
		resetDetailFields();
		inputTaxInvoiceIssueDetails = null;
		return "TaxInvoiceIssueScreen?faces-redirect=true";
	}
	
	public String cancelDetail() {
		inputTaxInvoiceIssueDetails = null;
		detailAmtUsd = null;
		detailAmtVnd = null;
		displayTotalUsd = usd.format(totalAmtUsd);
		displayTotalVnd = vnd.format(totalAmtVnd);
		resetDetailFields();
		return "TaxInvoiceIssueScreen?faces-redirect=true";
	}
	
	public void insertDetail() {
		//check insert
		if(!checkParam(false)) {
			return;
		}
		TaxInvoiceIssueDetail detail = new TaxInvoiceIssueDetail();
		detail.setTicketNo(inputTicketNo);
		detail.setRoute(inputRoute.toUpperCase());
		detail.setQuantity(new Long(inputQuantity));
		detail.setPrice(inputPrice);
		detail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
		inputTaxInvoiceIssueDetails.add(detail);
		refreshTotalAmount();
		resetDetailFields();
	}
	
	public void updateDetail() {
		//check update
		if(!checkParam(true)) {
			return;
		}
		if(selectedTaxInvoiceIssueDetail != null) {
			selectedTaxInvoiceIssueDetail.setTicketNo(inputTicketNo);
			selectedTaxInvoiceIssueDetail.setRoute(inputRoute.toUpperCase());
			selectedTaxInvoiceIssueDetail.setQuantity(new Long(inputQuantity));
			selectedTaxInvoiceIssueDetail.setPrice(inputPrice);
			selectedTaxInvoiceIssueDetail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
			refreshTotalAmount();
			resetDetailFields();
		} else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
	}
	
	public void deleteDetail() {
		if(selectedTaxInvoiceIssueDetail != null) {
			inputTaxInvoiceIssueDetails.remove(selectedTaxInvoiceIssueDetail);
			refreshTotalAmount();
			selectedTaxInvoiceIssueDetail = null;
			resetDetailFields();
		} else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
		}
	}
	
	public void clearDetail() {
		resetDetailFields();
	}
	
	public void onSelectTaxInvoiceDetail() {
		if(selectedTaxInvoiceIssueDetail != null) {
			inputTicketNo = selectedTaxInvoiceIssueDetail.getTicketNo().toString();
			inputRoute = selectedTaxInvoiceIssueDetail.getRoute();
			inputQuantity = selectedTaxInvoiceIssueDetail.getQuantity().intValue();
			inputPrice = selectedTaxInvoiceIssueDetail.getPrice();
		}
	}
	
	public void onChangeRoe() {
		refreshTotalAmount();
	}
	
	private void resetDetailFields() {
		selectedTaxInvoiceIssueDetail = null;
		inputTicketNo = null;
		inputRoute = null;
		inputQuantity = 0;
		inputPrice = null;
	}
	
	private void refreshTotalAmount() {
		detailAmtUsd = new BigDecimal(0);
		detailAmtVnd = new BigDecimal(0);
		if(roe != null) {
			BigDecimal thousandUnit = new BigDecimal(1000);
			List<TaxInvoiceIssueDetail> details = inputTaxInvoiceIssueDetails;
			if(details == null) {
				details = taxInvoiceIssueDetails;
			}
			if(details != null) {
				for(TaxInvoiceIssueDetail detail : details) {
					detailAmtUsd = detailAmtUsd.add(detail.getAmount());
					BigDecimal vndPrice = new BigDecimal(0);
					if("1".equals(roundUp)) {
						vndPrice = detail.getPrice().multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
					} else {
						vndPrice = detail.getPrice().multiply(roe).setScale(0, RoundingMode.HALF_UP);
					}
					detailAmtVnd = detailAmtVnd.add(vndPrice.multiply(new BigDecimal(detail.getQuantity())));
				}
			}
		}
		displayTotalUsd = usd.format(detailAmtUsd);
		displayTotalVnd = vnd.format(detailAmtVnd);
	}
	
	private boolean checkParam(boolean isUpdate) {
		boolean isValid = true;
		//check duplicate ticket number
		for(TaxInvoiceIssueDetail detail : inputTaxInvoiceIssueDetails) {
			if(inputTicketNo.equals(detail.getTicketNo())
					&& (selectedTaxInvoiceIssueDetail.equals(detail) && !isUpdate)) {
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
	
	public void loadIssueDataWithRefNo() {
		TicketIssue ticketIssue = ticketIssueService.findTicketIssueById(refNo);
		if(ticketIssue != null) {
			screenMode = NEW;
			roundUp = "1";
			issueDate = new Date();
			selectedRate = ticketIssue.getRate();
			roe = null;
			if(selectedRate != null) {
				roe = selectedRate.getRate();
			}
			paymentType = ticketIssue.getPaymentType();
			invoiceStockDetailId = null;
			Agent ticketAgent = ticketIssue.getAgent();
			if(ticketAgent != null) {
				if(!"1".equals(ticketAgent.getRetailFlag())) {
					companyName = ticketAgent.getCompany();
					vatCode = ticketAgent.getVat_code();
					address = ticketAgent.getAddress();
				} else {
					companyName = ticketIssue.getRetailCustomerName();
				}
			}
			taxInvoiceIssueDetails = copyTicketDetails(ticketIssue.getTicketIssueDetail());
			totalAmtUsd = ticketIssue.getAmountUsd();
			changeRoundUp();
			invStockDetails = taxInvoiceService.findUsableInvoiceStockDetail(cargo);
		} else {
			gotoNewTaxInvIssueScreen();
		}
	}
	
	private List<TaxInvoiceIssueDetail> copyTicketDetails(List<TicketIssueDetail> sourceDetails) {
		if(sourceDetails == null || sourceDetails.isEmpty()) {
			return null;
		}
		List<TaxInvoiceIssueDetail> copiedDetails = new ArrayList<>();
		for(TicketIssueDetail detail : sourceDetails) {
			TaxInvoiceIssueDetail copy = new TaxInvoiceIssueDetail();
			copy.setAmount(detail.getAmount());
			copy.setPrice(detail.getPrice());
			copy.setQuantity(detail.getQuantity());
			copy.setRoute(detail.getRoute());
			copy.setTicketNo(detail.getTicketNo());
			copiedDetails.add(copy);
		}
		return copiedDetails;
	}
	
	private String getPaymentType(String type) {
		String name = "";
		if("0".equals(type)) {
			name = "Tiền mặt";
		} else if("1".equals(type)) {
			name = "Thẻ tín dụng";
		} else if("2".equals(type)) {
			name = "Chuyển khoản";
		} else if("3".equals(type)) {
			name = "Tài khoản";
		}
		return name;
	}
	
	private void initSearchParams() {
		Date searchDate = new Date();
		searchStartDate = DateUtil.getFirstDateOfMonth(searchDate);
		searchEndDate = DateUtil.getLastDateOfMonth(searchDate);
		searchRefNo = null;
		searchVatCode = null;
		searchStatus = "ALL";
		searchCompanyName = null;
		searchTaxInvNo = null;
	}
	
	private TaxInvoiceIssue prepareTaxInvoiceForInsert() {
		TaxInvoiceIssue newInvoiceIssue = new TaxInvoiceIssue();
		newInvoiceIssue.setIssueDate(issueDate);
		newInvoiceIssue.setAmtUsd(totalAmtUsd);
		newInvoiceIssue.setCompanyName(companyName);
		newInvoiceIssue.setVatCode(vatCode);
		newInvoiceIssue.setAddress(address);
		newInvoiceIssue.setInvoiceStockDetailId(invoiceStockDetailId);
		newInvoiceIssue.setPaymentType(paymentType);
		newInvoiceIssue.setStatus("0");
		newInvoiceIssue.setTicketIssueId(refNo);
		newInvoiceIssue.setTaxInvoiceIssueDetail(taxInvoiceIssueDetails);
		newInvoiceIssue.setRoundUp(roundUp);
		if(loginBean.getLoginUser() != null) {
			newInvoiceIssue.setCreateEmpId(loginBean.getLoginUser().getId());
		}
		if(cargo) {
			newInvoiceIssue.setCargo("1");
		}
		if(useManualRate) {
			newInvoiceIssue.setUseManualRate("1");
			newInvoiceIssue.setManualRate(roe);
		} else {
			newInvoiceIssue.setRateId(selectedRate.getId());
		}
		return newInvoiceIssue;
	}
	
	private void prepareTaxInvoiceForUpdate() {
		selectedTaxInvIssue.setIssueDate(issueDate);
		selectedTaxInvIssue.setAmtUsd(totalAmtUsd);
		selectedTaxInvIssue.setCompanyName(companyName);
		selectedTaxInvIssue.setVatCode(vatCode);
		selectedTaxInvIssue.setAddress(address);
		selectedTaxInvIssue.setInvoiceStockDetailId(invoiceStockDetailId);
		selectedTaxInvIssue.setPaymentType(paymentType);
		//selectedTaxInvIssue.setStatus("0");
		selectedTaxInvIssue.setTicketIssueId(refNo);
		selectedTaxInvIssue.setRoundUp(roundUp);
		selectedTaxInvIssue.setTaxInvoiceIssueDetail(taxInvoiceIssueDetails);
		if(useManualRate) {
			selectedTaxInvIssue.setUseManualRate("1");
			selectedTaxInvIssue.setManualRate(roe);
		} else {
			selectedTaxInvIssue.setRateId(selectedRate.getId());
		}
	}
	
	private boolean checkTaxInvoiceIssue() {
		boolean result = true;
		if(taxInvoiceIssueDetails == null
				|| taxInvoiceIssueDetails.isEmpty()) {
			JSFUtil.addError(bundle, null, null, "ticketIssue_error_TicketNoDetail");
			result = false;
		}
		if((useManualRate && roe == null) || (!useManualRate && selectedRate == null)) {
			JSFUtil.addError(bundle, null, null, "taxInvoice_m_rateError");
			result = false;
		}
		return result;
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

	public String getSearchVatCode() {
		return searchVatCode;
	}

	public void setSearchVatCode(String searchVatCode) {
		this.searchVatCode = searchVatCode;
	}

	public Agent getSelectedAgent() {
		return selectedAgent;
	}

	public void setSelectedAgent(Agent selectedAgent) {
		this.selectedAgent = selectedAgent;
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

	public String getSearchCompanyName() {
		return searchCompanyName;
	}

	public void setSearchCompanyName(String searchCompanyName) {
		this.searchCompanyName = searchCompanyName;
	}

	public String getSearchTaxInvNo() {
		return searchTaxInvNo;
	}

	public void setSearchTaxInvNo(String searchTaxInvNo) {
		this.searchTaxInvNo = searchTaxInvNo;
	}

	public TaxInvoiceIssue getSelectedTaxInvIssue() {
		return selectedTaxInvIssue;
	}

	public void setSelectedTaxInvIssue(TaxInvoiceIssue selectedTaxInvIssue) {
		this.selectedTaxInvIssue = selectedTaxInvIssue;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getRefNo() {
		return refNo;
	}

	public void setRefNo(Long refNo) {
		this.refNo = refNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getInvoiceStockDetailId() {
		return invoiceStockDetailId;
	}

	public void setInvoiceStockDetailId(Long invoiceStockDetailId) {
		this.invoiceStockDetailId = invoiceStockDetailId;
	}

	public String getInvoiceStockDetailNo() {
		return invoiceStockDetailNo;
	}

	public void setInvoiceStockDetailNo(String invoiceStockDetailNo) {
		this.invoiceStockDetailNo = invoiceStockDetailNo;
	}

	public List<InvoiceStockDetail> getInvStockDetails() {
		return invStockDetails;
	}

	public void setInvStockDetails(List<InvoiceStockDetail> invStockDetails) {
		this.invStockDetails = invStockDetails;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<TaxInvoiceIssueDetail> getTaxInvoiceIssueDetails() {
		return taxInvoiceIssueDetails;
	}

	public void setTaxInvoiceIssueDetails(
			List<TaxInvoiceIssueDetail> taxInvoiceIssueDetails) {
		this.taxInvoiceIssueDetails = taxInvoiceIssueDetails;
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

	public List<TaxInvoiceIssueDetail> getInputTaxInvoiceIssueDetails() {
		return inputTaxInvoiceIssueDetails;
	}

	public void setInputTaxInvoiceIssueDetails(
			List<TaxInvoiceIssueDetail> inputTaxInvoiceIssueDetails) {
		this.inputTaxInvoiceIssueDetails = inputTaxInvoiceIssueDetails;
	}

	public TaxInvoiceIssueDetail getSelectedTaxInvoiceIssueDetail() {
		return selectedTaxInvoiceIssueDetail;
	}

	public void setSelectedTaxInvoiceIssueDetail(
			TaxInvoiceIssueDetail selectedTaxInvoiceIssueDetail) {
		this.selectedTaxInvoiceIssueDetail = selectedTaxInvoiceIssueDetail;
	}

	public TaxInvoiceIssueServiceBean getTaxInvoiceIssueService() {
		return taxInvoiceIssueService;
	}

	public void setTaxInvoiceIssueService(
			TaxInvoiceIssueServiceBean taxInvoiceIssueService) {
		this.taxInvoiceIssueService = taxInvoiceIssueService;
	}

	public String getRoundUp() {
		return roundUp;
	}

	public void setRoundUp(String roundUp) {
		this.roundUp = roundUp;
	}
	
	public String getRefNoFormatted() {
		if(refNo == null) {
			return "";
		}
		return String.format("%06d", refNo);
	}

	public boolean isCargo() {
		return cargo;
	}

	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}

	public void setTaxInvIssueList(LazyDataModel<TaxInvoiceIssue> taxInvIssueList) {
		this.taxInvIssueList = taxInvIssueList;
	}

	public LazyDataModel<TaxInvoiceIssue> getTaxInvIssueList() {
		return taxInvIssueList;
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

	public boolean isUseManualRate() {
		return useManualRate;
	}

	public void setUseManualRate(boolean useManualRate) {
		this.useManualRate = useManualRate;
	}

	public boolean isFromTicketIssue() {
		return fromTicketIssue;
	}

	public void setFromTicketIssue(boolean fromTicketIssue) {
		this.fromTicketIssue = fromTicketIssue;
	}

}
