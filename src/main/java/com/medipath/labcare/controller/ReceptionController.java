package com.medipath.labcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medipath.labcare.entity.User;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reception")
public class ReceptionController
{



	    @GetMapping("/dashboard")
	    public String receptionDashboard(HttpSession session, Model model) {
	        User user = (User) session.getAttribute("loggedInUser");

	        if (user == null || !RoleChecker.hasRole(user, "RECEPTIONIST")) {
	            return "redirect:/login?access-denied";
	        }

	        model.addAttribute("user", user);
	        return "reception-dashboard";  // Ensure this matches the JSP filename
	    }
	


}
