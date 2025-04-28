package com.medipath.labcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.User;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Fetch all appointments for a specific patient
    List<Appointment> findByPatient(User patient);

    // Find an appointment by doctorId and patientId (for approval)
    Appointment findByDoctorIdAndPatientId(Long doctorId, Long patientId);

	List<Appointment> findByDoctorId(Long doctorId);
	
	@Query("SELECT a FROM Appointment a JOIN FETCH a.patient WHERE a.doctor.id = :doctorId")
	List<Appointment> findAppointmentsByDoctor(@Param("doctorId") Long doctorId);

	List<Appointment> findByPatientId(Long id);

}
