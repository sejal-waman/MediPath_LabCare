package com.medipath.labcare.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lab_tests")
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @Column(name = "test_name", nullable = false)
    @NotEmpty(message = "Test name cannot be empty")
    @Size(max = 255, message = "Test name cannot exceed 255 characters")
    private String testName;

    @Column(name = "test_type", nullable = false)
    @NotEmpty(message = "Test type cannot be empty")
    @Size(max = 255, message = "Test type cannot exceed 255 characters")
    private String testType;

    @Column(name = "category", nullable = false)
    @NotEmpty(message = "Category cannot be empty")
    @Size(max = 255, message = "Category cannot exceed 255 characters")
    private String category;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status cannot be null")
    private String status = "Pending";  // Default value

    @Column(name = "cost", nullable = false)
    @NotNull(message = "Cost cannot be null")
    private Double cost;  // Ensure that 'cost' is always set

    @Column(name = "normal_range")
    @Size(max = 255, message = "Normal range cannot exceed 255 characters")
    private String normalRange;

    @Column(name = "unit")
    @Size(max = 255, message = "Unit cannot exceed 255 characters")
    private String unit;

    @Column(name = "description", length = 1000)
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    // TEMPORARY: Patient-specific data
    @Column(name = "patient_name")
    @Size(max = 255, message = "Patient name cannot exceed 255 characters")
    private String patientName;

    @Column(name = "result")
    @Size(max = 255, message = "Result cannot exceed 255 characters")
    private String result;

    @Column(name = "test_date")
    @NotNull(message = "Test date cannot be null")
    private LocalDate testDate;
    
    
    @Column(name = "bill_generated")
    private boolean billGenerated = false;

    // getters and setters
    public boolean isBillGenerated() {
        return billGenerated;
    }

    public void setBillGenerated(boolean billGenerated) {
        this.billGenerated = billGenerated;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }
}
