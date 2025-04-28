package com.medipath.labcare.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/send-reset-link")
    public String processForgotPassword(@RequestParam("email") String email, 
                                        Model model, 
                                        HttpServletRequest request, 
                                        HttpSession session) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Store the user in session for reset
            session.setAttribute("resetUser", user);

            // Send reset link or simple email (optional step if just redirecting to reset page)
            String resetLink = request.getRequestURL().toString().replace("/send-reset-link", "") + "/reset-password";

            // Email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Reset Password - MediPath LabCare");
            message.setText("Click the link to reset your password: " + resetLink);

            mailSender.send(message);

            model.addAttribute("message", "Password reset link has been sent to your email.");
        } else {
            model.addAttribute("error", "No account found with this email.");
        }

        return "forgot-password"; // This is your forgot-password.jsp page
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("resetUser");
        if (user != null) {
            return "reset-password"; // Show reset password page
        } else {
            model.addAttribute("error", "Session expired or invalid access.");
            return "forgot-password";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("newPassword") String newPassword,
                                HttpSession session,
                                Model model) {
        User user = (User) session.getAttribute("resetUser");
        if (user != null) {
            user.setPassword(newPassword); // 🔐 Don't forget to hash the password
            userRepository.save(user);

            session.removeAttribute("resetUser");
            model.addAttribute("message", "Password successfully reset. Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Session expired or invalid access.");
            return "forgot-password";
        }
    }
}
