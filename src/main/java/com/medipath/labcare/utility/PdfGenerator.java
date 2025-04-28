package com.medipath.labcare.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.LabTest;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

/** Helper for generating stylish PDF reports for Lab Tests & Lab Reports. */
public class PdfGenerator {

    /*──────────────────────────────────── 1.  FILE‑BASED REPORT ────────────────────────────────────*/
    public static String generateLabTestPDF(LabTest test, String uploadDir) {
        Document document = new Document(PageSize.A4, 36, 36, 72, 72);
        String fileName = test.getTestName().replaceAll("\\s+", "_") + "_Report.pdf";
        File file = new File(uploadDir, fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {

            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();

            addLogo(document);
            addWatermark(writer);
            addLabHeader(document);  // Uses the default header (no patient info)
            addTestDetails(document, test);
            addFooter(document);

            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return fileName;
    }

    /*──────────────────────────────────── 2.  STREAM‑BASED REPORT ──────────────────────────────────*/
    public static ByteArrayInputStream generateLabTestPDFAsStream(LabTest test) {
        Document doc = new Document(PageSize.A4, 36, 36, 72, 72);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();

            addWatermark(writer);
            addLabHeader(doc);  // Uses the default header (no patient info)
            addTestDetails(doc, test);
            addFooter(doc);

            doc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /*──────────────────────────────────── 3.  PATIENT REPORT (LabReport) ────────────────────────────*/
    public static ByteArrayInputStream generate(LabReport report) {
        Document doc   = new Document(PageSize.A4, 36, 36, 72, 72);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();

            addWatermark(writer);
            addLabHeader(doc, report);  // Pass the LabReport here for patient info

            Font bold   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, BaseColor.BLACK);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd‑MM‑yyyy");

            PdfPTable t = new PdfPTable(2);
            t.setWidthPercentage(100);
            t.setSpacingBefore(15f);
            t.setWidths(new float[]{2.5f, 5f});

            // helper
            addCell(t, "Report ID:",   bold, BaseColor.LIGHT_GRAY);  addCell(t, String.valueOf(report.getId()),         normal, BaseColor.WHITE);
            addCell(t, "Patient:",     bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getPatient().getUsername(),      normal, BaseColor.WHITE);
            addCell(t, "Doctor:",      bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getAssignedDoctor().getUsername(),normal, BaseColor.WHITE);
            addCell(t, "Lab Test:",    bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getLabTest().getTestName(),      normal, BaseColor.WHITE);
            addCell(t, "Result:",      bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getResult(),                     normal, BaseColor.WHITE);
            addCell(t, "Report Date:", bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getReportDate().format(fmt),     normal, BaseColor.WHITE);
            addCell(t, "Remarks:",     bold, BaseColor.LIGHT_GRAY);  addCell(t, report.getRemarks(),                    normal, BaseColor.WHITE);

            doc.add(t);
            addFooter(doc);

            doc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    /*─────────────────────────────  HELPER SECTIONS  ─────────────────────────────*/

    private static void addLogo(Document document) {
        try {
            ClassPathResource logoFile = new ClassPathResource("static/images/lab.jpg");
            Image logo = Image.getInstance(IOUtils.toByteArray(logoFile.getInputStream()));
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            System.err.println("Logo not found ‑ continuing without it.");
        }
    }

    private static void addLabHeader(Document doc) throws DocumentException {
        // Default header without patient info
        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
        Font sub   = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

        Paragraph name    = new Paragraph("MediPath LabCare", title);
        Paragraph address = new Paragraph("123 Health Street, Pune‑411001\nEmail: support@medipath.com", sub);

        name.setAlignment(Element.ALIGN_CENTER);
        address.setAlignment(Element.ALIGN_CENTER);

        doc.add(name); 
        doc.add(address);
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        doc.add(Chunk.NEWLINE);
    }

    private static void addLabHeader(Document doc, LabReport report) throws DocumentException {
        // Header with patient info
        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
        Font sub   = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

        // Fetch patient name from LabReport
        String patientName = report.getPatient().getUsername();

        Paragraph name    = new Paragraph("MediPath LabCare", title);
        Paragraph address = new Paragraph("123 Health Street, Pune‑411001\nEmail: support@medipath.com", sub);
        Paragraph patient = new Paragraph("Patient: " + patientName, sub);

        name.setAlignment(Element.ALIGN_CENTER);
        address.setAlignment(Element.ALIGN_CENTER);
        patient.setAlignment(Element.ALIGN_CENTER);

        doc.add(name); doc.add(address);
        doc.add(patient);  // Add patient name
        doc.add(Chunk.NEWLINE);
        doc.add(new LineSeparator());
        doc.add(Chunk.NEWLINE);
    }

    private static void addTestDetails(Document doc, LabTest test) throws DocumentException {
        Font label  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
        Font value  = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{2, 5});

        addCell(table, "Test Name:",     label, BaseColor.LIGHT_GRAY); addCell(table, test.getTestName(),     value, BaseColor.WHITE);
        addCell(table, "Test Type:",     label, BaseColor.LIGHT_GRAY); addCell(table, test.getTestType(),     value, BaseColor.WHITE);
        addCell(table, "Category:",      label, BaseColor.LIGHT_GRAY); addCell(table, test.getCategory(),      value, BaseColor.WHITE);
        addCell(table, "Cost (INR):",    label, BaseColor.LIGHT_GRAY); addCell(table, "₹" + test.getCost(),    value, BaseColor.WHITE);
        addCell(table, "Normal Range:",  label, BaseColor.LIGHT_GRAY); addCell(table, test.getNormalRange(),  value, BaseColor.WHITE);
        addCell(table, "Unit:",          label, BaseColor.LIGHT_GRAY); addCell(table, test.getUnit(),          value, BaseColor.WHITE);
        addCell(table, "Description:",   label, BaseColor.LIGHT_GRAY); addCell(table, test.getDescription(),   value, BaseColor.WHITE);

        doc.add(table);
    }

    private static void addFooter(Document doc) throws DocumentException {
        Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.GRAY);
        Paragraph footer = new Paragraph("This is a system‑generated report. No signature required.", subFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        doc.add(Chunk.NEWLINE);
        doc.add(footer);
    }

    private static void addCell(PdfPTable tbl, String txt, Font f, BaseColor bg) {
        PdfPCell c = new PdfPCell(new Phrase(txt, f));
        c.setBackgroundColor(bg);
        c.setBorder(Rectangle.NO_BORDER);
        c.setPadding(5);
        tbl.addCell(c);
    }

    /** Adds a light‑gray, semi‑transparent watermark diagonally across the page. */
    private static void addWatermark(PdfWriter writer) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        // semi‑transparent light‑gray
        BaseColor translucent = new BaseColor(160, 160, 160, 60); // last arg = alpha (0‑255)
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 60, translucent);

        Phrase stamp = new Phrase("MediPath LabCare", font);
        Rectangle page = writer.getPageSize();
        float x = page.getWidth() / 2;
        float y = page.getHeight() / 2;

        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, stamp, x, y, 45);
    }
}
