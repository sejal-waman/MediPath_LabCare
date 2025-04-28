package com.medipath.labcare.service.impl;

import com.medipath.labcare.entity.LabTest;
import com.medipath.labcare.repository.LabTestRepository;
import com.medipath.labcare.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTestServiceImpl implements LabTestService {

    @Autowired
    private LabTestRepository labTestRepository;

    // ─────────────────── CRUD OPERATIONS ─────────────────── //

    @Override
    public List<LabTest> getAllTests() {
        return labTestRepository.findAll();
    }

    @Override
    public LabTest getTestById(Long id) {
        return labTestRepository.findById(id).orElse(null);
    }

    /**
     * Persist a new LabTest or update an existing one and
     * return the saved entity (with generated ID, etc.)
     */
    @Override
    public LabTest saveTest(LabTest test) {
        return labTestRepository.save(test);
    }

    @Override
    public void deleteTest(Long id) {
        labTestRepository.deleteById(id);
    }

    /**
     * Update *all* mutable fields of an existing LabTest.
     */
    @Override
    public void updateLabTest(LabTest labTest) {

        LabTest existingTest = labTestRepository.findById(labTest.getId())
                .orElseThrow(() ->
                        new RuntimeException("Lab Test not found with ID: " + labTest.getId()));

        existingTest.setTestName(labTest.getTestName());
        existingTest.setCategory(labTest.getCategory());
        existingTest.setDescription(labTest.getDescription());
        existingTest.setCost(labTest.getCost());
        existingTest.setStatus(labTest.getStatus());
        existingTest.setTestType(labTest.getTestType());
        existingTest.setNormalRange(labTest.getNormalRange());
        existingTest.setUnit(labTest.getUnit());
        existingTest.setPatientName(labTest.getPatientName());
        existingTest.setResult(labTest.getResult());
        existingTest.setTestDate(labTest.getTestDate());

        labTestRepository.save(existingTest);
    }

    /**
     * Convenience wrapper kept for backwards compatibility.
     * Spring Data’s save() already handles both insert & update.
     */
    @Override
    public void saveLabTest(LabTest labTest) {
        labTestRepository.save(labTest);
    }

    /**
     * Retrieve all lab results.
     * This is a wrapper to fetch all lab test results.
     */
    @Override
    public List<LabTest> findAllLabResults() {
        return labTestRepository.findAll();
    }

    /**
     * Fetch a lab test result by its ID.
     * Returns an Optional containing the LabTest if found, or empty if not.
     */
    @Override
    public Optional<LabTest> findById(Long resultId) {
        return labTestRepository.findById(resultId);
    }
}
