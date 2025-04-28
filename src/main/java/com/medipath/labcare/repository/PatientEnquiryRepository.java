package com.medipath.labcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medipath.labcare.entity.PatientEnquiry;

@Repository
public interface PatientEnquiryRepository extends JpaRepository<PatientEnquiry, Long> {
    // Custom query methods can be added here if needed
}
