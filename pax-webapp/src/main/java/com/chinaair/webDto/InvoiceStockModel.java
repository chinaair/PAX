package com.chinaair.webDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.chinaair.entity.InvoiceStock;
import com.chinaair.services.TaxInvoiceServiceBean;

public class InvoiceStockModel extends LazyDataModel<InvoiceStock> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<InvoiceStock> datasource;
	private boolean cargo;
	private TaxInvoiceServiceBean taxInvoiceService;
	public InvoiceStockModel(TaxInvoiceServiceBean taxInvoiceService,boolean cargo) {
        //this.datasource = datasource;
		this.taxInvoiceService = taxInvoiceService;
		this.cargo = cargo;
    }
	@Override
    public InvoiceStock getRowData(String rowKey) {
        for(InvoiceStock invoiceStock : datasource) {
            if(invoiceStock.getId().equals(rowKey))
                return invoiceStock;
        }
 
        return null;
    }
	@Override
    public Object getRowKey(InvoiceStock invoiceStock) {
        return invoiceStock.getId();
    }
	@Override
    public List<InvoiceStock> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		datasource =  taxInvoiceService.getAllInvoiceStockInfo(first,pageSize,cargo);
        //sort
        if(sortField != null) {
            Collections.sort(datasource, new InvoiceStockSorter(sortField, sortOrder));
        }
        //rowCount
        int dataSize = taxInvoiceService.countAllInvoiceStockInfo(cargo);
        this.setRowCount(dataSize);
 
         return datasource;
    }
	/**
	 * @return the cargo
	 */
	public boolean isCargo() {
		return cargo;
	}
	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}
}
