package com.medipath.labcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.PatientEnquiry;
import com.medipath.labcare.repository.PatientEnquiryRepository;
import com.medipath.labcare.service.PatientEnquiryService;

@Service

public class PatientEnquiryServiceimpl implements PatientEnquiryService {

     @Autowired
    private PatientEnquiryRepository patientEnquiryRepository;  // Repository to fetch data from DB

    // Method to fetch all patient enquiries
     @Override
    public List<PatientEnquiry> getAllPatientEnquiries() {
        return patientEnquiryRepository.findAll();
    }

	@Override
	 public void saveEnquiry(PatientEnquiry enquiry) {
		patientEnquiryRepository.save(enquiry);
    }
}