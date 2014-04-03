package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.chinaair.entity.Agent;
import com.chinaair.entity.TicketIssueDetail;

@SessionScoped
@Named("ticketIssueBean")
public class TicketIssueBean implements Serializable {

	private static final long serialVersionUID = -1953274038082422040L;
	
	private Date issueDate;
	
	private BigDecimal roe;
	
	private String refNo;
	
	private String paymentType;
	
	private Agent selectedAgent;
	
	private String displayAgentName;
	
	private List<TicketIssueDetail> ticketIssueDetails;
	
	private List<TicketIssueDetail> inputTicketDetails;
	
	private TicketIssueDetail selectedTicketIssueDetail;
	
	private BigDecimal totalAmtUsd;
	
	private BigDecimal totalAmtVnd;
	
	private String inputTicketNo;
	
	private String inputRoute;
	
	private int inputQuantity;
	
	private BigDecimal inputPrice;
	
	public void init() {
		ResourceBundle bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		bundle.getString("selectAgent_companyName");
	}
	
	public void initDetail() {
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
		detail.setTicketNo(new Long(inputTicketNo));
		detail.setRoute(inputRoute);
		detail.setQuantity(new Long(inputQuantity));
		detail.setPrice(inputPrice);
		detail.setAmount(inputPrice.multiply(new BigDecimal(inputQuantity)));
		inputTicketDetails.add(detail);
		selectedTicketIssueDetail = null;
	}
	
	public void updateDetail() {
		
	}
	
	public void deleteDetail() {
		if(selectedTicketIssueDetail != null) {
			inputTicketDetails.remove(selectedTicketIssueDetail);
			selectedTicketIssueDetail = null;
		}
	}
	
	public void okDetail() {
		
	}
	
	public void cancelDetail() {
		
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
	
	private void resetFields() {
		selectedTicketIssueDetail = null;
		inputTicketNo = "";
		inputRoute = "";
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

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
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

}
