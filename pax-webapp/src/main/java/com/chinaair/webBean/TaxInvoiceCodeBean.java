package com.chinaair.webBean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.chinaair.entity.TaxInvoiceCode;
import com.chinaair.services.TaxInvoiceServiceBean;
import com.chinaair.util.JSFUtil;

@ConversationScoped
@Named("taxInvoiceCodeBean")
public class TaxInvoiceCodeBean implements Serializable {

	private static final long serialVersionUID = 7200260942646974883L;
	
	private ResourceBundle bundle;
	
	private List<TaxInvoiceCode> invoiceCodeList;
	
	private boolean buttonDelDisabled;
	
	private TaxInvoiceCode selectedTaxInvoiceCode;
	
	private String inputCode;
	
	private String inputName;
	
	@EJB
	private TaxInvoiceServiceBean taxInvoiceService;
	
	@Inject
	private Conversation conversation;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		if(bundle != null) {
			bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");	
		}
		clearInput();
		invoiceCodeList = taxInvoiceService.getAllTaxInvoiceCode();
	}
	
	public void insertTaxInvoiceCode() {
		TaxInvoiceCode insertObj = new TaxInvoiceCode();
		insertObj.setCode(inputCode);
		insertObj.setName(inputName);
		taxInvoiceService.insertTaxInvoiceCode(insertObj);
		init();
	}
	
	public void deleteTaxInvoiceCode() {
		if(selectedTaxInvoiceCode != null) {
			Long invStockCount = taxInvoiceService.countTaxStockByInvCode(selectedTaxInvoiceCode.getId());
			if(invStockCount != null && invStockCount.intValue() > 0) {
				JSFUtil.addError(bundle, null, null, "taxInvoice_m_invoiceCodeUsed");
				return;
			}
			taxInvoiceService.removeTaxInvoiceCode(selectedTaxInvoiceCode.getId());
		} else {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return;
		}
		init();
	}
	
	public void clearInput() {
		inputCode = null;
		inputName = null;
		selectedTaxInvoiceCode = null;
		buttonDelDisabled = false;
	}
	
	public void onSelectTaxInvoiceCode() {
		if(selectedTaxInvoiceCode != null) {
			inputCode = selectedTaxInvoiceCode.getCode();
			inputName = selectedTaxInvoiceCode.getName();
			//TODO search use of this tax code
			/*if(tax code has been used) {
				buttonDelDisabled = true;
			}*/
		}
	}

	public List<TaxInvoiceCode> getInvoiceCodeList() {
		return invoiceCodeList;
	}

	public void setInvoiceCodeList(List<TaxInvoiceCode> invoiceCodeList) {
		this.invoiceCodeList = invoiceCodeList;
	}

	public boolean isButtonDelDisabled() {
		return buttonDelDisabled;
	}

	public void setButtonDelDisabled(boolean buttonDelDisabled) {
		this.buttonDelDisabled = buttonDelDisabled;
	}

	public TaxInvoiceCode getSelectedTaxInvoiceCode() {
		return selectedTaxInvoiceCode;
	}

	public void setSelectedTaxInvoiceCode(TaxInvoiceCode selectedTaxInvoiceCode) {
		this.selectedTaxInvoiceCode = selectedTaxInvoiceCode;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

}
