package com.chinaair.webDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.chinaair.dto.TicketIssueSearchDto;
import com.chinaair.entity.TicketIssue;
import com.chinaair.services.TicketIssueServiceBean;

public class TicketIssueModel extends LazyDataModel<TicketIssue> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TicketIssue> datasource;
	private TicketIssueSearchDto searchDto;
	private TicketIssueServiceBean ticketIssueService;
	public TicketIssueModel(TicketIssueServiceBean ticketIssueService,TicketIssueSearchDto searchDto) {
        //this.datasource = datasource;
		this.ticketIssueService = ticketIssueService;
		this.searchDto = searchDto;
    }
	@Override
    public TicketIssue getRowData(String rowKey) {
        for(TicketIssue ticketIssue : datasource) {
            if(ticketIssue.getId().equals(rowKey))
                return ticketIssue;
        }
 
        return null;
    }
	@Override
    public Object getRowKey(TicketIssue ticketIssue) {
        return ticketIssue.getId();
    }
	@Override
    public List<TicketIssue> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
		datasource = ticketIssueService.findTicketIssueList(first,pageSize,searchDto);
        //sort
        if(sortField != null) {
            Collections.sort(datasource, new TicketIssueSorter(sortField, sortOrder));
        }
        //rowCount
        int dataSize = ticketIssueService.countTicketIssueList(searchDto).intValue();
        this.setRowCount(dataSize);
 
         return datasource;
    }
	public TicketIssueSearchDto getSearchDto() {
		return searchDto;
	}
	public void setSearchDto(TicketIssueSearchDto searchDto) {
		this.searchDto = searchDto;
	}
}
