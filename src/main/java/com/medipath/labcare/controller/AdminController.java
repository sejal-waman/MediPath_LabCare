package com.medipath.labcare.controller;

import com.medipath.labcare.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	/*@GetMapping("/dashboard")
	public String adminDashboard(HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loggedInUser");  // Get the user from session
	    
	    if (user == null) {  // If user is not logged in, redirect to login page
	        return "redirect:/login";
	    }
	    
	    // You can check if the user has the 'Admin' role and show the appropriate dashboard
	    if (user.getRole().equals("ADMIN")) {
	        model.addAttribute("username", user.getUsername());  // Pass the logged-in user's info to the view
	        return "admin-dashboard";  // Admin dashboard view
	    }
	    
	    model.addAttribute("error", "Access Denied!");  // Show error for non-admin users
	    return "error";  // Redirect to an error page if access is denied
	}*/

	@GetMapping("/dashboard")
	public String adminDashboard(HttpSession session, Model model) {
	    User user = (User) session.getAttribute("loggedInUser");  // Get the user from session
	    
	    if (user == null) {  // If user is not logged in, redirect to login page
	        return "redirect:/login";
	    }
	    
	   
	    boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

		if (isAdmin) {
			model.addAttribute("username", user.getUsername());
			return "admin-dashboard";
		}
	    
	    model.addAttribute("error", "Access Denied!");  // Show error for non-admin users
	    return "error";  // Redirect to an error page if access is denied
	}


}
