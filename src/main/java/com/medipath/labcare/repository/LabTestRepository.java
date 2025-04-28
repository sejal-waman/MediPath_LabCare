package com.medipath.labcare.repository;

import com.medipath.labcare.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    // ✅ Find all tests by category
    List<LabTest> findByCategory(String category);

    // ✅ Search by test name (partial match)
    List<LabTest> findByTestNameContainingIgnoreCase(String testName);

    // ✅ Custom query: Fetch only active tests (if you have status/active flag)
    @Query("SELECT t FROM LabTest t WHERE t.status = 'ACTIVE'")
    List<LabTest> findAllActiveTests();

    // ✅ Optional: Fetch distinct categories for UI filters
    @Query("SELECT DISTINCT t.category FROM LabTest t")
    List<String> findDistinctCategories();
    
    List<LabTest> findByStatus(String status);  
    
}
