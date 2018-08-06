package com.chinaair.webBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.SelectableDataModel;

import com.chinaair.dto.AgentSummaryDto;
import com.chinaair.dto.FinalReportSearchDto;
import com.chinaair.entity.Master;
import com.chinaair.services.AgentServiceBean;
import com.chinaair.services.FinalReportServiceBean;
import com.chinaair.services.MasterServiceBean;
import com.chinaair.util.DateUtil;
import com.chinaair.util.JSFUtil;
import com.chinaair.util.JasperUtil;

@ConversationScoped
@Named("finalReportBean")
public class FinalReportBean implements Serializable {
	
	private static final long serialVersionUID = -4816929101269413598L;

	private ResourceBundle bundle;
	
	private AgentSummaryModel agentSummaryList = new AgentSummaryModel();
	
	private String reportPeriod;
	
	private Date reportStartDate;
	
	private Date reportEndDate;
	
	private String reportYearMonth;
	
	private String paymentType;
	
	private AgentSummaryDto[] selectAgentSumDtos;
	
	private boolean reportDateDisabled;
	
	private boolean reportYearMonthDisabled;
	
	@EJB
	private AgentServiceBean agentService;
	
	@EJB
	private FinalReportServiceBean finalReportService;
	
	@EJB
	private MasterServiceBean masterService;
	
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
		reportPeriod = "1";
		Date now = new Date();
		reportStartDate = DateUtil.getFirstDateOfMonth(now);
		reportEndDate = DateUtil.addDate(reportStartDate, DateUtil.DAY, 14);
		reportYearMonth = DateUtil.formatDateString(reportStartDate, "yyyy/MM");
		paymentType = "0";
		reportDateDisabled = true;
		reportYearMonthDisabled = false;
		searchFinalReportData();
	}
	
	public void searchFinalReportData() {
		if(!checkInput()) {
			return;
		}
		selectAgentSumDtos = null;
		FinalReportSearchDto searchDto = new FinalReportSearchDto();
		searchDto.setStartDate(reportStartDate);
		searchDto.setEndDate(reportEndDate);
		searchDto.setPaymentType(paymentType);
		List<AgentSummaryDto> data = finalReportService.getAgentSummaryData(searchDto);
		agentSummaryList.setWrappedData(data);
	}
	
	public void finalReportPrint() {
		if(selectAgentSumDtos == null || selectAgentSumDtos.length == 0) {
			JSFUtil.addError(bundle, null, null, "finalReport_e_notSelectRecords");
			return;
		}
		List<AgentSummaryDto> summary = finalReportService.findInvoiceSummaryGroupByAgent(reportStartDate, reportEndDate, paymentType, selectAgentSumDtos);
		List<Master> companyMasterInfo = masterService.findByMasterParent("0001");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> params = new HashMap<>();
		params.put("printDate", df.format(new Date()));
		params.put("systemClass", loginBean.getSystemClass());
		if(companyMasterInfo != null && !companyMasterInfo.isEmpty()) {
			for(Master m : companyMasterInfo) {
				params.put(m.getName(), m.getValue());
			}
		}
		params.put("startDate", df.format(reportStartDate));
		params.put("endDate", df.format(reportEndDate));
		try {
			JasperUtil.createReportAndDownloadPDF("AgentFinalReport", summary, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void finalReportExport() {
		
	}
	
	public void onChangeReportPeriod() {
		JSFUtil.setValidComponent(":frm:startDate");
		JSFUtil.setValidComponent(":frm:endDate");
		if(reportYearMonth == null || reportPeriod == null) {
			JSFUtil.addError(bundle, null, null, "finalReport_e_notSelectPeriod");
			return;
		}
		String formatYm = reportYearMonth.replace("/", "");
		if("1".equals(reportPeriod)) {//first period
			reportStartDate = DateUtil.getFirstDateOfMonth(formatYm);
			reportEndDate = DateUtil.addDate(reportStartDate, DateUtil.DAY, 14);
			reportDateDisabled = true;
			reportYearMonthDisabled = false;
		} else if("2".equals(reportPeriod)) {//second period
			Date firstDay = DateUtil.getFirstDateOfMonth(formatYm);
			reportStartDate = DateUtil.addDate(firstDay, DateUtil.DAY, 15);
			reportEndDate = DateUtil.getLastDateOfMonth(formatYm);
			reportDateDisabled = true;
			reportYearMonthDisabled = false;
		} else if("0".equals(reportPeriod)) {//whole month
			reportStartDate = DateUtil.getFirstDateOfMonth(formatYm);
			reportEndDate = DateUtil.getLastDateOfMonth(formatYm);
			reportDateDisabled = true;
			reportYearMonthDisabled = false;
		} else if("3".equals(reportPeriod)) {
			reportStartDate = null;
			reportEndDate = null;
			reportDateDisabled = false;
			reportYearMonthDisabled = true;
		}
	}
	
	public void onChangeYearMonth() {
		onChangeReportPeriod();
	}

	public AgentSummaryModel getAgentSummaryList() {
		return agentSummaryList;
	}

	public void setAgentSummaryList(AgentSummaryModel agentSummaryList) {
		this.agentSummaryList = agentSummaryList;
	}

	public String getReportPeriod() {
		return reportPeriod;
	}

	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	public Date getReportStartDate() {
		return reportStartDate;
	}

	public void setReportStartDate(Date reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	public Date getReportEndDate() {
		return reportEndDate;
	}

	public void setReportEndDate(Date reportEndDate) {
		this.reportEndDate = reportEndDate;
	}

	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public AgentSummaryDto[] getSelectAgentSumDtos() {
		return selectAgentSumDtos;
	}

	public void setSelectAgentSumDtos(AgentSummaryDto[] selectAgentSumDtos) {
		this.selectAgentSumDtos = selectAgentSumDtos;
	}

	public boolean isReportDateDisabled() {
		return reportDateDisabled;
	}

	public void setReportDateDisabled(boolean reportDateDisabled) {
		this.reportDateDisabled = reportDateDisabled;
	}

	public boolean isReportYearMonthDisabled() {
		return reportYearMonthDisabled;
	}

	public void setReportYearMonthDisabled(boolean reportYearMonthDisabled) {
		this.reportYearMonthDisabled = reportYearMonthDisabled;
	}
	
	private boolean checkInput() {
		boolean result = true;
		if(reportStartDate == null) {
			JSFUtil.addError(bundle, null, ":frm:startDate", "finalReport_e_startDate");
			result = false;
		}
		if(reportEndDate == null) {
			JSFUtil.addError(bundle, null, ":frm:endDate", "finalReport_e_endDate");
			result = false;
		}
		if(paymentType == null) {
			JSFUtil.addError(bundle, null, null, "finalReport_e_paymentType");
			result = false;
		}
		if(reportStartDate != null && reportEndDate != null
				&& reportStartDate.compareTo(reportEndDate) > 0) {
			JSFUtil.addError(bundle, null, ":frm:startDate", "finalReport_e_startEndDate");
			result = false;
		}
		return result;
	}
	
	private class AgentSummaryModel extends ListDataModel<AgentSummaryDto> implements SelectableDataModel<AgentSummaryDto> {

		@Override
		public AgentSummaryDto getRowData(String rowKey) {
			@SuppressWarnings("unchecked")
			List<AgentSummaryDto> dtos = (List<AgentSummaryDto>)getWrappedData();
			Long key = new Long(rowKey);
			for(AgentSummaryDto item : dtos) {
				if(key.equals(item.getAgent().getId())) {
					return item;
				}
			}
			return null;
		}

		@Override
		public Object getRowKey(AgentSummaryDto dto) {
			return dto.getAgent().getId().toString();
		}
		
	}

}
