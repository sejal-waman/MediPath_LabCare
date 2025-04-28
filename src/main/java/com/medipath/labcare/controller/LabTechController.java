package com.medipath.labcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medipath.labcare.entity.User;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/labtech")
public class LabTechController 
{

    @GetMapping("/dashboard")
    public String labDashboard(HttpSession session, Model model) 
    {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || !RoleChecker.hasRole(user, "LABTECH")) 
        {
            return "redirect:/login?access-denied";
        }

        model.addAttribute("user", user);
        return "labtech-dashboard"; // labtech-dashboard.jsp
    }
}
