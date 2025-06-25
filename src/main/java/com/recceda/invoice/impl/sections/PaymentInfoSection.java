package com.recceda.invoice.impl.sections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.common.TextUtils;
import com.recceda.invoice.context.PdfContext;

public class PaymentInfoSection implements PdfSection {

    private static final String BANKING_DATA_PATH = "/banking";

    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception {
        try {
            addPaymentInfoToStream(contentStream, context);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add payment info section", e);
        }
    }

    private void addPaymentInfoToStream(PDPageContentStream contentStream, PdfContext context) throws Exception {

        contentStream.setLineWidth(0.9f);
        contentStream.moveTo(context.getMargin(), 170);
        contentStream.lineTo(context.getA4_WIDTH() - context.getMargin(), 170);
        contentStream.stroke();
        TextUtils.addBoldTextToTheLeft(contentStream, "Payment Information", context, 150);
        String[] bankingDetails = getBankingDetailsFromFile();
        float startY = 135;
        for (String line : bankingDetails) {
            TextUtils.addTextToTheLeft(contentStream, line, context, startY);
            startY -= 15;
        }
    }

    private String[] getBankingDetailsFromFile() {
        try (InputStream is = getClass().getResourceAsStream(BANKING_DATA_PATH)) {
            if (is == null) {
                throw new RuntimeException("Address resource not found: " + BANKING_DATA_PATH);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().toArray(String[]::new);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read address resource", e);
        }
    }

}
