package com.recceda.invoice.impl.sections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.recceda.invoice.common.TextUtils;
import com.recceda.invoice.context.PdfContext;

public class HeaderSection implements PdfSection {

    private static final Logger LOGGER = Logger.getLogger(HeaderSection.class.getName());
    private static final String ADDRESS_RESOURCE = "/address";
    private static final String LOGO_RESOURCE = "/logo.png";
    private static final float LOGO_HEIGHT = 10f;
    private static final float ADDRESS_LINE_SPACING = 15f;
    private static final float CUSTOMER_GAP_START = 60f;
    private static final float CUSTOMER_LINE_SPACING = 15f;

    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) {
        try {
            addLogo(context, contentStream);
            addInvoiceNumberToStream(contentStream, context);
            addAddressToStream(contentStream, context);
            addCustomerAddressToStream(contentStream, context);
            addInvoiceDateToStream(contentStream, context);
            addDueByDateToStream(contentStream, context);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding header section", e);
            throw new RuntimeException("Failed to add header section", e);
        }
    }

    public void addInvoiceDateToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        String invoiceDateText = "Invoice Date: " + context.getCustomerData().getInvoiceDate();
        TextUtils.addTextToTheRight(contentStream, invoiceDateText, context,
                context.getStartY() - CUSTOMER_GAP_START * 2 - CUSTOMER_LINE_SPACING * 2 * 2);
    }

    public void addDueByDateToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        String dueDateText = "Due by: " + context.getCustomerData().getInvoiceDueByDate();
        TextUtils.addTextToTheRight(contentStream, dueDateText, context,
                context.getStartY() - CUSTOMER_GAP_START * 2 - CUSTOMER_LINE_SPACING * 2 * 2 - ADDRESS_LINE_SPACING);
    }

    private void addInvoiceNumberToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        String invoiceText = "Invoice Number: " + context.getInvoiceNumber();
        TextUtils.addBoldTextToTheRight(contentStream, invoiceText, context, context.getStartY());
    }

    private void addCustomerAddressToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        String customerName = context.getCustomerData().getCustomerName();
        float gap = CUSTOMER_GAP_START;
        TextUtils.addBoldTextToTheRight(contentStream, "Invoiced to:", context, context.getStartY() - gap);

        TextUtils.addTextToTheRight(contentStream, customerName, context,
                context.getStartY() - gap - CUSTOMER_LINE_SPACING);

        String[] addressLines = context.getCustomerData().getAddressLines();
        if (addressLines == null || addressLines.length == 0)
            return;
        gap += CUSTOMER_LINE_SPACING * 2;
        for (String line : addressLines) {
            TextUtils.addTextToTheRight(contentStream, line, context, context.getStartY() - gap);
            gap += CUSTOMER_LINE_SPACING;
        }
    }

    private void addAddressToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        float startY = context.getStartY() - ADDRESS_LINE_SPACING * 3;
        String[] addressLines = getAddressLinesFromResource();

        for (String line : addressLines) {
            TextUtils.addTextToTheLeft(contentStream, line, context, startY - ADDRESS_LINE_SPACING);
            startY -= ADDRESS_LINE_SPACING;
        }
    }

    private String[] getAddressLinesFromResource() {
        try (InputStream is = getClass().getResourceAsStream(ADDRESS_RESOURCE)) {
            if (is == null) {
                throw new RuntimeException("Address resource not found: " + ADDRESS_RESOURCE);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().toArray(String[]::new);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading address resource", e);
            return new String[0];
        }
    }

    private void addLogo(PdfContext context, PDPageContentStream contentStream) throws IOException {
        try (InputStream logoStream = getClass().getResourceAsStream(LOGO_RESOURCE)) {
            if (logoStream == null) {
                LOGGER.warning("Logo resource not found: " + LOGO_RESOURCE);
                return;
            }
            PDImageXObject logo = PDImageXObject.createFromByteArray(context.getDocument(), logoStream.readAllBytes(),
                    "logo");
            float logWidth = logo.getWidth() * LOGO_HEIGHT / logo.getHeight();
            contentStream.drawImage(logo, context.getStartX(), context.getStartY(), logWidth, LOGO_HEIGHT);
        }
    }
}