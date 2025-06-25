package com.recceda.invoice.common;

public class CustomerInvoiceData {
    private final String customerName;
    private final String[] addressLines;
    private final InvoiceItem[] items;
    private final String invoiceDate;
    private final String invoiceDueByDate;
    private final String subTotal;
    private final String tax;
    private final String taxRate;
    private final String total;

    public CustomerInvoiceData(String customerName, String[] addressLines, InvoiceItem[] items, String invoiceDate,
            String invoiceDueByDate,
            String subTotal, String tax, String taxRate, String total) {
        this.addressLines = addressLines;
        this.items = items;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        this.invoiceDueByDate = invoiceDueByDate;
        this.subTotal = subTotal;
        this.tax = tax;
        this.taxRate = taxRate;
        this.total = total;
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

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getInvoiceDueByDate() {
        return invoiceDueByDate;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getTax() {
        return tax;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public String getTotal() {
        return total;
    }

}
