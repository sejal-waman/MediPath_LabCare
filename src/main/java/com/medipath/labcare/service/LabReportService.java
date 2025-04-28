package com.medipath.labcare.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.Prescription;
import com.medipath.labcare.entity.User;

import jakarta.mail.MessagingException;

public interface LabReportService {
    List<LabReport> getAllReports();
   
    Optional<LabReport> getReportsByPatientId(Long patientId); // use plural for clarity

    void saveReport(LabReport report);
    void deleteReport(Long id);

    // Optional Advanced Filters (future use 💡)
    List<LabReport> getReportsByPatient(User patient);
    List<LabReport> getReportsByStatus(String status);
    List<LabReport> getReportsByDoctor(User doctor);
	
	List<LabReport> getReportsForDoctor(Long id);
	
	void markReportAsReviewed(Long reportId);
	
	void sendLabReport(LabReport report) throws MessagingException;

	
	void uploadReport(MultipartFile reportFile) throws IOException;
	
	Optional<LabReport> findById(Long id);

	
    void save(LabReport report);
    ByteArrayInputStream generateReportPdf(Long id);

	LabReport getReportById(Long reportId);

	List<User> getUsersByRole(String string);

	User getUserById(Long patientId);

	LabReport getLabReport(Long id);
	
	List<LabReport> getReviewedReportsByPatient(User patient);

	List<LabReport> findByPatient(User loggedInUser);

	//void generateAndSavePdf(LabReport report) throws IOException;
	

	

}
