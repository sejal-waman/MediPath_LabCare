package com.medipath.labcare.repository;

import com.medipath.labcare.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    // Fetch all prescriptions written by a particular doctor
    List<Prescription> findByDoctorId(Long doctorId);
}
