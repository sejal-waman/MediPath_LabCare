package com.medipath.labcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private UserRepository userRepository;
    // Patient's Dashboard Route
	// Show the Patient Dashboard (after assigning doctor)
	@GetMapping("/dashboard")
	public String showPatientDashboard(HttpSession session, Model model) {
	    User patient = (User) session.getAttribute("loggedInUser");

	    if (patient == null) {
	        return "redirect:/login";
	    }

	    // Check if the patient has an assigned doctor
	    User assignedDoctor = patient.getAssignedDoctor();
	    if (assignedDoctor != null) {
	        model.addAttribute("assignedDoctor", assignedDoctor); 
	    }

	    // Fetch list of doctors and pass to model
	    List<User> doctors = userRepository.findAllByRoles_Name("DOCTOR"); // 👈 this method must exist
	    model.addAttribute("doctors", doctors);

	    return "patient-dashboard";
	}


}
