package com.recceda.invoice.impl.sections;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.common.InvoiceItem;
import com.recceda.invoice.common.TextUtils;
import com.recceda.invoice.context.PdfContext;

public class TableSection implements PdfSection {

    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception {
        drawTableHeader(contentStream, calculateYCoordinate(context, 250), context);
        drawTableRows(contentStream, calculateYCoordinate(context, 270), context);
        addSubTotal(contentStream, calculateYCoordinate(context, 270 + 20 * context.getCustomerData().getItems().length),
                context);
        addTaxRate(contentStream, calculateYCoordinate(context, 270 + 20 * context.getCustomerData().getItems().length + 20),
                context);
        addTax(contentStream, calculateYCoordinate(context, 270 + 20 * context.getCustomerData().getItems().length + 20),
                context);
        addTotal(contentStream, calculateYCoordinate(context, 270 + 20 * context.getCustomerData().getItems().length + 40),
                context);
    }

    private float calculateYCoordinate(PdfContext context, float offset) {
        return context.getStartY() - offset;
    }
    public void addSubTotal(PDPageContentStream contentStream, float startY, PdfContext context) throws IOException {
        float totalY = startY - 20; 
        String subTotalText = "Subtotal: " + context.getCustomerData().getSubTotal();
        TextUtils.addTextToTheRight(contentStream, subTotalText, context, totalY);
    }

    public void addTax(PDPageContentStream contentStream, float startY, PdfContext context) throws IOException {
        float  totalY = startY - 40; 
        String taxText = "Tax: " + context.getCustomerData().getTax();
        TextUtils.addTextToTheRight(contentStream, taxText, context, totalY);
    }

    public void addTaxRate(PDPageContentStream contentStream, float startY, PdfContext context) throws IOException {
        float totalY = startY - 20; 
        String taxRateText = "Tax Rate: " + context.getCustomerData().getTaxRate() + "%";
        TextUtils.addTextToTheRight(contentStream, taxRateText, context, totalY);

    }

    public void addTotal(PDPageContentStream contentStream, float startY, PdfContext context) throws IOException {
        float totalY = startY - 60; 

        TextUtils.addTextToTheRight(contentStream, "Total: " + context.getCustomerData().getTotal(), context, totalY);

    }

    public static void drawTableRows(PDPageContentStream contentStream, float startY, PdfContext context)
            throws IOException {
        float margin = context.getMargin();
        float tableWidth = context.getA4_WIDTH() - margin * 2;
        float[] colRatios = { 0.08f, 0.42f, 0.15f, 0.17f, 0.18f };
        float[] colWidths = new float[colRatios.length];
        float[] positions = new float[colRatios.length];
        float x = margin;
        for (int i = 0; i < colRatios.length; i++) {
            colWidths[i] = tableWidth * colRatios[i];
            positions[i] = x;
            x += colWidths[i];
        }

        float rowHeight = 20;
        float y = startY;
        int index = 1;

        for (InvoiceItem item : context.getCustomerData().getItems()) {
            contentStream.setNonStrokingColor(Color.WHITE);
            for (int i = 0; i < colWidths.length; i++) {
                contentStream.addRect(positions[i], y - rowHeight + 5, colWidths[i], rowHeight);
                contentStream.fill();
            }

            contentStream.setNonStrokingColor(Color.BLACK);
            drawText(contentStream, String.valueOf(index), positions[0] + 5, y);
            drawText(contentStream, item.getDescription(), positions[1] + 5, y);
            drawText(contentStream, String.valueOf(item.getQuantity()), positions[2] + 5, y);
            drawText(contentStream, String.format("%.2f", item.getUnitPrice()), positions[3] + 5, y);
            drawText(contentStream, String.format("%.2f", item.getTotalPrice()), positions[4] + 5, y);

            y -= rowHeight;
            index++;
        }
    }

    private static void drawTableHeader(PDPageContentStream contentStream, float y, PdfContext context)
            throws IOException {
        String[] headers = { "No.", "Description", "Quantity", "Item Price", "Total" };

        float margin = context.getMargin();
        float tableWidth = context.getA4_WIDTH() - margin * 2;

        
        float[] colRatios = { 0.08f, 0.42f, 0.15f, 0.17f, 0.18f };

        
        float[] colWidths = new float[headers.length];
        float[] positions = new float[headers.length];
        float x = margin;
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = tableWidth * colRatios[i];
            positions[i] = x;
            x += colWidths[i];
        }

        float headerHeight = 22;

        for (int i = 0; i < headers.length; i++) {
            contentStream.addRect(positions[i], y - headerHeight + 5, colWidths[i], headerHeight);
            contentStream.setNonStrokingColor(context.getPrimaryColor());
            contentStream.fill();
        }

        for (int i = 0; i < headers.length; i++) {
            contentStream.setNonStrokingColor(Color.WHITE);
            drawText(contentStream, headers[i], positions[i] + 5, y);
        }
    }

    private static void drawText(PDPageContentStream content, String text, float x, float y)
            throws IOException {
        content.beginText();
        content.newLineAtOffset(x, y - 10);
        content.showText(text);
        content.endText();
    }

}
