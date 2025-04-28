package com.medipath.labcare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.LabTest;

@Service

public interface LabTestService 
{
    List<LabTest> getAllTests();

	LabTest getTestById(Long id);

	LabTest saveTest(LabTest test);
	void saveLabTest(LabTest labTest);

	void deleteTest(Long id);

	void updateLabTest(LabTest labTest);

	List<LabTest> findAllLabResults();

	Optional<LabTest> findById(Long resultId);

	
}