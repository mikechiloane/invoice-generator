package com.recceda.invoice.impl.sections;

import com.recceda.invoice.common.TextUtils;
import com.recceda.invoice.context.PdfContext;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class HeaderSection implements PdfSection {


    @Override
    public void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception {

        try {

            addLogo(context, contentStream);
            addInvoiceNumberToStream(contentStream, context);
            addAddressToStream(contentStream, context);

        } catch (Exception e) {
            System.err.println("Error adding header section: " + e.getMessage());
            throw new RuntimeException("Failed to add header section", e);
        }
    }

    public void addInvoiceNumberToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        String invoiceText = "Invoice Number: " + context.getInvoiceNumber();
        TextUtils.addTextToTheRight(contentStream, invoiceText, context, context.getStartY());
    }

    public void addAddressToStream(PDPageContentStream contentStream, PdfContext context) throws IOException {
        float startY = context.getStartY() - 20;

        for (String line : Objects.requireNonNull(getAddressLinesFromFile())) {
            TextUtils.addTextToTheLeft(contentStream, line, context, startY);
            startY -= 15;
        }
    }

    public final String[] getAddressLinesFromFile() {
        File addressFile = new File("src/main/resources/address");
        if (!addressFile.exists()) {
            throw new RuntimeException("Address file not found: " + addressFile.getAbsolutePath());
        }

        String[] addressLines;
        try {
            addressLines = java.nio.file.Files.readString(addressFile.toPath()).split("\n");
        } catch (IOException e) {
            System.err.println("Error reading address file: " + e.getMessage());
            return null;
        }
        return addressLines;
    }

    private void addLogo(PdfContext context, PDPageContentStream contentStream) throws Exception {
        PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/logo.png", context.getDocument());
        float logHeight = 10;
        float logWidth = logo.getWidth() * logHeight / logo.getHeight();
        contentStream.drawImage(logo, context.getStartX(), context.getStartY() , logWidth, logHeight);
    }

}