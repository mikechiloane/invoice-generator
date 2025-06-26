package com.recceda.invoice.api;

import java.io.File;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.common.InvoiceItem;

public class InvoiceGeneratorTest {

    private static final String DUMMY_PDF_PATH = "dummy_invoice.pdf";
    private static final String CUSTOM_PDF_PATH = "test_invoice.pdf";
    
    private InvoiceGenerator invoiceGenerator;
    private CustomerInvoiceData testCustomerData;

    @Before
    public void setUp() {
        invoiceGenerator = new InvoiceGenerator();
        
        InvoiceItem[] testItems = new InvoiceItem[] {
            new InvoiceItem("Test Product A", 2, 75.0, "High quality test product", "Electronics"),
            new InvoiceItem("Test Service B", 1, 150.0, "Premium test service", "Services"),
            new InvoiceItem("Test Item C", 3, 25.0, "Budget test item", "Accessories")
        };

        testCustomerData = new CustomerInvoiceData(
            "Test Customer Ltd",
            new String[] { "456 Test Avenue", "Test City", "Test Country" },
            testItems,
            "26 Jun 2025",
            "27 Jun 2025",
            "300.00",
            "30.00",
            "10",
            "330.00"
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

    @Test
    public void testGenerateInvoiceWithEmptyItems() {
        CustomerInvoiceData emptyItemsData = new CustomerInvoiceData(
            "Empty Items Customer",
            new String[] { "123 Empty St", "Empty City", "Empty Country" },
            new InvoiceItem[0],
            "26 Jun 2025",
            "27 Jun 2025",
            "0.00",
            "0.00",
            "0",
            "0.00"
        );

        try {
            invoiceGenerator.generateInvoice(emptyItemsData, "empty_items_test.pdf");
        } catch (Exception e) {
            fail("generateInvoice() should handle empty items gracefully: " + e.getMessage());
        }
        
        File pdf = new File("empty_items_test.pdf");
        assertTrue("PDF with empty items should be created", pdf.exists());
        assertTrue("PDF with empty items should not be empty", pdf.length() > 0);
        
        // Clean up this test file
        deleteFileIfExists("empty_items_test.pdf");
    }
}