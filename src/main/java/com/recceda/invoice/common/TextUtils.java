package com.recceda.invoice.common;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.recceda.invoice.context.PdfContext;

public class TextUtils {
    public static void addTextToTheLeft(PDPageContentStream contentStream, String text, PdfContext context,
            float startY) throws IOException {
        float fontSize = 12;
        contentStream.beginText();
        contentStream.setFont(context.getFont(), fontSize);
        contentStream.newLineAtOffset(context.getStartX(), startY);
        contentStream.showText(text);
        contentStream.endText();
    }

    public static  void addTextToTheRight(PDPageContentStream contentStream, String text, PdfContext context,
            float startY) throws IOException {
        float fontSize = 12;
        float textWidth = context.getFont().getStringWidth(text) / 1000 * fontSize;
        float textX = context.getA4_WIDTH() - context.getMargin() - textWidth;

        contentStream.beginText();
        contentStream.setFont(context.getFont(), fontSize);
        contentStream.newLineAtOffset(textX, startY);
        contentStream.showText(text);
        contentStream.endText();
    }
}
