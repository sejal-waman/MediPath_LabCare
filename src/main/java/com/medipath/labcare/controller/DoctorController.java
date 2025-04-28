package com.medipath.labcare.controller;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.service.AppointmentService;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        // Retrieve the logged-in user from the session
        User user = (User) session.getAttribute("loggedInUser");

        // If no user is logged in or the user does not have the "DOCTOR" role, redirect to login
        if (user == null || !RoleChecker.hasRole(user, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }

        // Fetch all appointments assigned to this doctor
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctor(user.getId());

        // Add attributes to the model
        model.addAttribute("user", user);
        model.addAttribute("specialization", user.getSpecialization());
        model.addAttribute("appointments", appointments);

        // Return the view name (doctor-dashboard.jsp)
        return "doctor-dashboard";
    }
}
