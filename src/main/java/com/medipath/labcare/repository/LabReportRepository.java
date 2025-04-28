package com.medipath.labcare.repository;

import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.Prescription;
import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface LabReportRepository extends JpaRepository<LabReport, Long> {

    List<LabReport> findByPatient(User patient);

  
    public List<LabReport> findByStatus(String status);


    @EntityGraph(attributePaths = {"patient", "labTest", "assignedDoctor"})
    List<LabReport> findByAssignedDoctor(User doctor);

  
    public List<LabReport> findByPatientAndReviewed(User patient, boolean reviewed);

	Optional<User> findDoctorById(Long doctorId);
	List<LabReport> findByPatientId(Long patientId);


	List<LabReport> findByPatientAndStatus(User patient, String string);

	
	
	
}
