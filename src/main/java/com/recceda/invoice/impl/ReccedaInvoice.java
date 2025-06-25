package com.recceda.invoice.impl;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.common.InvoiceItem;
import com.recceda.invoice.context.PdfContext;
import com.recceda.invoice.impl.sections.HeaderSection;
import com.recceda.invoice.impl.sections.PaymentInfoSection;
import com.recceda.invoice.impl.sections.PaymentTermsSection;
import com.recceda.invoice.impl.sections.TableSection;

public class ReccedaInvoice {

    PDDocument document;
    PDType0Font font;
    PDType0Font fontBold;

    public ReccedaInvoice() {
        document = new PDDocument();
        PDPage invoicePage = new PDPage(PDRectangle.A4);
        document.addPage(invoicePage);

        try {
            font = PDType0Font.load(document, new File("src/main/resources/futura.ttf"));
            fontBold = PDType0Font.load(document, new File("src/main/resources/futura_bold.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load font");
        }

    }

    public void generateInvoice() {

        try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0),
                PDPageContentStream.AppendMode.APPEND, true, true)) {

            InvoiceItem[] invoiceItems = new InvoiceItem[] {

                    new InvoiceItem("Item 1", 2, 50.0, "Descr", "Category A"),
                    new InvoiceItem("Item 1", 2, 50.0, "Descr", "Category A"),
                    new InvoiceItem("Item 1", 2, 50.0, "Descr", "Category A"),
                
            };

            CustomerInvoiceData customerInvoiceData = new CustomerInvoiceData("Faboda",
                    new String[] { "123 Default St.", "Default City", "Default Country" }, invoiceItems, "24 Jun 2025",
                    "25 Jun 2025", "300", "30", "3", "300");

            PdfContext context = new PdfContext(customerInvoiceData, font, document, fontBold);
            HeaderSection headerSection = new HeaderSection();
            TableSection tableSection = new TableSection();
            headerSection.addToStream(context, contentStream);
            tableSection.addToStream(context, contentStream);
            PaymentInfoSection paymentInfoSection = new PaymentInfoSection();
            paymentInfoSection.addToStream(context, contentStream);
            PaymentTermsSection paymentTermsSection = new PaymentTermsSection();
            paymentTermsSection.addToStream(context, contentStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            document.save("invoice.pdf");
            document.close();
            System.out.println("Invoice generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLogoToStream(PDPageContentStream contentStream, PDImageXObject logo, float startX, float startY,
            float logHeight) throws IOException {
        float logWidth = logo.getWidth() * logHeight / logo.getHeight();
        contentStream.drawImage(logo, startX, startY - logHeight - 10, logWidth, logHeight);
    }

    public String[] getAddressLinesFromFile() {
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

    public void addTextToTheLeft(PDPageContentStream contentStream, String text, float leftMarginX,
            float startY) throws IOException {
        float fontSize = 12;
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;

        float textX = leftMarginX;

        contentStream.beginText();
        contentStream.setFont(this.font, fontSize);
        contentStream.newLineAtOffset(textX, startY);
        contentStream.showText(text);
        contentStream.endText();
    }

    public void addTextToTheRight(PDPageContentStream contentStream, String text, float rightMarginX,
            float startY) throws IOException {
        float fontSize = 12;
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;

        float textX = rightMarginX - textWidth;

        contentStream.beginText();
        contentStream.setFont(this.font, fontSize);
        contentStream.newLineAtOffset(textX, startY);
        contentStream.showText(text);
        contentStream.endText();
    }

    public void addInvoiceNumberToStream(PDPageContentStream contentStream, String invoiceNumber, float rightMarginX,
            float startY) throws IOException {
        String text = "Invoice Number: " + invoiceNumber;
        float fontSize = 12;
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;

        float textX = rightMarginX - textWidth;

        contentStream.beginText();
        contentStream.setFont(this.font, fontSize);
        contentStream.newLineAtOffset(textX, startY);
        contentStream.showText(text);
        contentStream.endText();
    }

}
