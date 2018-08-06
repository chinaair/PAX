package com.chinaair.webDto;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.chinaair.entity.TicketIssue;

public class TicketIssueSorter implements Comparator<TicketIssue> {
	 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public TicketIssueSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(TicketIssue ticketIssue1, TicketIssue ticketIssue2) {
        try {
            Object value1 = TicketIssue.class.getField(this.sortField).get(ticketIssue1);
            Object value2 = TicketIssue.class.getField(this.sortField).get(ticketIssue2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
