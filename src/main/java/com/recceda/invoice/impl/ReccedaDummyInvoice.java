package com.recceda.invoice.impl;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.common.InvoiceItem;
import com.recceda.invoice.context.PdfContext;
import com.recceda.invoice.impl.sections.HeaderSection;
import com.recceda.invoice.impl.sections.PaymentInfoSection;
import com.recceda.invoice.impl.sections.PaymentTermsSection;
import com.recceda.invoice.impl.sections.TableSection;

public class ReccedaDummyInvoice {

    PDDocument document;
    PDType0Font font;
    PDType0Font fontBold;

    public ReccedaDummyInvoice() {
        document = new PDDocument();
        PDPage invoicePage = new PDPage(PDRectangle.A4);
        document.addPage(invoicePage);

        try {
            font = PDType0Font.load(document, new File("src/main/resources/futura.ttf"));
            fontBold = PDType0Font.load(document, new File("src/main/resources/futura_bold.ttf"));
        } catch (IOException e) {
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

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            document.save("invoice.pdf");
            document.close();
            System.out.println("Invoice generated successfully!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save invoice", e);
        }
    }

}
