package com.recceda.invoice.impl.sections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.common.TextUtils;
import com.recceda.invoice.context.PdfContext;

public class PaymentTermsSection implements PdfSection {
    private final String PAYMENT_TERMS_PATH = "/payment_terms";
    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception {
        float startY =  230; // Adjust the Y position as needed
        String paymentTerms = "Payment Terms";
        TextUtils.addBoldTextToTheLeft(contentStream, paymentTerms, context, startY);
        String[] paymentTermsLines = getPaymentTermsFromFile();
        startY -= 15; // Adjust the spacing between lines
        for (String line : paymentTermsLines) {
            TextUtils.addTextToTheLeft(contentStream, line, context, startY);
            startY -= 15; // Adjust the spacing between lines
        }
        
    }
    private String[] getPaymentTermsFromFile() {
        try (InputStream is = getClass().getResourceAsStream(PAYMENT_TERMS_PATH)) {
            if (is == null) {
                throw new RuntimeException("Address resource not found: " + PAYMENT_TERMS_PATH);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().toArray(String[]::new);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read address resource", e);
        }
    }
}
