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

public class PdfServiceCl {
	public static void main(String[] args) throws Exception {
		PdfServiceCl pdfService = new PdfServiceCl();
		pdfService.generatePdf();
	}

	public void generatePdf() throws Exception {

		Document document = new Document();

		document.setMargins(40, 40, 40, 40);

		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("C:\\Users\\babashaik.ext\\Desktop\\pdfOC\\cl.pdf"));

		HeaderFooterPageEventOc event = new HeaderFooterPageEventOc();
		writer.setPageEvent(event);

		document.open();

		String qrCodeText = "Ref: BP/CL/2024/000005,  Approved Dated: 29/01/2024,  Lessee: SANOFI INDIA LTD";
		String qrCodeImageBase64 = event.generateQRCodeImage(qrCodeText); // Generate QR code image

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

		Paragraph gap = new Paragraph("\n"); // Add an empty line
		document.add(gap);
		Font font0 = new Font(FontFamily.HELVETICA, 9);
		Paragraph rightAlignedText0 = new Paragraph("No : BP/CL/2024/000005", font0);
		rightAlignedText0.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText0.setIndentationRight(70);
		document.add(rightAlignedText0);

		Paragraph rightAlignedText10 = new Paragraph("Dated: 29/01/2024", font0);
		rightAlignedText10.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText10.setIndentationRight(70);
		document.add(rightAlignedText10);

		Font font = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
		Paragraph paragraph = new Paragraph();
		Chunk chunk = new Chunk("BUILDING /CONSTRUCTION LICENSE APPROVAL", font);
		chunk.setUnderline(1f, -2f); // Add underline with thickness and y-offset
		paragraph.add(chunk);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(gap);

		Font font2 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);

		String[] listItems = { "TO", "M/s. SANOFI INDIA." };
		String[] list1Items = {
				"Sub: Approval for proposed factory building for M/s. SANOFI INDIA in Plot no. L-121 Phase: III A, at Verna." };
		String[] list2Items = { "Ref:-" };
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

		String[] listItems1 = { "Inward no. 3065 dated 14/08/2023.",
				"Letter no. GIDC/GM(E)/EODB/Tuem/P-54/2023-24/2631 dated 28/08/2023.",
				"Technical Clearance no. GIDC/37-B/Tuem/P-54/2023-24/3780 dated 01/11/2023.",
				"Letter No. GIDC/GM(E)/DN/Tuem/54/2023-24/3852 dated 06/11/2023." };
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
		String additionalContent4 = "The plans submitted for revised plans toward Factory Building for M/s. Prakash in Plot no. L-121 Phase: III A, at Verna has been approved by us subject to following conditions:-";
		Paragraph paragraph4 = new Paragraph(additionalContent4, font2);
		paragraph4.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph4);
		document.add(gap);

		Paragraph rightAlignedText11 = new Paragraph("1. Total area considered for approval is as follows:-", font0);
		rightAlignedText10.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText10.setIndentationRight(70);
		document.add(rightAlignedText11);
		document.add(gap);

		Paragraph rightAlignedText12 = new Paragraph("Total area of the plot: 1000.00 m2", font0);
		rightAlignedText12.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText12.setIndentationRight(70);
		document.add(rightAlignedText12);

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(80);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(5f);

		Font headerFont = new Font(FontFamily.HELVETICA, 9, Font.BOLD);

		PdfPCell header1 = new PdfPCell(new Phrase("Area Specification", headerFont));
		header1.setHorizontalAlignment(Element.ALIGN_LEFT);
		header1.setPadding(5f);

		table.addCell(header1);

		PdfPCell header2 = new PdfPCell(new Phrase("Proposed built-up Area in M2", headerFont));
		header2.setHorizontalAlignment(Element.ALIGN_LEFT);
		header2.setPadding(5f);
		table.addCell(header2);

		PdfPCell header3 = new PdfPCell(new Phrase("Total Net Floor area in M2", headerFont));
		header3.setHorizontalAlignment(Element.ALIGN_LEFT);
		header3.setPadding(5f);
		table.addCell(header3);

		PdfPCell[] cells = { new PdfPCell(new Phrase("Lower Ground Floor", font0)),
				new PdfPCell(new Phrase("435.90 m2", font0)), new PdfPCell(new Phrase("435.90 m2", font0)),
				new PdfPCell(new Phrase("Ground Floor", font0)), new PdfPCell(new Phrase("322.99 m2", font0)),
				new PdfPCell(new Phrase("322.99 m2", font0)), new PdfPCell(new Phrase("Mezzanine Floor", font0)),
				new PdfPCell(new Phrase("0.00 m2", font0)), new PdfPCell(new Phrase("0.00 m2", font0)),
				new PdfPCell(new Phrase("Total Area", font0)), new PdfPCell(new Phrase("758.89 m2", font0)),
				new PdfPCell(new Phrase("758.89 m2", font0)), new PdfPCell(new Phrase("F.A.R", font0)),
				new PdfPCell(new Phrase("67.09%2", font0)), new PdfPCell(new Phrase("67.09%2", font0)),
				new PdfPCell(new Phrase("Covered Area", font0)), new PdfPCell(new Phrase("439.50 m2", font0)),
				new PdfPCell(new Phrase("439.50 m2", font0)), new PdfPCell(new Phrase("Coverage", font0)),
				new PdfPCell(new Phrase("38.85%2", font0)), new PdfPCell(new Phrase("38.85%2", font0)) };

		for (PdfPCell cell : cells) {
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		for (PdfPCell cell : cells) {
			cell.setPaddingTop(5f);
			cell.setPaddingBottom(5f);
			table.addCell(cell);
		}

		document.add(table);

		document.add(gap);
		List list2 = new List(List.ORDERED);
		list2.setIndentationLeft(3);
		list2.setFirst(2);

		String[] listItems2 = { "All set back should be maintained as shown in the plan.",
				"Maximum coverage of the plot should not exceed 60% of the plot area.",
				"Height of the walls above plinth should be maintained strictly as indicated in drawings.",
				"The ventilation excluding door opening should be minimum 20% of the floor area. In case of air conditioning is provided the said condition will be relaxed.",
				"The construction should be carried out strictly as per the plan approved.",
				"The safety of the structure shall be the responsibility of your Engineer/Architect.",
				"A line out may be got approved from Field Manager,Verna Industrial Estate before starting the construction.",
				"The applicant shall obtain necessary License/Approval/Permission/N.O.C etc. from the authorities such as Factory Inspector, Fire Fighting Services; Town & Country Planning etc. may be required under any law in force.",
				"The applicant should inform the date of power connection and also the date of commencement of the production for our office record",

		};
		Font orderedFont2 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		float leading2 = 11f;
		for (int i = 0; i < listItems2.length; i++) {
			ListItem listItem = new ListItem(listItems2[i], orderedFont2);
			listItem.setFont(orderedFont2);
			listItem.setLeading(leading2);
			listItem.setAlignment(Element.ALIGN_LEFT);

			list2.add(listItem);
		}

		document.add(list2);

		document.newPage();
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

		document.add(gap);

		List list3 = new List(List.ORDERED);
		list3.setIndentationLeft(3);
		list3.setFirst(11);

		String[] listItems3 = {
				"The applicant should not undertake the work of cutting of any hilly or sloppy land or filling up of any low lying land in, over or upon any hilly or sloppy land as the case may be without obtaining the prior written permission from the authorities.",
				"After the completion of the construction, the applicant shall obtain occupancy certificate from the GOA-IDC or from such other authority as may be appointed/specified for the purpose by the Govt. of Goa.",
				"This permission shall be valid for 3 years.",
				"You have paid license fees/construction fees of Rs. 4,41,169/- vide receipt no. PNJ/1229 dated 07/11/2023.",
				"The Permission is liable to be revoked if it is based on false information/wrong plans/calculation or on other accompaniment of the application are found to be incorrect or wrong at any stage or after the grant of the permission and the applicant will not be in the event of course of the action will be entitled for any compensation.",
				"Storage of water should be done in such a way that mosquito breeding doesn’t take place either by introducing fish in the tanks/wells or properly covering the iron drums/plastic tanks etc. or by observing dry day once a week.",
				"Overhead tanks/sumps should be provided with mosquito proof lid and other pipe fittings without any hole for the entry of mosquitoes. Outlet is to be covered by muslin/wire mesh. Ladder for inspection of the tanks to be installed if require.",
				"Curing water collection should be treated with anti-larval chemicals by the builders/contractors.",
				"Not to engage labourers for any construction/building work unless they are screened for malaria and possess health cards. These cards are to be renewed regularly every 3 months. Also arrangement should be done to get their blood tested immediately in case of fever and ensure that full treatment is taken in consultation with NVBDC Programme.",
				"Labourers to be provided with basic amenities like proper shelter, water for drinking and domestic purposes, proper sanitary conditions including toilet facilities.",
				"To fill the pits, ditches, water pools, etc. to avoid stagnations and to ensure no mosquito breeding sites in an around especially in unused items like tyres, bottles, tins etc.",
				"All septic tanks/soak pits to be constructed as per the specification required with proper mosquito proof arrangement so that there is no overflow/leakage that could given rise to stagnation and breeding of mosquitoes.",
				"Drains/Nallahs to be maintained clean around the site so that there is no blockade to flow of water. The gradient should be proper for drainage/flow and also proper cleaning of water should be done.",
				"The applicant shall obtain necessary license/Approval/Permission/N.O.C. etc. from the concerned authorities.",
				"The applicant shall comply with any observations raised by Fire & Emergency and inform the Corporation accordingly. Also, submit the NOC while applying for Occupancy.",
				"The applicant shall comply with any observations raised by Factories & Boilers and inform the Corporation accordingly. Also, submit the NOC while applying for Occupancy.",
				"The applicant shall obtain N.O.C. from Directorate of Health Services and inform the Corporation accordingly.",
				"“The applicant has to make provision for Roof Top Rain Water Harvesting as per the provision of “The Goa, Land Development and Building Construction Regulations, 2010” for factory buildings/sheds.",
				"Occupancy certificate will not be issued if any illegal/unauthorized structure exists.",

		};
		Font orderedFont3 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL);
		float leading3 = 11f;

		for (int i = 0; i < listItems3.length; i++) {
			ListItem listItem = new ListItem(listItems3[i], orderedFont3);
			listItem.setFont(orderedFont3);
			listItem.setLeading(leading3);
			listItem.setAlignment(Element.ALIGN_LEFT);

			list3.add(listItem);
		}

		document.add(list3);
		document.add(gap);
		Paragraph rightAlignedText13 = new Paragraph(
				"A copy of approved plan/plans may be collected from this office on producing/submitting copy of this approval letter.",
				font0);
		rightAlignedText13.setAlignment(Element.ALIGN_LEFT);
		rightAlignedText13.setIndentationLeft(3);
		document.add(rightAlignedText13);

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

class HeaderFooterPageEventCl extends PdfPageEventHelper {
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