package com.medipath.labcare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_enquiries")  // Specifies the table name in the database
public class PatientEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate the ID
    @Column(name = "id")  // Explicitly specify the column name for the ID field (optional)
    private Long id;

    @Column(name = "patient_name", nullable = false)  // Specify the column name and not null constraint
    private String patientName;

    @Column(name = "subject", nullable = false)  // Specify the column name for the subject
    private String subject;  // Subject of the enquiry

    @Column(name = "message", nullable = false)  // Specify the column name for the message
    private String message;  // Detailed message of the enquiry

    @Column(name = "status", nullable = false)  // Specify the column name and not null constraint
    private String status;  // Status of the enquiry (e.g., Pending, Resolved)

   

    // Default constructor
    public PatientEnquiry() {}

    // Constructor to initialize PatientEnquiry object with patientName, subject, message, status, and enquiryDetails
    public PatientEnquiry(String patientName, String subject, String message, String status, String enquiryDetails) {
        this.patientName = patientName;
        this.subject = subject;
        this.message = message;
        this.status = status;
        
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    // Override toString method to return the details of the enquiry
    @Override
    public String toString() {
        return "PatientEnquiry{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                
                '}';
    }
}
