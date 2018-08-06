package com.chinaair.webDto;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.chinaair.entity.Rate;

public class RateSorter implements Comparator<Rate> {
	 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public RateSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Rate rate1, Rate rate2) {
        try {
            Object value1 = Rate.class.getField(this.sortField).get(rate1);
            Object value2 = Rate.class.getField(this.sortField).get(rate2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
