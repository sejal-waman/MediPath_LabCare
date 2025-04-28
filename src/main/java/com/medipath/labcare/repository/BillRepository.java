package com.medipath.labcare.repository;


import com.medipath.labcare.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> 
{
    // Optionally, define custom queries if needed
}
