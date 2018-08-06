package com.chinaair.webDto;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.chinaair.entity.InvoiceStock;

public class InvoiceStockSorter implements Comparator<InvoiceStock> {
	 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public InvoiceStockSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(InvoiceStock invoiceStock1, InvoiceStock invoiceStock2) {
        try {
            Object value1 = InvoiceStock.class.getField(this.sortField).get(invoiceStock1);
            Object value2 = InvoiceStock.class.getField(this.sortField).get(invoiceStock2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
