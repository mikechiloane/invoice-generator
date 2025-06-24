package com.recceda.invoice.common;


public class CustomerInvoiceData {
    private String invoiceNumber;
    private String[] addressLines;
    private InvoiceItems[] items;

    public CustomerInvoiceData(String invoiceNumber, String[] addressLines, InvoiceItems[] items) {
        this.invoiceNumber = invoiceNumber;
        this.addressLines = addressLines;
        this.items = items;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String[] getAddressLines() {
        return addressLines;
    }

    public InvoiceItems[] getItems() {
        return items;
    }

}
