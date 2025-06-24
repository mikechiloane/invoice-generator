package com.recceda.invoice.context;

import com.recceda.invoice.common.CustomerInvoiceData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public final class PdfContext {
    private final String invoiceNumber;
    private final CustomerInvoiceData customerData;

    private final PDDocument document;

    private final PDType0Font font;

    private final float A4_WIDTH = PDRectangle.A4.getWidth();
    private final float A4_HEIGHT = PDRectangle.A4.getHeight();
    private final float margin = 50;
    private final float width = A4_WIDTH - 2 * margin;
    private final float height = A4_HEIGHT - 2 * margin;
    private final float startX = margin;
    private final float startY = A4_HEIGHT - margin - 20;

    public PdfContext(CustomerInvoiceData customerData, PDType0Font font, PDDocument document) {
        this.invoiceNumber = this.generateInvoiceNumber();
        this.customerData = customerData;
        this.font = font;
        this.document = document;
    }

    public String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public CustomerInvoiceData getCustomerData() {
        return customerData;
    }

    public float getA4_WIDTH() {
        return A4_WIDTH;
    }

    public float getA4_HEIGHT() {
        return A4_HEIGHT;
    }

    public float getMargin() {
        return margin;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public PDType0Font getFont() {
        return font;
    }

    public PDDocument getDocument() {
        return document;
    }
}
