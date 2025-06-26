package com.recceda.invoice.api;

import java.io.File;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.common.InvoiceDates;
import com.recceda.invoice.common.InvoiceItem;
import com.recceda.invoice.common.TotalsAndTaxInfo;

public class InvoiceGeneratorTest {

    private static final String DUMMY_PDF_PATH = "dummy_invoice.pdf";
    private static final String CUSTOM_PDF_PATH = "test_invoice.pdf";
    
    private InvoiceGenerator invoiceGenerator;
    private CustomerInvoiceData testCustomerData;

    @Before
    public void setUp() {
        invoiceGenerator = new InvoiceGenerator();
        
        InvoiceItem[] testItems = new InvoiceItem[] {
            new InvoiceItem( 2, 75.0, "High quality test product", "Electronics"),
            new InvoiceItem( 1, 150.0, "Premium test service", "Services"),
            new InvoiceItem( 3, 25.0, "Budget test item", "Accessories")
        };

        testCustomerData = new CustomerInvoiceData(
            "Test Customer",
            new String[] { "123 Test St", "Test City", "Test State", "12345" },
            testItems,
            new InvoiceDates("2023-10-01", "2023-10-15"),
            new TotalsAndTaxInfo("250.00", "25.00", "10%", "275.00")
        );
    }

    @After
    public void cleanup() {
        deleteFileIfExists(DUMMY_PDF_PATH);
        deleteFileIfExists(CUSTOM_PDF_PATH);
    }

    private void deleteFileIfExists(String filePath) {
        File pdf = new File(filePath);
        if (pdf.exists()) {
            pdf.delete();
        }
    }

    @Test
    public void testGenerateDummyInvoiceCreatesPdf() {
        try {
            invoiceGenerator.generateInvoice(this.testCustomerData, DUMMY_PDF_PATH);
        } catch (Exception e) {
            fail("generateDummyInvoice() threw an exception: " + e.getMessage());
        }
        
        File pdf = new File(DUMMY_PDF_PATH);
        assertTrue("Dummy PDF file should be created", pdf.exists());
        assertTrue("Dummy PDF file should not be empty", pdf.length() > 0);
    }

    @Test
    public void testGenerateInvoiceWithCustomerDataAndPath() {
        try {
            invoiceGenerator.generateInvoice(testCustomerData, CUSTOM_PDF_PATH);
        } catch (Exception e) {
            fail("generateInvoice(customerData, path) threw an exception: " + e.getMessage());
        }
        
        File pdf = new File(CUSTOM_PDF_PATH);
        assertTrue("PDF file should be created at custom location", pdf.exists());
        assertTrue("PDF file should not be empty", pdf.length() > 0);
    }


}