package com.recceda.invoice.common;


public class CustomerInvoiceData {
    private  final String customerName ;
    private String[] addressLines;
    private InvoiceItem[] items;


    public CustomerInvoiceData(String customerName, String[] addressLines, InvoiceItem[] items) {
        this.addressLines =  addressLines;
        this.items = items;
        this.customerName = customerName;
    }

   
    public String[] getAddressLines() {
        return addressLines;
    }

    public InvoiceItem[] getItems() {
        return items;
    }

    public String getCustomerName() {
        return customerName;
    }

}
