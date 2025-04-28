package com.medipath.labcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;



@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    
    

    // 🎯 Handle user registration
    @PostMapping("/processRegister")
    public String processRegister(@ModelAttribute("user") User user, 
                                   @RequestParam("roles") List<Long> roleIds, 
                                   Model model) {
        try {
            // Check if roles are selected
            if (roleIds == null || roleIds.isEmpty()) {
                model.addAttribute("error", "❌ Please select at least one role.");
                return "register"; // Return to register if no role is selected
            }

            // Map roleIds to actual Role objects
            Set<Role> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                Role role = userService.findByRId(roleId); // Fetch Role by ID using the service
                if (role != null) {
                    roles.add(role);  // Add role to set if found
                } else {
                    model.addAttribute("error", "❌ Invalid role selected.");
                    return "register"; // Return to register if a role is invalid
                }
            }

            // Set roles to the user
            user.setRoles(roles);

            // Check if email or mobile already exists
            if (userService.existsByEmail(user.getEmail())) {
                model.addAttribute("error", "❌ Email is already registered.");
                return "register"; // Return to register if email already exists
            }
            if (userService.existsByMobileNo(user.getMobileNo())) {
                model.addAttribute("error", "❌ Mobile number is already registered.");
                return "register"; // Return to register if mobile number already exists
            }

            // Save the user (Assuming password is hashed beforehand if needed)
            userService.saveUser(user);

            // Add success message
            model.addAttribute("success", "🎉 Registration successful! Please login to continue.");
            return "login";  // Redirect to login page after successful registration
        } catch (Exception e) {
            // Log the error for internal debugging (ensure proper logging is configured)
            e.printStackTrace(); // This will print to the console, or use a logger.
            model.addAttribute("error", "❌ Something went wrong. Please try again later.");
            return "register";  // Stay on the register page with a generic error
        }

    }


    // 🔐 Handle user login

    @PostMapping("/processLogin")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Email not found.");
            return "login";  // Return to the login page if email is not found.
        }

        User user = optionalUser.get();

        // Direct plain text password comparison (without hashing)
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid password.");
            return "login";  // Return to the login page if password is incorrect.
        }

        // Set the user in the session
        session.setAttribute("loggedInUser", user);

        return "redirect:/dashboard";  // Redirect to the dashboard once logged in.
    }


}
