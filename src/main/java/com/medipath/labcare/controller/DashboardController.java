package com.medipath.labcare.controller;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
    public String redirectToDashboard(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        Set<Role> roles = user.getRoles();

        if (roles == null || roles.isEmpty()) {
            return "redirect:/access-denied"; // Redirect to access denied if no roles are found
        }

        Role firstRole = roles.iterator().next();
        if (firstRole == null || firstRole.getName() == null || firstRole.getName().trim().isEmpty()) {
            return "redirect:/access-denied"; // Redirect to access denied if role name is null or empty
        }

        String roleName = firstRole.getName().trim().toUpperCase();

        // Redirect to the corresponding dashboard based on the role
        switch (roleName) {
            case "ADMIN":
                return "redirect:/admin/dashboard";
            case "DOCTOR":
                return "redirect:/doctor/dashboard";
            case "LABTECH":
                return "redirect:/labtech/dashboard";
            case "RECEPTIONIST":
                return "redirect:/reception/dashboard";
            case "PATIENT":
                return "redirect:/patient/dashboard";
            default:
                return "redirect:/access-denied"; // Redirect to access denied if the role is unrecognized
        }
    }
}
