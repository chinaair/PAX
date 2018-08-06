package com.chinaair.webDto;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.chinaair.dto.TaxInvoiceIssueSearchDto;
import com.chinaair.entity.TaxInvoiceIssue;
import com.chinaair.services.TaxInvoiceIssueServiceBean;

public class TaxInvoiceIssueModel extends LazyDataModel<TaxInvoiceIssue> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TaxInvoiceIssue> datasource;
	private TaxInvoiceIssueSearchDto searchDto;
	private TaxInvoiceIssueServiceBean taxInvoiceIssueService;
	public TaxInvoiceIssueModel(TaxInvoiceIssueServiceBean taxInvoiceIssueService,TaxInvoiceIssueSearchDto searchDto) {
        //this.datasource = datasource;
		this.taxInvoiceIssueService = taxInvoiceIssueService;
		this.searchDto = searchDto;
    }
	@Override
    public TaxInvoiceIssue getRowData(String rowKey) {
        for(TaxInvoiceIssue taxInvoiceIssue : datasource) {
            if(taxInvoiceIssue.getId().equals(rowKey))
                return taxInvoiceIssue;
        }
 
        return null;
    }
	@Override
    public Object getRowKey(TaxInvoiceIssue taxInvoiceIssue) {
        return taxInvoiceIssue.getId();
    }
	@Override
    public List<TaxInvoiceIssue> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		if(sortField != null) {
			searchDto.setSortField(sortField);
			if(SortOrder.DESCENDING.equals(sortOrder)) {
				searchDto.setSortOrder("DESC");
			} else {
				searchDto.setSortOrder("ASC");
			}
		}
		datasource =  taxInvoiceIssueService.getTaxInvoiceIssueList(first,pageSize,searchDto);
        //rowCount
        int dataSize = taxInvoiceIssueService.countTaxInvoiceIssueList(searchDto).intValue();
        this.setRowCount(dataSize);
 
         return datasource;
    }
	public TaxInvoiceIssueSearchDto getSearchDto() {
		return searchDto;
	}
	public void setSearchDto(TaxInvoiceIssueSearchDto searchDto) {
		this.searchDto = searchDto;
	}
	public TaxInvoiceIssueServiceBean getTaxInvoiceIssueService() {
		return taxInvoiceIssueService;
	}
	public void setTaxInvoiceIssueService(TaxInvoiceIssueServiceBean taxInvoiceIssueService) {
		this.taxInvoiceIssueService = taxInvoiceIssueService;
	}
}
