package com.recceda.invoice.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.context.PdfContext;
import com.recceda.invoice.impl.sections.HeaderSection;
import com.recceda.invoice.impl.sections.PaymentInfoSection;
import com.recceda.invoice.impl.sections.PaymentTermsSection;
import com.recceda.invoice.impl.sections.TableSection;

public class InvoiceGenerator {

    private PDDocument document;
    private PDType0Font font;
    private PDType0Font fontBold;

    public InvoiceGenerator() {
        initializeDocument();
    }

    private void initializeDocument() {
        document = new PDDocument();
        PDPage invoicePage = new PDPage(PDRectangle.A4);
        document.addPage(invoicePage);

        try {
            InputStream futuraFont = getClass().getResourceAsStream("/futura.ttf");
            InputStream futuraBoldFont = getClass().getResourceAsStream("/futura_bold.ttf");

            font = PDType0Font.load(document, futuraFont);
            fontBold = PDType0Font.load(document, futuraBoldFont);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load font", e);
        }
    }

    public void generateInvoice(CustomerInvoiceData customerInvoiceData, String outputPath) {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0),
                PDPageContentStream.AppendMode.APPEND, true, true)) {

            PdfContext context = new PdfContext(customerInvoiceData, font, document, fontBold);
            HeaderSection headerSection = new HeaderSection();
            TableSection tableSection = new TableSection();
            headerSection.addToStream(context, contentStream);
            tableSection.addToStream(context, contentStream);
            PaymentInfoSection paymentInfoSection = new PaymentInfoSection();
            paymentInfoSection.addToStream(context, contentStream);
            PaymentTermsSection paymentTermsSection = new PaymentTermsSection();
            paymentTermsSection.addToStream(context, contentStream);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            document.save(outputPath);
            document.close();
            System.out.println("Invoice generated successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save invoice", e);
        }
    }

    public File generateInvoiceToPath(CustomerInvoiceData customerInvoiceData, String outputPath) {
        generateInvoice(customerInvoiceData, outputPath);
        return new File(outputPath);
    }

}
