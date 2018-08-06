package com.chinaair.webBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import com.chinaair.entity.Master;
import com.chinaair.entity.TaxInvoiceIssue;
import com.chinaair.entity.TaxInvoiceIssueDetail;
import com.chinaair.services.MasterServiceBean;
import com.chinaair.services.TaxInvoiceServiceBean;
import com.chinaair.util.DateUtil;
import com.chinaair.util.JSFUtil;
import com.chinaair.webDto.TaxInvReportDto;

@ConversationScoped
@Named("taxInvoiceReportBean")
public class TaxInvoiceReportBean implements Serializable {
	
	private static final long serialVersionUID = -293759243627636271L;
	
	private static final String dateFormat = "dd/MM/yyyy";
	
	private ResourceBundle bundle;

	private Long paxInvoiceCount;
	
	private Long cgoInvoiceCount;
	
	private String reportYearMonth;
	
	@EJB
	private TaxInvoiceServiceBean taxInvoiceService;
	
	@EJB
	private MasterServiceBean masterService;
	
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
		Date nowDate = new Date();
		reportYearMonth = DateUtil.formatDateString(nowDate, "yyyy/MM");
		searchTaxInvoice();
	}
	
	public void onChangeYearMonth() {
		searchTaxInvoice();
	}
	
	public void printPaxInvoice() {
		exportExcelReport(false);
	}
	
	public void printCgoInvoice() {
		exportExcelReport(true);
	}
	
	private void exportExcelReport(boolean isCargo) {
		String formatYm = reportYearMonth.replace("/", "");
		Date startDate = DateUtil.getFirstDateOfMonth(formatYm);
		Date endDate = DateUtil.getLastDateOfMonth(formatYm);
		List<TaxInvoiceIssue> invoices = taxInvoiceService.gettaxInvoiceList(startDate, endDate, isCargo);
		if(invoices == null || invoices.isEmpty()) {
			//error message
			JSFUtil.addError(bundle, null, null, "taxInvoice_m_noRecordToExport");
			return;
		}
		List<TaxInvReportDto> details = new ArrayList<>();
		int index = 1;
		BigDecimal thousandUnit = new BigDecimal(1000);
		for(TaxInvoiceIssue item : invoices) {
			TaxInvReportDto dto = new TaxInvReportDto();
			dto.setNo(index);
			dto.setInvoiceTemplateCode(item.getInvoiceStockDetail().getInvoiceStock().getTaxInvoiceCode().getName());
			dto.setInvoiceCode(item.getInvoiceStockDetail().getInvoiceStock().getTaxInvoiceCode().getCode());
			dto.setInvoiceNumber(item.getInvoiceStockDetail().getInvoiceStockNo());
			dto.setIssueDate(DateUtil.formatDateString(item.getIssueDate(), dateFormat));
			if("2".equals(item.getStatus())) {//voided invoice
				dto.setCompanyName("Hủy");
			} else {
				dto.setCompanyName(item.getCompanyName());
				dto.setVatCode(item.getVatCode());
				if(isCargo) {
					dto.setType("Không vận đơn");
				} else {
					dto.setType("Vé máy bay");
				}
				BigDecimal roe = null;
				if("1".equals(item.getUseManualRate())) {
					roe = item.getManualRate();
				} else {
					roe = item.getRate().getRate();
				}
				if(item.getTaxInvoiceIssueDetail() != null) {
					BigDecimal totalVnd = new BigDecimal(0);
					for(TaxInvoiceIssueDetail detail : item.getTaxInvoiceIssueDetail()) {
						BigDecimal quan = new BigDecimal(detail.getQuantity());
						BigDecimal vndPrice = new BigDecimal(0);
						if("1".equals(item.getRoundUp())) {
							vndPrice = detail.getPrice().multiply(roe).divide(thousandUnit).setScale(0, RoundingMode.HALF_UP).multiply(thousandUnit);
						} else {
							vndPrice = detail.getPrice().multiply(roe).setScale(0, RoundingMode.HALF_UP);
						}
						totalVnd = totalVnd.add(vndPrice.multiply(quan));
					}
					dto.setAmountVnd(totalVnd);
				}
			}
			details.add(dto);
			index++;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Master taxCompanyMaster = masterService.findByMasterNo("0008");
		Master taxNoMaster = masterService.findByMasterNo("0009");
		if(taxCompanyMaster != null) {
			params.put("company", taxCompanyMaster.getValue());
		}
		if(taxNoMaster != null) {
			params.put("taxNo", taxNoMaster.getValue());
		}
		
		params.put("reportMonth", DateUtil.getMonth(startDate));
		params.put("reportYear", DateUtil.getYear(startDate));
		int rMonthInt = Integer.parseInt(DateUtil.getMonth(startDate));
		String reportPeriod = "";
		if(rMonthInt >= 1 && rMonthInt <= 3) {
			reportPeriod = "1";
		} else if(rMonthInt >= 4 && rMonthInt <= 6) {
			reportPeriod = "2";
		} else if(rMonthInt >= 7 && rMonthInt <= 9) {
			reportPeriod = "3";
		} else if(rMonthInt >= 10 && rMonthInt <= 12) {
			reportPeriod = "4";
		}
		params.put("reportPeriod", reportPeriod);
		params.put("invoiceDetails", details);
		XLSTransformer transformer  = new XLSTransformer();
		String templateFilePath = getTemplatePath("invoiceReportTemp");
		String populatedFilePath = templateFilePath.substring(0, templateFilePath.length() - 4) + "_populated.xls";
		try {
			transformer.transformXLS(templateFilePath, params, populatedFilePath);
			InputStream excelFileInStream = new FileInputStream(populatedFilePath);
			HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			String downloadFileName = "BangkebanraCI" + formatYm + ".xls";
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + downloadFileName);
			ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = excelFileInStream.read(bytes)) != -1) {
				servletOutputStream.write(bytes, 0, read);
			}
			excelFileInStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch(Exception e) {
			JSFUtil.addErrorMessage(null, null, e.getMessage());
			return;
		}
	}
	
	private String getTemplatePath(String excelFileName) {
		String excelPath = null;
		try {
			String templateFileName = excelFileName + ".xls";
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> names = cl.getResources("excel/" + templateFileName);
			while (names.hasMoreElements()) {
				URL excelUrl = names.nextElement();
				excelPath = excelUrl.getPath();
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return excelPath;
	}
	
	public Long getPaxInvoiceCount() {
		return paxInvoiceCount;
	}

	public void setPaxInvoiceCount(Long paxInvoiceCount) {
		this.paxInvoiceCount = paxInvoiceCount;
	}

	public Long getCgoInvoiceCount() {
		return cgoInvoiceCount;
	}

	public void setCgoInvoiceCount(Long cgoInvoiceCount) {
		this.cgoInvoiceCount = cgoInvoiceCount;
	}

	public String getReportYearMonth() {
		return reportYearMonth;
	}

	public void setReportYearMonth(String reportYearMonth) {
		this.reportYearMonth = reportYearMonth;
	}
	
	private void searchTaxInvoice() {
		String formatYm = reportYearMonth.replace("/", "");
		Date startDate = DateUtil.getFirstDateOfMonth(formatYm);
		Date endDate = DateUtil.getLastDateOfMonth(formatYm);
		paxInvoiceCount = taxInvoiceService.taxInvoiceCount(startDate, endDate, false);
		cgoInvoiceCount = taxInvoiceService.taxInvoiceCount(startDate, endDate, true);
	}

}
