package com.chinaair.webBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

import org.primefaces.model.LazyDataModel;

import com.chinaair.entity.InvoiceStock;
import com.chinaair.entity.InvoiceStockDetail;
import com.chinaair.entity.TaxInvoiceCode;
import com.chinaair.services.TaxInvoiceServiceBean;
import com.chinaair.util.JSFUtil;
import com.chinaair.webDto.InvoiceStockModel;

@ConversationScoped
@Named("invoiceStockBean")
public class InvoiceStockBean implements Serializable {
	
	private static final long serialVersionUID = 5074208540047698189L;
	
	private static final int NEW_MODE = 0;
	
	private static final int VIEW_MODE = 1;
	
	private static final int EDIT_MODE = 2;
	
	private ResourceBundle bundle;

	private InvoiceStock selectedInvoiceStock;
	
	private LazyDataModel<InvoiceStock> invoiceStockList;
	
	private Date createDate;
	
	private Long taxInvoiceCodeId;
	
	private String prefix;
	
	private BigDecimal startNo;
	
	private BigDecimal quantity;
	
	private Long used;
	
	private Long blank;
	
	private List<TaxInvoiceCode> taxInvoiceCodes;
	
	private List<InvoiceStockDetail> invoiceStockDetails;
	
	private boolean updatable;
	
	private int mode;
	
	private String taxInvoiceCodeName;
	
	private boolean cargo;
	
	@Inject
	private Conversation conversation;
	
	@EJB
	private TaxInvoiceServiceBean taxInvoiceService;
	
	public void startConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("com.chinaair.internationalization.AllResourceBundle");
		Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(requestParams.containsKey("cargo") && "true".equals(requestParams.get("cargo"))) {
			cargo = true;
		} else {
			cargo = false;
		}
		invoiceStockList = new InvoiceStockModel(taxInvoiceService,cargo);
		updatable = true;
	}
	
	public String gotoNewInvoiceStock() {
		mode = NEW_MODE;
		resetInputInvoiceStock();
		return "InputInvoiceStock?faces-redirect=true";
	}
	
	public String gotoViewInvoiceStock() {
		if(selectedInvoiceStock == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		mode = VIEW_MODE;
		taxInvoiceCodes = taxInvoiceService.getAllTaxInvoiceCode();
		createDate = selectedInvoiceStock.getCreateDate();
		taxInvoiceCodeId = selectedInvoiceStock.getInvoiceCodeId();
		taxInvoiceCodeName = selectedInvoiceStock.getTaxInvoiceCode().getCode()
				+ "(" + selectedInvoiceStock.getTaxInvoiceCode().getName()
				+ ")";
		prefix = selectedInvoiceStock.getPrefix();
		startNo = new BigDecimal(selectedInvoiceStock.getStartNo());
		quantity = new BigDecimal(selectedInvoiceStock.getQuantity());
		invoiceStockDetails = selectedInvoiceStock.getInvoiceStockDetails();
		used = new Long(taxInvoiceService.countUsedInvoiceStock(selectedInvoiceStock.getId()));
		blank = new Long(invoiceStockDetails.size() - used);
		if(used > 0) {
			updatable = false;
		}
		return "InputInvoiceStock?faces-redirect=true";
	}
	
	public void toggleEditMode() {
		mode = EDIT_MODE;
	}
	
	public String deleteInvoiceStock() {
		if(selectedInvoiceStock == null) {
			JSFUtil.addError(bundle, null, null, "resource_error_selectRecord");
			return null;
		}
		taxInvoiceService.deleteInvoiceStock(selectedInvoiceStock.getId());
		return backToList();
	}
	
	public String registerInvoiceStock() {
		if(!checkInput()) {
			return null;
		}
		InvoiceStock newStock = new InvoiceStock();
		newStock.setCreateDate(createDate);
		newStock.setInvoiceCodeId(taxInvoiceCodeId);
		newStock.setPrefix(prefix);
		newStock.setStartNo(startNo.longValue());
		newStock.setQuantity(quantity.longValue());
		if(cargo) {
			newStock.setCargo("1");
		}
		taxInvoiceService.insertTaxInvoiceStock(newStock);
		return backToList();
	}
	
	public String updateInvoiceStock() {
		if(!checkInput()) {
			return null;
		}
		if(!selectedInvoiceStock.getPrefix().equals(prefix)
				|| selectedInvoiceStock.getStartNo().longValue() != startNo.longValue()
				|| selectedInvoiceStock.getQuantity().longValue() != quantity.longValue()) {
			selectedInvoiceStock.setInvoiceCodeId(taxInvoiceCodeId);
			selectedInvoiceStock.setPrefix(prefix);
			selectedInvoiceStock.setStartNo(startNo.longValue());
			selectedInvoiceStock.setQuantity(quantity.longValue());
			taxInvoiceService.updateAllInvoiceDetails(selectedInvoiceStock);
		} else if(!selectedInvoiceStock.getInvoiceCodeId().equals(taxInvoiceCodeId)) {
			selectedInvoiceStock.setInvoiceCodeId(taxInvoiceCodeId);
			taxInvoiceService.updateInvoiceStockOnly(selectedInvoiceStock);
		}
		return backToList();
	}
	
	public String backToList() {
		invoiceStockList = new InvoiceStockModel(taxInvoiceService,cargo);
		return "TaxInvoiceStock?faces-redirect=true";
	}

	public InvoiceStock getSelectedInvoiceStock() {
		return selectedInvoiceStock;
	}

	public void setSelectedInvoiceStock(InvoiceStock selectedInvoiceStock) {
		this.selectedInvoiceStock = selectedInvoiceStock;
	}

	public LazyDataModel<InvoiceStock> getInvoiceStockList() {
		return invoiceStockList;
	}

	public void setInvoiceStockList(LazyDataModel<InvoiceStock> invoiceStockList) {
		this.invoiceStockList = invoiceStockList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getTaxInvoiceCodeId() {
		return taxInvoiceCodeId;
	}

	public void setTaxInvoiceCodeId(Long taxInvoiceCodeId) {
		this.taxInvoiceCodeId = taxInvoiceCodeId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public BigDecimal getStartNo() {
		return startNo;
	}

	public void setStartNo(BigDecimal startNo) {
		this.startNo = startNo;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Long getUsed() {
		return used;
	}

	public void setUsed(Long used) {
		this.used = used;
	}

	public Long getBlank() {
		return blank;
	}

	public void setBlank(Long blank) {
		this.blank = blank;
	}

	public List<TaxInvoiceCode> getTaxInvoiceCodes() {
		return taxInvoiceCodes;
	}

	public void setTaxInvoiceCodes(List<TaxInvoiceCode> taxInvoiceCodes) {
		this.taxInvoiceCodes = taxInvoiceCodes;
	}

	public TaxInvoiceServiceBean getTaxInvoiceService() {
		return taxInvoiceService;
	}

	public void setTaxInvoiceService(TaxInvoiceServiceBean taxInvoiceService) {
		this.taxInvoiceService = taxInvoiceService;
	}

	public List<InvoiceStockDetail> getInvoiceStockDetails() {
		return invoiceStockDetails;
	}

	public void setInvoiceStockDetails(List<InvoiceStockDetail> invoiceStockDetails) {
		this.invoiceStockDetails = invoiceStockDetails;
	}
	
	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getTaxInvoiceCodeName() {
		return taxInvoiceCodeName;
	}

	public void setTaxInvoiceCodeName(String taxInvoiceCodeName) {
		this.taxInvoiceCodeName = taxInvoiceCodeName;
	}
	
	private void resetInputInvoiceStock() {
		createDate = new Date();
		taxInvoiceCodes = taxInvoiceService.getAllTaxInvoiceCode();
		prefix = null;
		startNo = null;
		quantity = null;
	}
	
	private boolean checkInput() {
		if(startNo.add(quantity).subtract(new BigDecimal(1)).compareTo(new BigDecimal(9999)) == 1) {
			JSFUtil.addError(bundle, null, null, "taxInvoice_e_maxQuantity");
			return false;
		}
		return true;
	}

	public boolean isCargo() {
		return cargo;
	}

	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}

}
