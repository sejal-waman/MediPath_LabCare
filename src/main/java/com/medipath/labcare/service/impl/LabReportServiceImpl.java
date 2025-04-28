package com.medipath.labcare.service.impl;

import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.LabReportRepository;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.service.LabReportService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Service
public class LabReportServiceImpl implements LabReportService {

    private static final String UPLOAD_DIR = "/path/to/save/reports/";

    @Autowired
    private LabReportRepository labReportRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LabReport> getAllReports() {
        return labReportRepository.findAll();
    }

    @Override
    public Optional<LabReport> getReportsByPatientId(Long patientId) {
        return labReportRepository.findByPatientId(patientId).stream().findFirst();
    }

    @Override
    public void saveReport(LabReport report) {
        labReportRepository.save(report);
    }

    @Override
    public void deleteReport(Long id) {
        labReportRepository.deleteById(id);
    }

    @Override
    public List<LabReport> getReportsByPatient(User patient) {
        return labReportRepository.findByPatient(patient);
    }

    @Override
    public List<LabReport> getReportsByStatus(String status) {
        return labReportRepository.findByStatus(status);
    }

    @Override
    public List<LabReport> getReportsByDoctor(User doctor) {
        return labReportRepository.findByAssignedDoctor(doctor);
    }

    @Override
    public List<LabReport> getReportsForDoctor(Long doctorId) {
        Optional<User> doctor = labReportRepository.findDoctorById(doctorId);
        return doctor.map(this::getReportsByDoctor).orElse(List.of());
    }

    // Add the getLabReport method
    @Override
    public LabReport getLabReport(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The provided id must not be null");
        }
        return labReportRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("LabReport not found with id: " + id));
    }

    // Generates a PDF for the LabReport and returns it as a byte stream
    @Override
    public ByteArrayInputStream generateReportPdf(Long id) {
        LabReport report = getLabReport(id); // Use the new getLabReport method
        if (report == null) {
            System.out.println("🚫 No report found with ID: " + id);
            return null;
        }

        User patient = report.getPatient();
        if (patient == null) {
            System.err.println("🚨 LabReport with ID " + report.getId() + " has no associated patient!");
            return null; 
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Lab Report ID: " + report.getId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Patient Username: " + patient.getUsername());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Test Type: " + report.getTestType());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Test Results: " + report.getResult());
            contentStream.newLineAtOffset(0, -20);
            contentStream.endText();
            contentStream.close();

            document.save(out);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Error generating PDF for report ID: " + id);
            return null;
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void uploadReport(MultipartFile reportFile) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + reportFile.getOriginalFilename();
        File dest = new File(dir, fileName);
        reportFile.transferTo(dest);

        // Store the file path in the report entity
        LabReport report = new LabReport();
        report.setPdfPath(dest.getAbsolutePath());
        labReportRepository.save(report);
    }

    @Override
    public void sendLabReport(LabReport report) {
        System.out.println("📭 Email sending is disabled for this environment.");
    }

    @Override
    public void save(LabReport report) {
        labReportRepository.save(report);
    }

    @Override
    public LabReport getReportById(Long reportId) {
        return labReportRepository.findById(reportId).orElse(null);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findAllByRoles_Name(roleName);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public Optional<LabReport> findById(Long id) {
        LabReport labReport = entityManager.find(LabReport.class, id);
        return Optional.ofNullable(labReport);
    }
    
   
    
    @Override
    public List<LabReport> getReviewedReportsByPatient(User patient) {
        return labReportRepository.findByPatientAndStatus(patient, "Completed");
    }

    @Override
    public List<LabReport> findByPatient(User loggedInUser) {
        List<LabReport> reports = labReportRepository.findByPatient(loggedInUser);

        for (LabReport report : reports) {
            if (report.getAssignedDoctor() == null) {
                report.setAssignedDoctor(loggedInUser.getAssignedDoctor());
            }
        }

        return reports;
    }


    @Override
    public void markReportAsReviewed(Long reportId) {
        LabReport report = labReportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Lab report not found with id: " + reportId));
        report.setReviewed(true);  // Mark report as reviewed
        labReportRepository.save(report);  // Save the updated report
    }

}
