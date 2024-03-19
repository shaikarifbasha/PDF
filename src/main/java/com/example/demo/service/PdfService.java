package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import net.glxn.qrgen.QRCode;

public class PdfService {
	public static void main(String[] args) throws Exception {
		PdfService pdfService = new PdfService();
		pdfService.generatePdf();
	}

	public void generatePdf() throws Exception {
		// create a new document
		Document document = new Document();
		// set the page size A4

		document.setMargins(40, 40, 40, 40);

		// create a writer that listens to the document
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\temp2\\test.pdf"));

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);

		// open the document
		document.open();

		// add content for 2 pages
		Font font = new Font(FontFamily.HELVETICA, 16);
		Paragraph paragraph = new Paragraph("TECHNICAL CLEARANCE", font);
		paragraph.setAlignment(Element.ALIGN_CENTER);

		document.add(paragraph);

		document.newPage();
		Font font1 = new Font(FontFamily.HELVETICA, 16);
		Paragraph paragraph1 = new Paragraph("TECHNICAL CLEARANCE", font);
		paragraph.setAlignment(Element.ALIGN_CENTER);

		document.add(paragraph);

		// save the pdf to the file system
		document.close();
		writer.close();
	}

	public void generatePdfV2() {

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
			logo.scaleToFit(100, 150);
			logo.setAlignment(Image.MIDDLE);
			logo.setAlignment(Image.TOP);
			logo.setAlignment(Image.ALIGN_LEFT);
			document.add(logo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String generateQRCodeImage(String barcodeText) throws Exception {
		try {
			ByteArrayOutputStream stream = QRCode.from(barcodeText).withSize(250, 250).stream();

			// Using try-with-resources for automatic resource management
			try (ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
					ByteArrayOutputStream os = new ByteArrayOutputStream()) {

				StringBuffer qr = new StringBuffer("data:image/png;base64,");
				ImageIO.write(ImageIO.read(bis), "png", os);
				qr.append(Base64.getEncoder().encodeToString(os.toByteArray()));
				return qr.toString();
			}
		} catch (final IOException ioe) {
			throw new Exception("error while generating QR code", ioe);
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
		canvas.moveTo(document.leftMargin(), document.bottomMargin() + 25);
		// canvas.lineTo(document.right() - document.rightMargin(),
		// document.bottomMargin());
		canvas.lineTo(document.right(), document.bottomMargin() + 25);
		canvas.stroke();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() + 10);
		canvas.showText("Plot No 13 A-2, EDC Complex");
		canvas.endText(); // close the text block

		// add footer text after the line
		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() - 5);
		canvas.showText("Patto Plaza, Panaji, Goa-403001");
		canvas.endText(); // close the text block

		// add text in right side
		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() + 10);
		canvas.showText("T: +91 8322437470");
		canvas.endText(); // close the text block

		// add text in right side
		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 5);
		canvas.showText("E: goa-idc@goa.gov.in");
		canvas.endText(); // close the text block

		// add text in right side
		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 22);
		canvas.showText("W: www.goa-idc.com");
		canvas.endText(); // close the text block
	}
}