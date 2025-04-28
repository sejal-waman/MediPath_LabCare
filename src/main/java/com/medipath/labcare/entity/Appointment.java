package com.medipath.labcare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    // Primary assigned doctor (can still keep if needed)
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate annotation
    private User patient;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    /*@Column(name = "appointment_approved", nullable = false)
    private boolean appointmentApproved = false;*/

    @Column(name = "reason", length = 500)
    private String reason;

    // New: dynamic doctor approvals
    @ElementCollection
    @CollectionTable(name = "doctor_approvals", joinColumns = @JoinColumn(name = "appointment_id"))
    @MapKeyColumn(name = "doctor_id")
    @Column(name = "approved")
    private Map<Long, Boolean> doctorApprovals = new HashMap<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /*public boolean isAppointmentApproved() {
        return appointmentApproved;
    }

    public void setAppointmentApproved(boolean appointmentApproved) {
        this.appointmentApproved = appointmentApproved;
    }*/

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<Long, Boolean> getDoctorApprovals() {
        return doctorApprovals;
    }

    public void setDoctorApprovals(Map<Long, Boolean> doctorApprovals) {
        this.doctorApprovals = doctorApprovals;
    }

   
    public Date getAppointmentTime() {
        if (this.appointmentDate != null) {
            return Date.from(this.appointmentDate.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public void setAppointmentTime(Date date) {
        if (date != null) {
            this.appointmentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else {
            this.appointmentDate = null;
        }
    }

    
    
    
    public boolean isAppointmentApproved() {
        if (doctorApprovals == null) return false;
        return doctorApprovals.containsValue(true);
    }

	public void setAppointmentApproved(boolean appointmentApproved) {
		// TODO Auto-generated method stub
		
	}

	


	
}
