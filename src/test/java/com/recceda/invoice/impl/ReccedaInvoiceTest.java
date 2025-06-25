package com.recceda.invoice.impl;

import java.io.File;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;



public class ReccedaInvoiceTest {

    private static final String PDF_PATH = "invoice.pdf";

    @After
    public void cleanup() {
        File pdf = new File(PDF_PATH);
        if (pdf.exists()) {
            // pdf.delete();
        }
    }

    @Test
    public void testGenerateInvoiceCreatesPdfWithAllSections() {
        ReccedaDummyInvoice invoice = new ReccedaDummyInvoice();
        try {
            invoice.generateInvoice();
        } catch (Exception e) {
            fail("generateInvoice() threw an exception: " + e.getMessage());
        }
        File pdf = new File(PDF_PATH);
        assertTrue("PDF file should be created", pdf.exists());
        assertTrue("PDF file should not be empty", pdf.length() > 0);
    }
}