package com.recceda.invoice.common;

public class CustomerInvoiceData {
    private final String customerName;
    private final String[] addressLines;
    private final InvoiceItem[] items;
    private final InvoiceDates invoiceDates;

    private final TotalsAndTaxInfo totalsAndTaxInfo;

    public CustomerInvoiceData(String customerName, String[] addressLines, InvoiceItem[] items,
            InvoiceDates invoiceDates,
            TotalsAndTaxInfo totalsAndTaxInfo) {
        this.addressLines = addressLines;
        this.items = items;
        this.customerName = customerName;
        this.invoiceDates = invoiceDates;

        this.totalsAndTaxInfo = totalsAndTaxInfo;
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

    public TotalsAndTaxInfo getTotalsAndTaxInfo() {
        return totalsAndTaxInfo;
    }

    public InvoiceDates getInvoiceDates() {
        return invoiceDates;
    }

}
