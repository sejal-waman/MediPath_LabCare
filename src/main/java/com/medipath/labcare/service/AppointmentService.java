package com.medipath.labcare.service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.User;

public interface AppointmentService {

	//void approvePatientAppointment(User doctor, Long patientId);

	Appointment findByDoctorAndPatient(Long id, Long patientId);

	void saveAppointment(Appointment appointment);

	List<Appointment> findAppointmentsByDoctor(Long id);

	Appointment findAppointmentById(Long appointmentId);

	List<Appointment> findAppointmentsByPatientId(Long id);

	List<Appointment> getAppointments();

	void save(Appointment appointment);
	
	LocalDateTime convertToLocalDateTime(Date date);
	


}
