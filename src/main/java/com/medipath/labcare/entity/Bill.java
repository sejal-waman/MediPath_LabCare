package com.medipath.labcare.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Patient name cannot be null")
    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @NotNull(message = "Test name cannot be null")
    @Column(name = "test_name", nullable = false)
    private String testName;

    @NotNull(message = "Cost cannot be null")
    @Column(name = "cost", nullable = false)
    private Double cost;

    // Default constructor
    public Bill() {}

    // Constructor to initialize the Bill object
    public Bill(String patientName, String testName, Double cost) {
        this.patientName = patientName;
        this.testName = testName;
        this.cost = cost;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    // Override toString to display the bill's details
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", testName='" + testName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
