package com.chinaair.webDto;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.chinaair.entity.Rate;
import com.chinaair.services.RateServiceBean;

public class RateModel extends LazyDataModel<Rate> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Rate> datasource;
	private RateServiceBean rateService;
	private Date startDate ; 
	private Date endDate;
	public RateModel(RateServiceBean rateService,Date startDate,Date endDate) {
        //this.datasource = datasource;
		this.rateService = rateService;
		this.startDate = startDate;
		this.endDate = endDate;
    }
	@Override
    public Rate getRowData(String rowKey) {
        for(Rate rate : datasource) {
            if(rate.getId().equals(rowKey))
                return rate;
        }
 
        return null;
    }
	@Override
    public Object getRowKey(Rate rate) {
        return rate.getId();
    }
	@Override
    public List<Rate> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
        if(startDate != null){
        	datasource = rateService.getRateList(startDate,endDate,first,pageSize);
        } else{
        	datasource = rateService.getRateList(first,pageSize);
        }
        //sort
        if(sortField != null) {
            Collections.sort(datasource, new RateSorter(sortField, sortOrder));
        }
        //rowCount
        int dataSize = rateService.countRateList(startDate,endDate);
        this.setRowCount(dataSize);
 
            return datasource;
    }
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
