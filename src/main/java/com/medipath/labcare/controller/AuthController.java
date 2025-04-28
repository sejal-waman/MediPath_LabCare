package com.medipath.labcare.controller;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.RoleRepository;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Show login page (handled by Spring Security)
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());  // Create and add an empty User object to the model
        return "login";  // Make sure this matches your JSP name
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        List<Role> allRoles = roleRepository.findAll();
        
        // Simplified error handling for now
        if (allRoles == null || allRoles.isEmpty()) {
            model.addAttribute("error", "No roles found in the system. Please contact the admin.");
            return "register"; // Stay on the register page itself with an error message
        }

        model.addAttribute("user", new User());
        model.addAttribute("roles", allRoles);
        return "register";
    }




    // Logout the user
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/login"; // Redirect to login page after logout
    }
    
    
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("error", "🚫 Access Denied. You do not have permission to access this page.");
        return "access-denied";  // You must create access-denied.jsp or access-denied.html
    }

}
