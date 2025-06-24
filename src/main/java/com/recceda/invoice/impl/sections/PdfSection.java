package com.recceda.invoice.impl.sections;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.context.PdfContext;

public interface PdfSection {

    /**
     * Adds the section content to the PDF stream.
     * @param context
     * @param contentStream
     * @throws Exception
     */
    void addToStream(PdfContext context, PDPageContentStream contentStream) throws Exception;

}
