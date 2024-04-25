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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class PdfService {
	public static void main(String[] args) throws Exception {
		PdfService pdfService = new PdfService();
		pdfService.generatePdf();
	}

	public void generatePdf() throws Exception {

		Document document = new Document();

		document.setMargins(30, 30, 30, 30);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("C:\\Users\\babashaik.ext\\Desktop\\pdf\\text.pdf"));

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);

		document.open();

		String qrCodeText = "Ref: BP/2024/000001,  Approved Dated: 29/01/2024,  Lessee: SANOFI INDIA LTD";
		String qrCodeImageBase64 = event.generateQRCodeImage(qrCodeText);

		try {

			byte[] qrCodeBytes = Base64.getDecoder().decode(qrCodeImageBase64);

			Image qrImage = Image.getInstance(qrCodeBytes);

			qrImage.scaleAbsolute(80, 80);

			float qrX = document.right() - qrImage.getScaledWidth() - 10;
			float qrY = document.top() - qrImage.getScaledHeight() - 10;

			qrImage.setAbsolutePosition(qrX, qrY);
			document.add(qrImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Paragraph gap = new Paragraph("\n");
		document.add(gap);
		Font font0 = new Font(FontFamily.HELVETICA, 9);
		Paragraph rightAlignedText0 = new Paragraph("Ref: BP/2024/000001", font0);
		rightAlignedText0.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText0.setIndentationRight(70);
		document.add(rightAlignedText0);

		Paragraph rightAlignedText10 = new Paragraph("Dated: 29/01/2024", font0);
		rightAlignedText10.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText10.setIndentationRight(70);
		document.add(rightAlignedText10);

		Font font = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
		Paragraph paragraph = new Paragraph();
		Chunk chunk = new Chunk("TECHNICAL CLEARANCE ORDER", font);
		chunk.setUnderline(1f, -2f);
		paragraph.add(chunk);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(gap);

		Font font1 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		String additionalContent1 = "Technical Clearance is hereby granted for the Construction of the Industrial Building as per the enclosed approved plans in Plot No: L-121, Phase No: III, of Verna Industrial Estate of Goa Industrial Development Corporation with the following conditions:-";
		Paragraph paragraph1 = new Paragraph(additionalContent1, font1);
		paragraph1.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph1);

		document.add(gap);
		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		List list = new List(List.ORDERED);
		list.setIndentationLeft(3);

		String[] listItems = {
				"Construction shall be strictly as per the approved plans. No changes shall be effected in the approved plans/approved built spaces without the prior permission of this Authority.",
				"The permission granted shall be revoked, if any information, plans, calculations, documents and any other accompaniments of the application are found incorrect or wrong at any stage after the grant of the permission and the applicant will not be entitled for any compensation.",
				"The permission shall be revoked if found expedient to such an action under the provision of Section 50 of The Goa Town and Country Planning Act, 1974.",
				"The development permission will not entitle the applicant for making/laying any claim on water and any other connection from the Government of Goa.",
				"The Developer/applicant should display a sign board of minimum size 1.00 mts. x 0.50 mts. with writing in black color on a white background at the site, as required under the Regulations.",
				"The soak pit should not be located within a distance of 15.00 meters from any other existing well in the plot area/plan.",
				"The commencement and the completion of the work shall be notified to the authority in writing in appropriate forms.",
				"Completion Certificate has to be obtained from this Authority before applying for Occupancy Certificate from the licensing authority.",
				"Storm water drain should be constructed along the boundary of the effected plot abutting to the road.",
				"Adequate Utility space for the dustbin, transformer etc. should be reserved within the plot area. In case of any cutting of sloppy land of filling of low-lying land, beyond permissible limits, prior permission of the IPDC (37B) shall be obtained before the commencement of the works per the provisions of Section 17(A) of The Goa Town and Country Planning Act, 1974.",
				"In case of compound walls, the gates shall open inwards only and traditional access, if any passing through the property shall not be blocked.",
				"The Ownership of the property shall be verified by the licensing body before the issuing of the licence." };
		Font orderedFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		float leading = 11f;
		for (int i = 0; i < listItems.length; i++) {
			ListItem listItem = new ListItem();
			listItem.setFont(orderedFont);
			Chunk textChunk = new Chunk(listItems[i]);
			listItem.add(textChunk);
			listItem.setLeading(leading);
			list.add(listItem);
		}

		document.add(list);

		document.add(gap);

		String additionalContent3 = "THIS ORDER IS ISSUED WITH REFERENCE TO THE APPLICATION BP/2024/000001 DATED 29/01/2024 FROM SANOFI INDIA LTD.";
		Paragraph paragraph3 = new Paragraph(additionalContent3, font2);
		paragraph3.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph3);
		document.add(gap);
		String additionalContent4 = "THIS ORDER IS VALID FOR THREE YEARS FROM THE DATE OF ISSUE OF CONSTRUCTION LICENCE, PROVIDED THE CONSTRUCTION LICENCE IS ISSUED WITHIN THE PERIOD OF THREE YEARS.";
		Paragraph paragraph4 = new Paragraph(additionalContent4, font2);
		paragraph4.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph4);
		document.add(gap);
		document.add(gap);
		document.add(gap);

		Paragraph rightAlignedText1 = new Paragraph("(Shivprasad Murari)", font2);
		rightAlignedText1.setAlignment(Element.ALIGN_RIGHT);
		rightAlignedText1.setIndentationRight(30);
		document.add(rightAlignedText1);

		Paragraph rightAlignedText2 = new Paragraph("Member Secretary/", font2);
		rightAlignedText2.setAlignment(Element.ALIGN_RIGHT);
		rightAlignedText2.setIndentationRight(30);
		document.add(rightAlignedText2);

		Paragraph rightAlignedText3 = new Paragraph("Dy. Town Planner IPDC", font2);
		rightAlignedText3.setAlignment(Element.ALIGN_RIGHT);
		rightAlignedText3.setIndentationRight(20);
		document.add(rightAlignedText3);

		Paragraph rightAlignedText4 = new Paragraph("Goa-IDC", font2);
		rightAlignedText4.setAlignment(Element.ALIGN_RIGHT);
		rightAlignedText4.setIndentationRight(50);
		document.add(rightAlignedText4);

		Paragraph rightAlignedText5 = new Paragraph("TO", font2);
		rightAlignedText5.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText5.setIndentationRight(40);
		document.add(rightAlignedText5);

		Paragraph rightAlignedText6 = new Paragraph("SANOFI INDIA LTD", font2);
		rightAlignedText6.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText6.setIndentationRight(50);
		document.add(rightAlignedText6);

		Paragraph rightAlignedText7 = new Paragraph("PLOT NO. L-121", font2);
		rightAlignedText7.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText7.setIndentationRight(40);
		document.add(rightAlignedText7);

		Paragraph rightAlignedText8 = new Paragraph("PH-III VIE", font2);
		rightAlignedText8.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText8.setIndentationRight(70);
		document.add(rightAlignedText8);

		Paragraph rightAlignedText9 = new Paragraph("Verna 403722", font2);
		rightAlignedText9.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText9.setIndentationRight(70);
		document.add(rightAlignedText9);

		document.close();
		writer.close();

	}

	public void generatePdfV2() {

	}

}

class HeaderFooterPageEvent extends PdfPageEventHelper {
	public void onStartPage(PdfWriter writer, Document document) {

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

	public String generateQRCodeImage(String barcodeText) throws IOException {
		ByteArrayOutputStream stream = QRCode.from(barcodeText).to(ImageType.PNG).withSize(250, 250).stream();

		byte[] imageBytes = stream.toByteArray();

		String base64Image = Base64.getEncoder().encodeToString(imageBytes);

		return base64Image;
	}

	public void onEndPage(PdfWriter writer, Document document) {

		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PdfContentByte canvas = writer.getDirectContent();

		canvas.setColorStroke(new BaseColor(0, 128, 0));

		canvas.setLineWidth(2);
		canvas.moveTo(document.leftMargin(), document.bottomMargin() + 25);

		canvas.lineTo(document.right(), document.bottomMargin() + 25);
		canvas.stroke();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() + 10);
		canvas.showText("Plot No 13 A-2, EDC Complex");
		canvas.endText();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.leftMargin(), document.bottomMargin() - 5);
		canvas.showText("Patto Plaza, Panaji, Goa-403001");
		canvas.endText();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() + 10);
		canvas.showText("T: +91 8322437470");
		canvas.endText();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 5);
		canvas.showText("E: goa-idc@goa.gov.in");
		canvas.endText();

		canvas.beginText();
		canvas.setFontAndSize(bf, 10);
		canvas.setTextMatrix(document.right() - 125, document.bottomMargin() - 22);
		canvas.showText("W: www.goa-idc.com");
		canvas.endText();
	}
}