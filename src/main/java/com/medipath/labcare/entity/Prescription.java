package com.medipath.labcare.entity;



import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The doctor who wrote the prescription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    // The patient for whom the prescription is written
  
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate annotation
    private User patient;



    @Column(nullable = false)
    private String medication;

    @Column(nullable = false)
    private String dosage;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "prescribed_date", nullable = false)
    private LocalDate prescribedDate;

    public Prescription() {
        // JPA requires a no‑arg constructor
    }

    // === Getters & Setters ===

    public Long getId() {
        return id;
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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDate getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(LocalDate prescribedDate) {
        this.prescribedDate = prescribedDate;
    }

    // === equals & hashCode ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Prescription that = (Prescription) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // === toString for debugging ===

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", doctor=" + (doctor != null ? doctor.getUsername() : "null") +
                ", patient=" + (patient != null ? patient.getUsername() : "null") +
                ", medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", prescribedDate=" + prescribedDate +
                '}';
    }
}

