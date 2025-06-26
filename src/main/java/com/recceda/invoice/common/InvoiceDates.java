package com.recceda.invoice.common;

public class InvoiceDates {
    private final String invoiceDate;
    private final String invoiceDueByDate;

    public InvoiceDates(String invoiceDate, String invoiceDueByDate) {
        this.invoiceDate = invoiceDate;
        this.invoiceDueByDate = invoiceDueByDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getInvoiceDueByDate() {
        return invoiceDueByDate;
    }
}
