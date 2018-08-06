package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import com.chinaair.dto.AgentTicketIssueDto;
import com.chinaair.dto.EmpTicketIssueDto;
import com.chinaair.entity.Agent;
import com.chinaair.entity.DailyTicketJournal;
import com.chinaair.entity.Employee;
import com.chinaair.entity.Rate;
import com.chinaair.entity.TicketIssue;
import com.chinaair.entity.TicketIssueDetail;
import com.chinaair.services.JournalServiceBean;
import com.chinaair.services.TicketIssueServiceBean;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;

@ConversationScoped
@Named("closeOpenPbsrBean")
public class CloseOpenPbsrBean implements Serializable {
	
	private static final long serialVersionUID = -7682375558026131855L;

	@EJB
	private TicketIssueServiceBean ticketIssueService;
	
	@EJB
	private JournalServiceBean journalService;
	
	private Date reportDate;
	
	private DailyTicketJournal selectedJournal;
	
	private List<EmpTicketIssueDto> dailyTicketIssueByEmps;
	
	private List<AgentTicketIssueDto> dailyTicketByAgent;
	
	private boolean opened;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private BigDecimal totalAgentsAmtUsd;
	
	private BigDecimal totalAgentsAmtVnd;
	
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
		reportDate = new Date();
		summaryTicketIssueByDate();
	}
	
	public void openPbsr() {
		if(selectedJournal == null) {
			JSFUtil.addError(bundle, null, null, "pbsr_closeDateInvalid");
			return;
		}
		journalService.reOpenClosedJournal(reportDate);
		opened = true;
	}
	
	public void closePbsr() {
		Long closeEmpid = null;
		if(loginBean.getLoginUser() != null) {
			closeEmpid = loginBean.getLoginUser().getId();
		}
		if(selectedJournal == null) {
			journalService.insertCloseJournal(reportDate, totalAmtUsd, totalAmtVnd, closeEmpid);
		} else {
			journalService.updateCloseJournal(reportDate, totalAmtUsd, totalAmtVnd, closeEmpid);
		}
		opened = false;
	}
	
	public void onchangeReportDate() {
		summaryTicketIssueByDate();
	}
	public void printCashAgents() {
		summaryTicketIssueByAgent("0");
		printAgentDetails();
	}
	
	public void printCreditAgents() {
		summaryTicketIssueByAgent("1");
		printAgentDetails();
	}
	
	public void printBankTransferAgents() {
		summaryTicketIssueByAgent("2");
		printAgentDetails();
	}
	
	public void printAccountReceivableAgents() {
		summaryTicketIssueByAgent("3");
		printAgentDetails();
	}
	
	public void printAgentDetails() {
		if(dailyTicketByAgent == null || dailyTicketByAgent.isEmpty()) {
			return;
		}
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("systemClass", loginBean.getSystemClass());
			params.put("paymentType", "Cash Collection");
			params.put("closeDate", reportDate);
			params.put("totalUsd", totalAgentsAmtUsd);
			params.put("totalVnd", totalAgentsAmtVnd);
			JasperUtil.createReportAndDownloadPDF("AgentCollectionSummary", dailyTicketByAgent, params);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void summaryTicketIssueByDate() {
		selectedJournal = journalService.findTicketJournalByDate(reportDate);
		opened = false;
		if(selectedJournal == null || "0".equals(selectedJournal.getStatus())) {//opened
			opened = true;
		}
		List<TicketIssue> orderedIssues = ticketIssueService.findValidTicketIssueByDate(reportDate);
		if(orderedIssues != null && !orderedIssues.isEmpty()) {
			createEmpTicketIssue(orderedIssues);
		} else {
			dailyTicketIssueByEmps = null;
			totalAmtUsd = new BigDecimal(0);
			totalAmtVnd = new BigDecimal(0);
		}
	}
	
	private void summaryTicketIssueByAgent(String paymentType) {
		selectedJournal = journalService.findTicketJournalByDate(reportDate);
		if(selectedJournal == null || "0".equals(selectedJournal.getStatus())) {//opened
			JSFUtil.addError(bundle, null, null, "pbsr_openedReportDate");
			return;
		}
		List<TicketIssue> orderedIssues = ticketIssueService.findValidTicketIssueByDateOrderByAgent(reportDate, paymentType);
		if(orderedIssues != null && !orderedIssues.isEmpty()) {
			createAgentTicketIssue(orderedIssues);
		} else {
			dailyTicketByAgent = null;
			totalAgentsAmtUsd = new BigDecimal(0);
			totalAgentsAmtVnd = new BigDecimal(0);
			JSFUtil.addError(bundle, null, null, "pbsr_emptyReportData");
			return;
		}
	}
	
	private void createEmpTicketIssue(List<TicketIssue> orderedList) {
		BigDecimal thousandUnit = new BigDecimal(1000);
		Long empId = orderedList.get(0).getInputEmpId();
		Employee emp = orderedList.get(0).getInputEmp();
		dailyTicketIssueByEmps = new ArrayList<>();
		List<TicketIssue> empTicketIssue = new ArrayList<>();
		BigDecimal empTotalAmtUsd = new BigDecimal(0);
		BigDecimal empTotalAmtVnd = new BigDecimal(0);
		totalAmtUsd = new BigDecimal(0);
		totalAmtVnd = new BigDecimal(0);
		for(TicketIssue item : orderedList) {
			String compareEmpId = "";
			if(empId != null) {
				compareEmpId = empId.toString();
			}
			String listEmpId = "";
			if(item.getInputEmpId() != null) {
				listEmpId = item.getInputEmpId().toString();
			}
			if(!listEmpId.equals(compareEmpId)) {
				EmpTicketIssueDto dto = new EmpTicketIssueDto();
				dto.setEmp(emp);
				dto.setTotalAmtUsd(empTotalAmtUsd);
				dto.setTotalAmtVnd(empTotalAmtVnd);
				dto.setTicketIssueList(empTicketIssue);
				dailyTicketIssueByEmps.add(dto);
				empTotalAmtUsd = new BigDecimal(0);
				empTotalAmtVnd = new BigDecimal(0);
				empTicketIssue = new ArrayList<>();
			}
			empTotalAmtUsd = empTotalAmtUsd.add(item.getAmountUsd());
			totalAmtUsd = totalAmtUsd.add(item.getAmountUsd());
			Rate rate = item.getRate();
			if(rate != null && rate.getRate() != null) {
				BigDecimal vndAmt = new BigDecimal(0);
				for(TicketIssueDetail detail : item.getTicketIssueDetail()) {
					vndAmt = vndAmt.add(detail.getPrice()
							.multiply(rate.getRate()).divide(thousandUnit)
							.setScale(0, RoundingMode.HALF_UP)
							.multiply(thousandUnit).multiply(new BigDecimal(detail.getQuantity())));
				}
				empTotalAmtVnd = empTotalAmtVnd.add(vndAmt);
				totalAmtVnd = totalAmtVnd.add(vndAmt);
				item.setAmountVnd(vndAmt);
			}
			empTicketIssue.add(item);
			empId = item.getInputEmpId();
			emp = item.getInputEmp();
		}
		if(!empTicketIssue.isEmpty()) {
			EmpTicketIssueDto dto = new EmpTicketIssueDto();
			dto.setEmp(emp);
			dto.setTotalAmtUsd(empTotalAmtUsd);
			dto.setTotalAmtVnd(empTotalAmtVnd);
			dto.setTicketIssueList(empTicketIssue);
			dailyTicketIssueByEmps.add(dto);
		}
	}
	
	private void createAgentTicketIssue(List<TicketIssue> orderedList) {
		BigDecimal thousandUnit = new BigDecimal(1000);
		Agent agent = orderedList.get(0).getAgent();
		Long agentId = agent.getId();
		dailyTicketByAgent = new ArrayList<>();
		List<TicketIssue> agentTicketIssue = new ArrayList<>();
		BigDecimal agentTotalAmtUsd = new BigDecimal(0);
		BigDecimal agentTotalAmtVnd = new BigDecimal(0);
		totalAgentsAmtUsd = new BigDecimal(0);
		totalAgentsAmtVnd = new BigDecimal(0);
		for(TicketIssue item : orderedList) {
			String compareAgentId = "";
			if(agentId != null) {
				compareAgentId = agentId.toString();
			}
			String listAgentId = "";
			if(item.getAgent() != null && item.getAgent().getId() != null) {
				listAgentId = item.getAgent().getId().toString();
			}
			if(!listAgentId.equals(compareAgentId)) {
				AgentTicketIssueDto dto = new AgentTicketIssueDto();
				dto.setAgent(agent);
				dto.setTotalAmtUsd(agentTotalAmtUsd);
				dto.setTotalAmtVnd(agentTotalAmtVnd);
				dto.setTicketIssueList(agentTicketIssue);
				dailyTicketByAgent.add(dto);
				agentTotalAmtUsd = new BigDecimal(0);
				agentTotalAmtVnd = new BigDecimal(0);
				agentTicketIssue = new ArrayList<>();
			}
			agentTotalAmtUsd = agentTotalAmtUsd.add(item.getAmountUsd());
			totalAgentsAmtUsd = totalAgentsAmtUsd.add(item.getAmountUsd());
			Rate rate = item.getRate();
			if(rate != null && rate.getRate() != null) {
				BigDecimal vndAmt = new BigDecimal(0);
				for(TicketIssueDetail detail : item.getTicketIssueDetail()) {
					vndAmt = vndAmt.add(detail.getPrice()
							.multiply(rate.getRate()).divide(thousandUnit)
							.setScale(0, RoundingMode.HALF_UP)
							.multiply(thousandUnit).multiply(new BigDecimal(detail.getQuantity())));
				}
				agentTotalAmtVnd = agentTotalAmtVnd.add(vndAmt);
				totalAgentsAmtVnd = totalAgentsAmtVnd.add(vndAmt);
				item.setAmountVnd(vndAmt);
			}
			agentTicketIssue.add(item);
			agent = item.getAgent();
			agentId = agent.getId();
		}
		if(!agentTicketIssue.isEmpty()) {
			AgentTicketIssueDto dto = new AgentTicketIssueDto();
			dto.setAgent(agent);
			dto.setTotalAmtUsd(agentTotalAmtUsd);
			dto.setTotalAmtVnd(agentTotalAmtVnd);
			dto.setTicketIssueList(agentTicketIssue);
			dailyTicketByAgent.add(dto);
		}
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public List<EmpTicketIssueDto> getDailyTicketIssueByEmps() {
		return dailyTicketIssueByEmps;
	}

	public void setDailyTicketIssueByEmps(
			List<EmpTicketIssueDto> dailyTicketIssueByEmps) {
		this.dailyTicketIssueByEmps = dailyTicketIssueByEmps;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

}
