package com.medipath.labcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.Bill;
import com.medipath.labcare.repository.BillRepository;
import com.medipath.labcare.service.BillService;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    // Implement the save method
    @Override
    public void save(Bill bill) {
        if (bill.getPatientName() == null || bill.getPatientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be null or empty!");
        }
        billRepository.save(bill);
    }

	

    // Optionally, you can add more methods if needed (e.g., find by ID, delete, etc.)
}
