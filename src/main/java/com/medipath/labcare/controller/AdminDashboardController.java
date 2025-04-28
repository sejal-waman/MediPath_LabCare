package com.medipath.labcare.controller;

import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.LabTest;
import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.LabReportRepository;
import com.medipath.labcare.repository.LabTestRepository;
import com.medipath.labcare.repository.RoleRepository;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.service.LabReportService;
import com.medipath.labcare.service.impl.SettingsService;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Transactional
@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private LabReportService labReportService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private  LabReportRepository  labReportRepository;
   
    // ********************** 1. Manage Users *****************************
    
    @GetMapping("/manage-users")
    public String manageUsersPage(HttpSession session, Model model) {
        User admin = (User) session.getAttribute("loggedInUser");

        if (admin == null || !isAdmin(admin)) {
            return "redirect:/login";
        }

        model.addAttribute("adminName", admin.getUsername());
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/manage-users";
    }

    // 🖋️ Edit User Form
    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model, HttpSession session) {
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null || !isAdmin(admin)) return "redirect:/login";

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "admin/edit-user";
        } else {
            session.setAttribute("message", "⚠️ User not found.");
            return "redirect:/admin/manage-users";
        }
    }

    // 💾 Update User - Save Changes
    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") User updatedUser,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                             HttpSession session) {

        Optional<User> existingUserOpt = userRepository.findById(updatedUser.getId());
        if (existingUserOpt.isPresent()) {
            User existing = existingUserOpt.get();
            existing.setUsername(updatedUser.getUsername());
            existing.setEmail(updatedUser.getEmail());
            existing.setMobileNo(updatedUser.getMobileNo());
            existing.setEnabled(updatedUser.isEnabled());

            if (roleIds != null) {
                existing.setRoles(new HashSet<>(roleRepository.findAllById(roleIds)));
            }

            userRepository.save(existing);
            session.setAttribute("message", "✅ User updated successfully.");
        } else {
            session.setAttribute("message", "⚠️ User not found.");
        }

        return "redirect:/admin/manage-users";
    }

    // ❌ Delete User
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null || !isAdmin(admin)) {
            session.setAttribute("message", "❌ Unauthorized access. Please log in.");
            return "redirect:/login";
        }

        try {
            // First, delete the related LabReport records (if any)
            List<LabReport> labReports = labReportRepository.findByPatientId(id);
            if (labReports != null && !labReports.isEmpty()) {
                // Option 1: Delete related lab reports
                labReportRepository.deleteAll(labReports);

                // Option 2 (alternative): Set the patient_id to null for all related records
                // labReports.forEach(report -> {
                //     report.setPatient(null);
                //     labReportRepository.save(report);
                // });
            }

            // Now, delete the user
            userRepository.deleteById(id);
            session.setAttribute("message", "🗑️ User deleted successfully.");

        } catch (Exception e) {
            // Log error if deletion fails
            session.setAttribute("message", "❌ Error occurred while deleting the user. Please try again.");
            e.printStackTrace(); // Or use a logger
            return "redirect:/admin/manage-users";  // Optionally redirect to the same page
        }

        return "redirect:/admin/manage-users";
    }

    private boolean isAdmin(User admin) {
        return admin != null && RoleChecker.hasRole(admin, "ADMIN");
    }


   


    // ********************** 2. Manage Tests *****************************
    
    // 🧪 View All Lab Tests
    @GetMapping("/manage-tests")
    public String viewAllTests(Model model, HttpSession session) {
        model.addAttribute("labTests", labTestRepository.findAll());
        model.addAttribute("adminName", ((User) session.getAttribute("loggedInUser")).getUsername());
        return "admin/manage-tests";
    }

    // 📝 Show Edit Form
    @GetMapping("/edit-test/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        Optional<LabTest> optionalTest = labTestRepository.findById(id);
        if (optionalTest.isPresent()) {
            model.addAttribute("labTest", optionalTest.get());
            return "admin/edit-test";
        } else {
            session.setAttribute("message", "⚠️ Lab test not found.");
            return "redirect:/admin/manage-tests";
        }
    }

    // 💾 Update Lab Test
    @PostMapping("/update-test")
    public String updateTest(@ModelAttribute("labTest") LabTest updatedTest, HttpSession session) {
        Optional<LabTest> existingTestOpt = labTestRepository.findById(updatedTest.getId());
        if (existingTestOpt.isPresent()) {
            LabTest test = existingTestOpt.get();
            test.setTestName(updatedTest.getTestName());
            test.setCategory(updatedTest.getCategory());
            test.setNormalRange(updatedTest.getNormalRange());
            test.setUnit(updatedTest.getUnit());
            test.setCost(updatedTest.getCost());

            labTestRepository.save(test);
            session.setAttribute("message", "✅ Lab test updated successfully.");
        } else {
            session.setAttribute("message", "⚠️ Lab test not found.");
        }

        return "redirect:/admin/manage-tests";
    }

    // ❌ Delete Lab Test
    @GetMapping("/delete-test/{id}")
    public String deleteTest(@PathVariable Long id, HttpSession session) {
        try {
            labTestRepository.deleteById(id);
            session.setAttribute("message", "🗑️ Lab test deleted successfully.");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            session.setAttribute("message", "⚠️ Cannot delete test due to dependent lab reports.");
        } catch (Exception e) {
            session.setAttribute("message", "⚠️ Error deleting lab test.");
        }
        return "redirect:/admin/manage-tests";
    }


    // ********************** 3. View Reports *****************************
    
 // View all lab reports for Admin
    @GetMapping("/view-reports")
    public String viewReportsForAdmin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null || !RoleChecker.hasRole(user, "ADMIN")) {
            return "redirect:/login?access-denied";
        }

        List<LabReport> reports = labReportService.getAllReports();
        model.addAttribute("reports", reports);
        return "admin/view-reports";
    }

   

    @GetMapping("/download-report/{id}")
    public void downloadReport(@PathVariable Long id,
                               HttpServletResponse response) throws IOException {
      LabReport report = labReportRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Report not found"));

      // build the absolute path:
      File file = new File(System.getProperty("user.dir")
                           + "/uploads/"
                           + report.getPdfPath());
      if (!file.exists()) {
        throw new FileNotFoundException("File not found");
      }

      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition",
                         "attachment; filename=\"" + file.getName() + "\"");
      Files.copy(file.toPath(), response.getOutputStream());
      response.getOutputStream().flush();
    }


    // ********************** 4. System Settings ***************************
    
    @GetMapping("/settings")
    public String showSettings(Model model, HttpSession session) {
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null) {
            return "redirect:/login";
        }

        com.medipath.labcare.model.SystemSettings settings = settingsService.load();
        model.addAttribute("settings", settings);
        return "admin/settings";
    }

    // 📝 Handle settings form submission
    @PostMapping("/settings")
    public String saveSettings(@ModelAttribute("settings") com.medipath.labcare.model.SystemSettings settings, HttpSession session) {
        User admin = (User) session.getAttribute("loggedInUser");
        if (admin == null) {
            return "redirect:/login";
        }

        settingsService.save(settings);
        session.setAttribute("message", "⚙️ Settings saved successfully!");
        return "redirect:/admin/settings";
    }
}
