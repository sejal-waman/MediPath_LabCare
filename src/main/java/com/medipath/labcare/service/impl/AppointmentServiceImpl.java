package com.medipath.labcare.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.AppointmentRepository;
import com.medipath.labcare.service.AppointmentService;

import jakarta.transaction.Transactional;

@Service

public class AppointmentServiceImpl implements AppointmentService{
	
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	/*@Transactional
	public void approvePatientAppointment(User doctor, Long patientId) {
	    Appointment appointment = appointmentRepository.findByDoctorIdAndPatientId(doctor.getId(), patientId);
	    if (appointment != null) {
	        appointment.setAppointmentApproved(true);
	        appointmentRepository.save(appointment);
	    }
	}*/

	@Override
    public Appointment findByDoctorAndPatient(Long doctorId, Long patientId) {
        return appointmentRepository.findByDoctorIdAndPatientId(doctorId, patientId);
    }

   
    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }


	@Override
	public Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }


	@Override
	public List<Appointment> findAppointmentsByPatientId(Long id) {
	    // Return a list of appointments for the patient with the given ID
	    return appointmentRepository.findByPatientId(id);
	}
	

	@Override
	public List<Appointment> getAppointments() {
	    List<Appointment> appointments = appointmentRepository.findAll(); // fetch appointments logic
	    
	    // No need to convert the LocalDateTime to Date here, as `getAppointmentTime()` handles it
	    return appointments;
	}


	@Override
	public
    // Convert Date to LocalDateTime if you want to set it back
 LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
	
	
	 @Override
	 public void save(Appointment appointment) {
	     if (appointment == null) {
	         throw new IllegalArgumentException("Appointment cannot be null");
	     }

	     if (appointment.getId() == null) {
	         // New appointment, directly save
	         appointmentRepository.save(appointment);
	     } else {
	         // Update existing appointment
	         Appointment existingAppointment = appointmentRepository.findById(appointment.getId())
	             .orElseThrow(() -> new IllegalArgumentException("Appointment with ID " + appointment.getId() + " does not exist"));

	         existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
	         existingAppointment.setReason(appointment.getReason());
	         existingAppointment.setDoctor(appointment.getDoctor());
	         existingAppointment.setPatient(appointment.getPatient());
	         
	         // Important: Update doctor approvals if needed
	         if (appointment.getDoctorApprovals() != null) {
	             existingAppointment.setDoctorApprovals(appointment.getDoctorApprovals());
	         }

	         appointmentRepository.save(existingAppointment);
	     }
	 }



}
