package com.recceda.invoice.common;

public class InvoiceItem {
    private final String itemName;
    private final  Integer quantity;
    private final Double unitPrice;
    private final  Double totalPrice;

    public InvoiceItem(Integer quantity, Double unitPrice, String itemName) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
        this.itemName = itemName;
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



    public String getItemName() {
        return itemName;
    }

}
