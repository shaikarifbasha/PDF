package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfService {
    public static void main(String[] args) throws Exception {
        PdfService pdfService = new PdfService();
        pdfService.generatePdf();
    }

    public void generatePdf() throws Exception {
        // create a new document
        Document document = new Document();
        //set the page size A4

        document.setMargins(40, 40, 40, 40);

        // create a writer that listens to the document
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\codeP\\pdf.pdf"));

        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);

        // open the document
        document.open();

        // add content for 2 pages
        document.add(new Phrase("Hello World!"));
        document.newPage();
        document.add(new Phrase("Hello World!"));

        // save the pdf to the file system
        document.close();
        writer.close();
    }

    public void generatePdfV2(){
        
    }

}

class HeaderFooterPageEvent extends PdfPageEventHelper {
    public void onStartPage(PdfWriter writer, Document document) {
        // add logo to the document from base64 and set the position
        Image logo = null;
        try {
            ClassPathResource resource = new ClassPathResource("logo.txt");
            InputStream inputStream = resource.getInputStream();
            String stringTooLong = IOUtils.toString(inputStream, "UTF-8");
            byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(stringTooLong);
            logo = Image.getInstance(b);
            logo.scaleToFit(170, 250);
            logo.setAlignment(Image.MIDDLE);
            logo.setAlignment(Image.TOP);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEndPage(PdfWriter writer, Document document) {
       
        // add footer text after the line
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PdfContentByte canvas = writer.getDirectContent();

        canvas.setColorStroke(new BaseColor(0, 128, 0)); // RGB value for green
        // change the thikness of the line
        canvas.setLineWidth(2);
        canvas.moveTo(document.leftMargin(), document.bottomMargin()+25);
        //canvas.lineTo(document.right() - document.rightMargin(), document.bottomMargin());
        canvas.lineTo(document.right(), document.bottomMargin()+25);
        canvas.stroke();

        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() - 5);
        canvas.showText("Plot No 13 A-2, EDC Complex");
        canvas.endText(); // close the text block

        // add footer text after the line
        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() - 20);
        canvas.showText("Patto Plaza, Panaji, Goa 403 001");
        canvas.endText(); // close the text block

        // add footer text after the line
        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() - 35);
        canvas.showText(".");
        canvas.endText(); // close the text block

        // add text in right side
        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 5);
        canvas.showText("T: +91 832 2437470-73");
        canvas.endText(); // close the text block

        // add text in right side
        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 20);
        canvas.showText("E: goa-idc@goa.gov.in");
        canvas.endText(); // close the text block
        
        // add text in right side
        canvas.beginText();
        canvas.setFontAndSize(bf, 12);
        canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 35);
        canvas.showText("W: www.goaidc.com");
        canvas.endText(); // close the text block
    }
}