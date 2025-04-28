package com.medipath.labcare.controller;

import java.io.ByteArrayOutputStream;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.medipath.labcare.entity.Bill;
import com.medipath.labcare.entity.LabTest;
import com.medipath.labcare.entity.PatientEnquiry;
import com.medipath.labcare.repository.PatientEnquiryRepository;
import com.medipath.labcare.service.BillService;
import com.medipath.labcare.service.LabTestService;
import com.medipath.labcare.service.PatientEnquiryService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/receptionist")
public class ReceptionDashboardController {

    @Autowired
    private PatientEnquiryService patientEnquiryService;

    @GetMapping("/patient-enquiries")
    public String getPatientEnquiries(Model model) {
        List<PatientEnquiry> enquiries = patientEnquiryService.getAllPatientEnquiries();
        model.addAttribute("enquiries", enquiries);
        return "receptionist/patient-enquiries"; // Make sure this matches the JSP filename and path
    }
    
    @Autowired
    private PatientEnquiryRepository patientEnquiryRepository;
    
    @PostMapping("/solve-enquiry")
    public String solveEnquiry(@RequestParam("enquiryId") Long enquiryId, Model model) {
        try {
            // Fetch the enquiry by ID and update the status
            Optional<PatientEnquiry> optionalEnquiry = patientEnquiryRepository.findById(enquiryId);
            
            if (optionalEnquiry.isPresent()) {
                PatientEnquiry enquiry = optionalEnquiry.get();
                enquiry.setStatus("Solved");
                patientEnquiryRepository.save(enquiry);  // Save the updated enquiry
                model.addAttribute("message", "Enquiry has been solved successfully.");
            } else {
                model.addAttribute("errorMessage", "Enquiry not found.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while solving the enquiry.");
        }
        return "redirect:/receptionist/patient-enquiries";   // Redirect back to the enquiry page
    }

    @Autowired
   private LabTestService labTestService;
    
    @Autowired
    private BillService billService;
    
    //GET method to display lab results for billing
    @GetMapping("/billing")
    public String displayLabResultsForBilling(Model model) {
        // Fetch all lab results or filter based on criteria like patient ID, test status, etc.
        List<LabTest> labResults = labTestService.findAllLabResults();
        model.addAttribute("labResults", labResults);
        return "receptionist/billing";  // JSP page where the receptionist can view results and generate bills
    }

    // POST method to generate a bill based on selected lab result
    @PostMapping("/generate-bill")
    public ResponseEntity<byte[]> generateBill(@RequestParam("resultId") Long resultId, Model model) {
        // Fetch the lab result using the provided result ID
        Optional<LabTest> labResultOpt = labTestService.findById(resultId);

        if (labResultOpt.isPresent()) {
            LabTest labResult = labResultOpt.get();

            // Check if bill already generated
            if (labResult.isBillGenerated()) {
                model.addAttribute("errorMessage", "Bill has already been generated for this test.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Check if all required fields are available
            if (labResult.getPatientName() == null || labResult.getTestName() == null || labResult.getCost() == null) {
                model.addAttribute("errorMessage", "Invalid lab result data. Missing patient name, test name, or cost.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Hospital Details
            String hospitalName = "MediPath LabCare";
            String hospitalEmail = "support@medipath.com";
            String hospitalPhone = "+91 9373768626";
            String hospitalAddress = "123 Health Street, Pune-411001, India";

            // Generate the PDF bill
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, outputStream);
                document.open();

                Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

                Paragraph hospitalInfo = new Paragraph();
                hospitalInfo.add(new Chunk(hospitalName, headerFont));
                hospitalInfo.add(new Chunk("\n" + hospitalAddress));
                hospitalInfo.add(new Chunk("\n" + hospitalPhone));
                hospitalInfo.add(new Chunk("\n" + hospitalEmail));
                hospitalInfo.setAlignment(Element.ALIGN_CENTER);
                document.add(hospitalInfo);
                document.add(new Chunk("\n\n"));

                Paragraph billTitle = new Paragraph("Bill for Patient: " + labResult.getPatientName(), new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
                billTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(billTitle);
                document.add(new Chunk("\n\n"));

                PdfPTable table = new PdfPTable(2);
                table.setWidths(new float[] {1, 3});

                table.addCell(new PdfPCell(new Phrase("Test Name"))).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(new PdfPCell(new Phrase(labResult.getTestName())));

                table.addCell(new PdfPCell(new Phrase("Test Type"))).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(new PdfPCell(new Phrase(labResult.getTestType())));

                table.addCell(new PdfPCell(new Phrase("Test Date"))).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(new PdfPCell(new Phrase(labResult.getTestDate().toString())));

                table.addCell(new PdfPCell(new Phrase("Cost"))).setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(new PdfPCell(new Phrase(String.valueOf(labResult.getCost()))));

                document.add(table);
                document.add(new Chunk("\n\n"));

                Paragraph footer = new Paragraph("Thank you for choosing " + hospitalName + ". We wish you good health.");
                footer.setAlignment(Element.ALIGN_CENTER);
                document.add(footer);

                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Error while generating bill PDF.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // --- IMPORTANT: Mark bill as generated and save ---
            labResult.setBillGenerated(true);
            labTestService.saveTest(labResult); // Save the updated lab result to database

            // Create the file name dynamically
            String fileName = labResult.getPatientName() + "-Bill.pdf";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=" + fileName);

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .contentType(MediaType.APPLICATION_PDF)
                                 .body(outputStream.toByteArray());
        } else {
            model.addAttribute("errorMessage", "Lab result not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}

