package com.medipath.labcare.service;

import java.util.List;

import com.medipath.labcare.entity.PatientEnquiry;



public interface PatientEnquiryService  {

	List<PatientEnquiry> getAllPatientEnquiries();

	void saveEnquiry(PatientEnquiry enquiry);

}
