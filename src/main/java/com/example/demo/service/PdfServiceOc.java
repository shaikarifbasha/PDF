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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class PdfServiceOc {
	public static void main(String[] args) throws Exception {
		PdfServiceOc pdfService = new PdfServiceOc();
		pdfService.generatePdf();
	}

	public void generatePdf() throws Exception {

		Document document = new Document();

		document.setMargins(40, 40, 40, 40);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("C:\\Users\\babashaik.ext\\Desktop\\pdfOC\\oc.pdf"));

		HeaderFooterPageEventOc event = new HeaderFooterPageEventOc();
		writer.setPageEvent(event);

		document.open();

		String qrCodeText = "Ref: BP/OC/2024/000005,  Approved Dated: 29/01/2024,  Lessee: SANOFI INDIA LTD";
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
		Paragraph rightAlignedText0 = new Paragraph("No : BP/OC/2024/000005", font0);
		rightAlignedText0.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText0.setIndentationRight(70);
		document.add(rightAlignedText0);

		Paragraph rightAlignedText10 = new Paragraph("Dated: 29/01/2024", font0);
		rightAlignedText10.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText10.setIndentationRight(70);
		document.add(rightAlignedText10);

		Font font = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
		Paragraph paragraph = new Paragraph();
		Chunk chunk = new Chunk("OCCUPANCY CERTIFICATE", font);
		chunk.setUnderline(1f, -2f);
		paragraph.add(chunk);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(gap);

		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		String[] listItems = { "TO", "M/s. SANOFI INDIA.,", "L-121", "Phase: III A", "Verna" };
		String[] list1Items = {
				"Sub: Issue of Occupancy Certificate for M/s. SANOFI INDIA, in Plot no. L-121 Phase: III A, at Verna Industrial Estate." };
		String[] list2Items = { "Ref.No.:" };
		Font orderedFont = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		for (String item : listItems) {
			ListItem listItem = new ListItem(item, orderedFont);
			document.add(listItem);
		}

		document.add(gap);

		for (String item : list1Items) {
			ListItem listItem = new ListItem(item, orderedFont);
			document.add(listItem);
		}
		document.add(gap);
		for (String item : list2Items) {
			ListItem listItem = new ListItem(item, orderedFont);
			document.add(listItem);
		}

		document.add(gap);

		List list1 = new List(List.ORDERED);
		list1.setIndentationLeft(3);

		String[] listItems1 = { "Inward no.11440 dated 15/03/2023.",
				"Letter no. GIDC/GME/EODB/Verna/M-14 to M-18/2022-23/09 dated 03/04/2023.",
				"Completion order no. Goa-IDC/Verna/M-14/2023-2024/720 dated 23/05/2023.",
				"Letter no. GIDC/GM(E)/App/Verna/M-14 to M-18/2021-22/3881 dated 23/12/2021.", };
		Font orderedFont1 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		float leading1 = 11f;

		for (int i = 0; i < listItems1.length; i++) {
			ListItem listItem = new ListItem();
			listItem.setFont(orderedFont);
			Chunk textChunk = new Chunk(listItems1[i]);
			listItem.add(textChunk);
			listItem.setLeading(leading1);
			list1.add(listItem);

		}

		document.add(list1);

		document.add(gap);

		String additionalContent3 = "Sir,";
		Paragraph paragraph3 = new Paragraph(additionalContent3, font2);
		paragraph3.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph3);
		String additionalContent4 = "With reference to the above letters, it is observed that the following structures are completed in all respects by M/s. SANOFI INDIA, in plot no. L-121 at Verna Industrial Estate and the same may be occupied, for which this Corporation has no objection subject to the following conditions:";
		Paragraph paragraph4 = new Paragraph(additionalContent4, font2);
		paragraph4.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph4);
		document.add(gap);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(40);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		Font headerFont = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
		Font cellFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);

		PdfPCell header1 = new PdfPCell(new Phrase("Area Specification", headerFont));
		header1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(header1);

		PdfPCell header2 = new PdfPCell(new Phrase("Total area in sq. m.", headerFont));
		header2.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(header2);

		PdfPCell cell1 = new PdfPCell(new Phrase("Ground Floor", cellFont));
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell1);

		PdfPCell cell2 = new PdfPCell(new Phrase("207.68", cellFont));
		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell2);

		PdfPCell cell3 = new PdfPCell(new Phrase("Total Area", cellFont));
		cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell3);

		PdfPCell cell4 = new PdfPCell(new Phrase("207.68", cellFont));
		cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell4);

		document.add(table);

		document.add(gap);
		List list2 = new List(List.ORDERED);
		list2.setIndentationLeft(3);

		String[] listItems2 = {
				"Storage of water should be done in such a way that mosquito breeding doesn’t take place either by introducing fish in the tanks/wells or properly covering the iron drums/plastic tanks etc. or by observing a dry day once a week.",
				"Overhead tanks/sumps should be provided with a mosquito-proof lid and other pipe fittings without any hole for the entry of mosquitoes. Outlet is to be covered by muslin/wire mesh. A ladder for inspection of the tanks to be installed if required.",
				"Drains/Nallahs to be maintained clean around the site so that there is no blockade to the flow of stormwater. The gradient should be proper for drainage/flow, and also proper cleaning of the drain should be done.",
				"The applicant should obtain necessary NOC from any other Authorities if applicable and as required under the law in force.",
				"You have paid license fees of Rs. 57,934/- vide receipt no. PNJ/626 dated 21/12/2021" };
		Font orderedFont2 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		float leading2 = 11f;
		for (int i = 0; i < listItems2.length; i++) {
			ListItem listItem = new ListItem();
			listItem.setFont(orderedFont2);
			Chunk textChunk = new Chunk(listItems2[i]);
			listItem.add(textChunk);
			listItem.setLeading(leading2);
			list2.add(listItem);

		}

		document.add(list2);

		document.add(gap);
		document.add(gap);
		document.add(gap);
		document.add(gap);

		String[] list3Items = { "Yours faithfully,", "General Manager (Civil Engg.)" };
		for (String item : list3Items) {
			ListItem listItem = new ListItem(item, orderedFont);
			document.add(listItem);
		}

		document.close();
		writer.close();
	}

	public void generatePdfV2() {

	}

}

class HeaderFooterPageEventOc extends PdfPageEventHelper {
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