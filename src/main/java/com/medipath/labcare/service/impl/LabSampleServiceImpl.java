package com.medipath.labcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medipath.labcare.entity.LabSample;
import com.medipath.labcare.repository.LabSampleRepository;
import com.medipath.labcare.service.LabSampleService;

import java.util.List;
import java.util.Optional;

@Service
public class LabSampleServiceImpl implements LabSampleService {
    
    @Autowired
    private LabSampleRepository labSampleRepository;

    // Method to add a new lab sample
    @Override
    public void addLabSample(LabSample labSample) {
        labSampleRepository.save(labSample);  // Save the lab sample to the database
    }

    // Method to get a lab sample by its ID
    @Override
    public LabSample getLabSampleById(Long sampleId) {
        Optional<LabSample> labSample = labSampleRepository.findById(sampleId);
        return labSample.orElse(null);  // Return the lab sample if found, else return null
    }

    // Method to delete a lab sample by its ID
    @Override
    public void deleteLabSample(Long sampleId) {
        Optional<LabSample> labSample = labSampleRepository.findById(sampleId);
        if (labSample.isPresent()) {
            labSampleRepository.delete(labSample.get());  // Delete the sample if it exists
        } else {
            // Optionally, throw an exception or handle the case where the sample doesn't exist
            throw new IllegalArgumentException("Lab sample with ID " + sampleId + " does not exist.");
        }
    }
    
    // Method to get all lab samples
    public List<LabSample> getAllLabSamples() {
        return labSampleRepository.findAll();
    }

    // Method to update an existing lab sample (using ID and updated sample data)
    @Override
    public void updateLabSample(Long id, LabSample updatedSample) {
        // Retrieve the existing sample by ID
        LabSample existingSample = labSampleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lab sample with ID " + id + " does not exist."));
        
        // Update the properties of the existing sample
        existingSample.setPatientName(updatedSample.getPatientName());
        existingSample.setTestType(updatedSample.getTestType());
        existingSample.setStatus(updatedSample.getStatus());
        existingSample.setNormalRange(updatedSample.getNormalRange());
        existingSample.setUnit(updatedSample.getUnit());
        existingSample.setDescription(updatedSample.getDescription());

        // Save the updated sample back to the database
        labSampleRepository.save(existingSample);
    }

    // Remove redundant method, as it's already handled by the method above.
    // If you intended to have a method that directly updates the entity without using ID, then you can refactor the logic appropriately.
}
