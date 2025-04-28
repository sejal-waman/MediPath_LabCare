package com.medipath.labcare.entity;

import jakarta.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "lab_reports")
public class LabReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // This will cascade the delete action
    private User patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LabTest labTest;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "submitted_by")
    private String submittedBy;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User assignedDoctor;
    
 
  

    @Column(name = "pdf_path")
    private String pdfPath;

    @Lob
    @Column(name = "pdf_data", columnDefinition = "LONGBLOB")
    private byte[] pdfData;

    @Transient
    private String formattedDate;

    // ==================== Getters & Setters ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public User getPatient() { return patient; }
    public void setPatient(User patient) { 
        this.patient = patient;
        if (patient != null) {
            this.patientName = patient.getUsername(); // Ensure patient name is set when patient is set
        }
    }

    public LabTest getLabTest() { return labTest; }
    public void setLabTest(LabTest labTest) { this.labTest = labTest; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDate getReportDate() { return reportDate; }
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        this.formattedDate = null;
    }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getAssignedDoctor() { return assignedDoctor; }
    public void setAssignedDoctor(User assignedDoctor) { this.assignedDoctor = assignedDoctor; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public byte[] getPdfData() { return pdfData; }
    public void setPdfData(byte[] pdfData) { this.pdfData = pdfData; }

    // ==================== Updated Logic ====================

    // Remove redundant doctorId field and method
    // Use the assignedDoctor's ID instead.
    public Long getDoctorId() {
        return (assignedDoctor != null) ? assignedDoctor.getId() : null;
    }

    public void setDoctorId(Long doctorId) {
        if (assignedDoctor == null) {
            assignedDoctor = new User();
        }
        assignedDoctor.setId(doctorId);
    }

    // ==================== Transient/Helper Methods ====================

    public String getTestName() {
        return (labTest != null) ? labTest.getTestName() : "Unnamed Test";
    }

    public String getTestCategory() {
        return (labTest != null) ? labTest.getCategory() : "Uncategorized";
    }

    public Double getTestCost() {
        return (labTest != null) ? labTest.getCost() : 0.0;
    }

    public String getTestType() {
        return (labTest != null && labTest.getTestType() != null && !labTest.getTestType().isEmpty())
                ? labTest.getTestType() : "General";
    }

    public String getShortResult() {
        if (result == null || result.trim().isEmpty()) return "Pending";
        return result.length() > 100 ? result.substring(0, 100) + "..." : result;
    }

    public String getDoctorName() {
        return (assignedDoctor != null) ? assignedDoctor.getUsername() : "Not Assigned";
    }

    public String getFormattedDate() {
        if (formattedDate == null && reportDate != null) {
            formattedDate = reportDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return formattedDate;
    }

    public void setReportFile(byte[] reportBytes) {
        this.pdfData = reportBytes;
        this.pdfPath = null;
    }

    public void savePdfToFileSystem(String uploadDirectory) throws IOException {
        if (this.pdfData != null) {
            String filePath = uploadDirectory + "/report_" + this.id + ".pdf";
            Files.write(Paths.get(filePath), this.pdfData);
            this.pdfPath = filePath;
        }
    }

    @Override
    public String toString() {
        return "LabReport{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", testName='" + getTestName() + '\'' +
                ", result='" + result + '\'' +
                ", reportDate=" + reportDate +
                ", status='" + status + '\'' +
                ", doctor='" + getDoctorName() + '\'' +
                '}';
    }

    public void setReportDate(Date date) {
        if (date != null) {
            if (date instanceof java.sql.Date) {
                this.reportDate = ((java.sql.Date) date).toLocalDate();
            } else {
                this.reportDate = date.toInstant()
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDate();
            }
        } else {
            this.reportDate = null;
        }
    }

    @Transient
    public Date getReportDateAsUtilDate() {
        return (reportDate != null)
            ? Date.from(reportDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            : null;
    }

    @Column(name = "reviewed")
    private boolean reviewed = false;

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isReviewed() {
        return reviewed;
    }
    
   
  

}
