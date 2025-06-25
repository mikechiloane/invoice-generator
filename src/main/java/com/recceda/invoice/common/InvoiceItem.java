package com.recceda.invoice.common;

public class InvoiceItem {
    private final String description;
    private final  Integer quantity;
    private final Double unitPrice;
    private final  Double totalPrice;

    public InvoiceItem(String itemName, Integer quantity, Double unitPrice,  String itemId, String description) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
        this.description = description;
    }



    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }



    public String getDescription() {
        return description;
    }

}
