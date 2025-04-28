package com.medipath.labcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService; // Assuming you have an EmailService for sending emails

    // Notify the patient by email
    public void notifyPatient(Long patientId, String message) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient ID"));

        // Construct subject and pass the message separately
        String subject = "Appointment Status Update";
        emailService.sendHtmlEmail(patient.getEmail(), patient.getUsername(), subject, message);
    }
}
