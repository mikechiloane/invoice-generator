package com.recceda.invoice.common;

public class TotalsAndTaxInfo {
    private final String subTotal;
    private final String tax;
    private final String taxRate;
    private final String total;

    public TotalsAndTaxInfo(String subTotal, String tax, String taxRate, String total) {
        this.subTotal = subTotal;
        this.tax = tax;
        this.taxRate = taxRate;
        this.total = total;
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
