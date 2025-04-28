package com.medipath.labcare.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.medipath.labcare.entity.Equipment;
import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.LabSample;
import com.medipath.labcare.entity.LabTest;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.LabReportRepository;
import com.medipath.labcare.repository.LabSampleRepository;
import com.medipath.labcare.repository.LabTestRepository;
import com.medipath.labcare.service.EquipmentService;
import com.medipath.labcare.service.LabReportService;
import com.medipath.labcare.service.LabSampleService;
import com.medipath.labcare.service.LabTestService;
import com.medipath.labcare.utility.PdfGenerator;

import jakarta.annotation.Resource;
import main.com.java.Utils.PDFGenerator;

@Controller
@RequestMapping("/labtech")
public class LabTechDashboardController {

    @Autowired
    private LabTestService labTestService;

    @Autowired
    private LabSampleService labSampleService;

    @Autowired
    private LabReportService labReportService;

    @Autowired
    private LabReportRepository labReportRepository;

    @Autowired
    private LabTestRepository labTestRepository;
    
    @Autowired
    LabSampleRepository labSampleRepository;

    // ************************1. MANAGE SAMPLES************************

    @GetMapping("/manage-samples")
    public String manageSamplesPage(Model model) {
        List<LabSample> samples = labSampleService.getAllLabSamples(); 
        model.addAttribute("labSamples", samples); // ✅ Renamed from "samples" to "labSamples"
        return "labtech/manage-samples";
    }

    
    
    @GetMapping("/add-sample")
    public String showAddSampleForm(Model model) {
        model.addAttribute("labSample", new LabSample()); // optional, for binding
        return "labtech/add-sample"; // matches your JSP file name
    }
    
    
    @PostMapping("/add-sample")
    public String addLabSample(@RequestParam String patientName,
                                @RequestParam String testType,
                                @RequestParam String status,
                                @RequestParam(required = false) String normalRange,
                                @RequestParam(required = false) String unit,
                                @RequestParam(required = false) String description) {
        // Create and populate a new LabSample object
        LabSample labSample = new LabSample();
        labSample.setPatientName(patientName);
        labSample.setTestType(testType);
        labSample.setStatus(status);  // Ensure status is set
        labSample.setNormalRange(normalRange);
        labSample.setUnit(unit);
        labSample.setDescription(description);

        // Save the LabSample entity to the database
        labSampleRepository.save(labSample);

        // Return success message
        return "redirect:/labtech/add-sample?message=Lab sample added successfully!";
    }


    
    @GetMapping("/sample/edit/{id}")
    public String editSampleForm(@PathVariable Long id, Model model) {
        LabSample sample = labSampleService.getLabSampleById(id);
        model.addAttribute("sample", sample);
        return "labtech/edit-sample"; // JSP page to render the edit form
    }

    @PostMapping("/sample/edit/{id}")
    public String updateSample(@PathVariable Long id, @ModelAttribute LabSample updatedSample, RedirectAttributes redirectAttributes) {
        // Update the lab sample using the service method
        labSampleService.updateLabSample(id, updatedSample);

        // Optionally add a success message
        redirectAttributes.addFlashAttribute("message", "Sample updated successfully!");

        // Redirect to manage-samples after update
        return "redirect:/labtech/manage-samples";  // Correct redirection URL
    }

    @PostMapping("/sample/update")
    public String updateSample(@ModelAttribute LabSample sample, RedirectAttributes redirectAttributes, Model model) {
        // Extract the sample ID from the LabSample object
        Long sampleId = sample.getId();

        // Call the service method with the ID and the updated sample object
        labSampleService.updateLabSample(sampleId, sample);

        // Add the updated sample to the model
        model.addAttribute("updatedSample", sample);

        // Optionally, add a success message
        model.addAttribute("message", "Sample updated successfully!");

        // Return to the manage-users or manage-samples page with updated data
        return "labtech/manage-samples"; // Or the relevant page you want to return to
    }



    @PostMapping("/sample/delete")
    public String deleteSamplePost(@RequestParam Long id) {
        labSampleService.deleteLabSample(id);
        return "redirect:/labtech/manage-samples?message=Sample+deleted+successfully";
    }

    
   
    // ************************2. ADD RESULTS************************

    // 🔹 Display the Add Results Page
    @GetMapping("/add-results")
    public String addResultsPage(Model model) {
        // Fetch available lab tests (optional, use if you want to show available tests in your form)
        List<LabTest> labTests = labTestService.getAllTests();
        
        // Fetch available users who are patients (ensure you have a way to filter by role, e.g., role='patient')
        List<User> patients = labReportService.getUsersByRole("patient");  // Adjust based on your role logic
        

        // Add both lists to the model
        model.addAttribute("labTests", labTests);
        model.addAttribute("patients", patients);

        return "labtech/add-results";
    }

    
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    // 🔹 Handle the Form Submission
    @PostMapping("/add-results")
    public ResponseEntity<?> addLabResult(
            @RequestParam("patientId") Long patientId,
            @RequestParam("testName") String testName,
            @RequestParam("testType") String testType,
            @RequestParam("category") String category,
            @RequestParam("status") String status,
            @RequestParam("testDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate testDate,
            @RequestParam("result") String result,
            @RequestParam("normalRange") String normalRange,
            @RequestParam("unit") String unit,
            @RequestParam("description") String description,
            @RequestParam("cost") Double cost
    ) throws IOException {

        // Fetch patient details using ID
        User patient = labReportService.getUserById(patientId); // Ensure this method exists

        if (patient == null) {
            // Return a structured error response
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Patient not found with ID: " + patientId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Save the LabTest (make sure it's linked to the correct patient)
        LabTest test = new LabTest();
        test.setPatientName(patient.getUsername()); // Ensure the patient name is being set
        test.setTestName(testName);
        test.setTestType(testType);
        test.setCategory(category);
        test.setStatus(status);
        test.setTestDate(testDate);
        test.setResult(result);
        test.setNormalRange(normalRange);
        test.setUnit(unit);
        test.setDescription(description);
        test.setCost(cost);
        LabTest savedTest = labTestService.saveTest(test);

        // Create & save LabReport
        LabReport report = new LabReport();
        report.setLabTest(savedTest);
        report.setPatient(patient);  // Ensure patient is set here
        report.setPatientName(patient.getUsername());
        report.setResult(result);
        report.setReportDate(testDate);
        report.setStatus(status);

        String pdfFilename = PdfGenerator.generateLabTestPDF(savedTest, UPLOAD_DIR);
        report.setPdfPath(pdfFilename);

        // Save the LabReport
        labReportService.saveReport(report);

        // Return the PDF as response
        ByteArrayInputStream pdfStream = PdfGenerator.generateLabTestPDFAsStream(savedTest);
        byte[] pdfBytes = pdfStream.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + testName.replaceAll("\\s+", "_") + "_Report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveLabTest(@ModelAttribute LabTest test) {
        // Save the LabTest entity
        LabTest savedTest = labTestService.saveTest(test);

        // Generate and save PDF
        String uploadDir = "/path/to/your/upload/folder"; // absolute or relative path
        String fileName = PdfGenerator.generateLabTestPDF(savedTest, uploadDir);

        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Failed to generate PDF"
            ));
        }

        // Create a download link to the saved PDF
        String downloadLink = "/labtest/download/" + fileName;

        return ResponseEntity.ok(Map.of(
            "message", "Test saved successfully",
            "downloadLink", downloadLink
        ));
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<UrlResource> downloadPDF(@PathVariable String fileName) {
        try {
            // Absolute path to your upload folder
            Path filePath = Paths.get("/path/to/your/upload/folder").resolve(fileName);
            
            // Ensure the file exists
            File file = filePath.toFile();
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // file not found
            }

            // Convert the path to URI
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // file not found
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // error in file URL
        }
    }


    // ************************3. UPLOAD REPORT************************

    /* =================== 1. Show page =================== */
    @GetMapping("/upload-report")
    public String showUploadReportPage() {
        return "labtech/upload-report";
    }

    
    @Value("${app.upload.dir}")
    private String uploadDir;
    
    // Handle file upload for report
 // Handle file upload for report
    @PostMapping("/upload-report")
    public String uploadReport(@RequestParam("labTestId") Long labTestId,
                               @RequestParam("reportFile") MultipartFile file,
                               RedirectAttributes ra) throws IOException {
      if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
        ra.addFlashAttribute("error","Please upload a PDF.");
        return "redirect:/labtech/upload-report";
      }

      // 1) Ensure directory exists
      Path dir = Paths.get(uploadDir);
      Files.createDirectories(dir);

      // 2) Save file
      String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      Path target = dir.resolve(filename);
      file.transferTo(target.toFile());

      // 3) Persist lab report
      LabTest labTest = labTestRepository.findById(labTestId)
                              .orElseThrow(() -> new IllegalArgumentException("Test not found"));
      LabReport report = new LabReport();
      report.setLabTest(labTest);
      report.setPatientName(labTest.getPatientName());
      report.setResult(labTest.getResult() != null ? labTest.getResult() : "--");
      report.setReportDate(LocalDate.now());
      report.setStatus("Completed");
      // **store only** the relative path under /uploads
      report.setPdfPath(filename);
      labReportRepository.save(report);

      ra.addFlashAttribute("message","Report uploaded!");
      return "redirect:/labtech/upload-report";
    }
    // Helper method to store file
    private String storeFile(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");
        Files.createDirectories(uploadDir);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    private String uploadFile(MultipartFile reportFile) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path dirPath = Paths.get(uploadDir);

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String fileName = System.currentTimeMillis() + "_" + reportFile.getOriginalFilename();
            Path filePath = dirPath.resolve(fileName);

            Files.copy(reportFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            System.err.println("⚠️ Error uploading file: " + e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }
    
    
    
    //equipment 
    
    
    private final EquipmentService equipmentService;

    public LabTechDashboardController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

 // ✅ Display all equipment
    @GetMapping("/equipment")
    public String showEquipmentList(Model model) {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        model.addAttribute("equipmentList", equipmentList);
        return "labtech/equipment-list"; // ✅ this matches your new JSP name
    }

    // ✅ Show add form
    @GetMapping("/equipment/add")
    public String showAddForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "labtech/equipment-form";
    }

    // ✅ Save new or updated equipment
    @PostMapping("/equipment/save")
    public String saveEquipment(@ModelAttribute Equipment equipment) {
        equipmentService.save(equipment);
        return "redirect:/labtech/equipment";
    }

    // ✅ Edit existing equipment
    @GetMapping("/equipment/edit/{id}")
    public String editEquipment(@PathVariable Long id, Model model) {
        Equipment eq = equipmentService.getById(id);
        model.addAttribute("equipment", eq);
        return "labtech/equipment-form";
    }

    // ✅ Delete equipment
    @GetMapping("/equipment/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.delete(id);
        return "redirect:/labtech/equipment";
    }

}
