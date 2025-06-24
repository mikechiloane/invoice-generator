package com.recceda.invoice.impl.sections;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.context.PdfContext;

public class TableSection implements PdfSection {

    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception {
        drawTableHeader(contentStream, context.getStartY() - 250);

    }

    private static void drawTableHeader(PDPageContentStream contentStream, float y) throws IOException {
        String[] headers = { "No.", "Description", "Quantity", "Item Price", "Total" };
        float[] positions = { 50, 100, 300, 400, 500 };
        float headerHeight = 22;
        float[] colWidths = { 50, 200, 100, 100, 100 }; // Adjust as needed

        // Draw background rectangles
        float x = positions[0];
        for (int i = 0; i < headers.length; i++) {
            contentStream.setNonStrokingColor(255, 0, 68); //rgb(39, 0, 86)
            contentStream.addRect(positions[i], y - headerHeight + 5, colWidths[i], headerHeight);
            contentStream.fill();
        }

        // Draw header text
        for (int i = 0; i < headers.length; i++) {
            contentStream.setNonStrokingColor(255, 255, 255); // White text
            drawText(contentStream, headers[i], positions[i] + 5, y, 12, true);
        }
    }

    private static void drawText(PDPageContentStream content, String text, float x, float y, int fontSize, boolean bold)
            throws IOException {
        content.beginText();
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
    }

}
