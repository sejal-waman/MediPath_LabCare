package com.medipath.labcare.service;

import java.util.List;

import com.medipath.labcare.entity.LabSample;

public interface LabSampleService
{
	
	void addLabSample(LabSample labSample);

	LabSample getLabSampleById(Long sampleId);

	void deleteLabSample(Long sampleId);
	
	List<LabSample> getAllLabSamples();



	void updateLabSample(Long id, LabSample updatedSample);

}
