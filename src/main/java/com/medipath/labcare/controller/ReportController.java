package com.medipath.labcare.controller;

import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.service.LabReportService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private LabReportService labReportService;

    // 🧾 Display list of all reports
    @GetMapping
    public String showReportManagementPage(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("loggedInUser");

        if (admin == null || !hasRole(admin, "ADMIN")) {
            return "redirect:/login"; // ❌ Redirect if not logged in or not admin
        }

        List<LabReport> reports = labReportService.getAllReports();
        model.addAttribute("reports", reports);
        model.addAttribute("adminName", admin.getUsername());
        return "admin/report-management";
    }

    // 📄 View individual report detail
    @GetMapping("/view/{id}")
    public String viewReport(@PathVariable("id") Long id, HttpSession session, Model model) {
        User admin = (User) session.getAttribute("loggedInUser");

        if (admin == null || !hasRole(admin, "ADMIN")) {
            return "redirect:/login";
        }

        LabReport report = labReportService.getReportById(id);
        model.addAttribute("report", report);
        model.addAttribute("adminName", admin.getUsername());
        return "admin/report-detail";
    }

    // 📋 View all reports (alternate view)
    @GetMapping("/view-reports")
    public String viewReports(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("loggedInUser");

        if (admin == null || !hasRole(admin, "ADMIN")) {
            return "redirect:/login";
        }

        List<LabReport> reports = labReportService.getAllReports();
        model.addAttribute("reports", reports);
        model.addAttribute("adminName", admin.getUsername());
        return "admin/view-reports";
    }

    // 🔐 Helper Method to Check Role
    private boolean hasRole(User user, String roleName) {
        if (user == null) return false;
        Set<Role> roles = user.getRoles();
        return roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }
}
