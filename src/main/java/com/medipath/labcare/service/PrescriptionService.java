package com.medipath.labcare.service;

import com.medipath.labcare.entity.Prescription;

import java.util.List;

public interface PrescriptionService {

    List<Prescription> getPrescriptionsByDoctorId(Long doctorId);

    Prescription getPrescriptionById(Long id);

    List<Prescription> getAllPrescriptions();

    void deletePrescription(Long id);

    Prescription savePrescription(Prescription prescription);

	List<Prescription> findByDoctorId(Long id);
}
