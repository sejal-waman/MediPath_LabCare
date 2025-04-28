package com.medipath.labcare.controller;

import com.medipath.labcare.entity.*;
import com.medipath.labcare.repository.LabReportRepository;
import com.medipath.labcare.service.*;
import com.medipath.labcare.service.impl.NotificationService;
import com.medipath.labcare.utility.RoleChecker;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private LabReportService labReportService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LabReportRepository labReportRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // 1️⃣ View Assigned Patients
    @GetMapping("/assigned-patients")
    public String viewAssignedPatients(HttpSession session, Model model) {
        User doctor = (User) session.getAttribute("loggedInUser");

        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }

        // Fetch patients assigned to the doctor from the same User table
        List<User> assignedPatients = userService.findAssignedPatientsByDoctor(doctor.getId());
        model.addAttribute("assignedPatients", assignedPatients);

        // Optionally, fetch appointments if necessary
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctor(doctor.getId());
        model.addAttribute("appointments", appointments);

        return "doctor/assigned-patients";  // Return the assigned patients page
    }


    // 2️⃣ Add Diagnosis
    @GetMapping("/add-diagnosis")
    public String showAddDiagnosisPage(HttpSession session, Model model) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        model.addAttribute("diagnosis", new Diagnosis());
        model.addAttribute("patients", userService.findAllByRole("PATIENT"));
        return "doctor/add-diagnosis";
    }

    // 3️⃣ Review Lab Reports
    @GetMapping("/reports")
    public String viewReportsForDoctor(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !RoleChecker.hasRole(user, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        List<LabReport> reports = labReportService.getAllReports();
        model.addAttribute("reports", reports);
        return "doctor/reports";
    }
    

    // 4️⃣ Download Report
    @GetMapping("/download-report/{id}")
    public ResponseEntity<Resource> downloadReport(@PathVariable Long id) {
        LabReport report = labReportService.getReportById(id);
        if (report == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lab Report not found");
        }
        Path path = Paths.get(uploadDir, report.getPdfPath());
        if (!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PDF not found at: " + path);
        }
        try {
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid file path", e);
        }
    }

    // 5️⃣ Email Report
    @PostMapping("/send-report")
    public String sendReportToEmail(@RequestParam("reportId") Long reportId,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        LabReport report = labReportService.getReportById(reportId);
        if (report != null && report.getPdfPath() != null) {
            try {
                labReportService.sendLabReport(report);
                redirectAttributes.addFlashAttribute("success", "Report emailed successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error sending email.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Report not found or missing file.");
        }
        return "redirect:/doctor/reports";
    }

    // 6️⃣ Manage Prescriptions
    @GetMapping("/prescriptions")
    public String managePrescriptions(HttpSession session, Model model) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        model.addAttribute("patients", userService.findAllByRole("PATIENT"));
        model.addAttribute("prescriptions", prescriptionService.findByDoctorId(doctor.getId()));
        return "doctor/prescriptions";
    }

    @PostMapping("/prescriptions")
    public String submitPrescription(@RequestParam("patientId") Long patientId,
                                     @RequestParam("medication") String medication,
                                     @RequestParam("dosage") String dosage,
                                     @RequestParam("instructions") String instructions,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        try {
            Prescription prescription = new Prescription();
            prescription.setDoctor(doctor);
            prescription.setPatient(userService.findById(patientId));
            prescription.setMedication(medication);
            prescription.setDosage(dosage);
            prescription.setInstructions(instructions);
            prescription.setPrescribedDate(LocalDate.now());
            prescriptionService.savePrescription(prescription);
            redirectAttributes.addFlashAttribute("success", "Prescription added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding prescription.");
        }
        return "redirect:/doctor/prescriptions";
    }

    // 7️⃣ Update/Delete Specialization
    @GetMapping("/update-specialization")
    public String showUpdateSpecializationPage(HttpSession session, Model model) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        model.addAttribute("doctor", doctor);
        return "doctor/update-specialization";
    }

    @PostMapping("/update-specialization")
    public String updateSpecialization(@RequestParam("specialization") String specialization,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        doctor.setSpecialization(specialization);
        userService.saveUser(doctor);
        redirectAttributes.addFlashAttribute("success", "Specialization updated successfully!");
        return "redirect:/doctor/dashboard";
    }

    @GetMapping("/delete-specialization")
    public String deleteSpecialization(HttpSession session, RedirectAttributes redirectAttributes) {
        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null || !RoleChecker.hasRole(doctor, "DOCTOR")) {
            return "redirect:/login?access-denied";
        }
        doctor.setSpecialization(null);
        userService.saveUser(doctor);
        redirectAttributes.addFlashAttribute("success", "Specialization deleted successfully!");
        return "redirect:/doctor/dashboard";
    }
    
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/assigned-appointments")
    public String viewAssignedAppointments(HttpSession session, Model model) {
        User doctor = (User) session.getAttribute("loggedInUser");

        if (doctor == null || !doctor.getRole().equals("DOCTOR")) {
            return "redirect:/login?access-denied";
        }

        // Fetch appointments assigned to this doctor
        List<Appointment> appointments = appointmentService.findAppointmentsByDoctor(doctor.getId());

        // Add appointments to the model for rendering
        model.addAttribute("appointments", appointments);
        return "doctor/appointments";  // JSP page for viewing appointments
    }

    /**
     * Approve appointment for a patient.
     */
    @PostMapping("/approve-appointment")
    public String approveAppointment(@RequestParam Long appointmentId, HttpSession session) {
        User doctor = (User) session.getAttribute("loggedInUser");

        if (doctor == null) {
            return "redirect:/login";
        }

        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        if (appointment != null) {
            if (appointment.getDoctorApprovals() == null) {
                appointment.setDoctorApprovals(new HashMap<>());
            }

            // ✅ Mark this doctor's approval
            appointment.getDoctorApprovals().put(doctor.getId(), true);

            appointmentService.saveAppointment(appointment);
        }

        return "redirect:/doctor/dashboard";
    }


    // 9️⃣ Get Single Lab Report
    @GetMapping("/labReport/{id}")
    public ResponseEntity<LabReport> getLabReport(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return labReportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔥 UPDATED: Mark Report Reviewed (Fixed to assign Doctor)
    @PostMapping("/mark-reviewed/{reportId}")
    public ResponseEntity<Map<String, Object>> markReportReviewed(@PathVariable("reportId") Long reportId,
                                                                  HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (reportId == null) {
            response.put("success", false);
            response.put("message", "Report ID must not be null.");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<LabReport> optionalLabReport = labReportRepository.findById(reportId);
        if (!optionalLabReport.isPresent()) {
            response.put("success", false);
            response.put("message", "Lab report not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        LabReport labReport = optionalLabReport.get();

        if (labReport.getPatient() == null) {
            response.put("success", false);
            response.put("message", "Patient associated with this report not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User doctor = (User) session.getAttribute("loggedInUser");

        if (doctor == null) {
            response.put("success", false);
            response.put("message", "Doctor must be logged in.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Update the report details
        labReport.setAssignedDoctor(doctor); // Set who reviewed
        labReport.setReviewed(true);          // Mark as reviewed
        labReport.setStatus("COMPLETED");      // Correct case: COMPLETED, not Completed
        labReportRepository.save(labReport);   // Save changes

        // Success response
        response.put("success", true);
        response.put("message", "Lab report marked as reviewed successfully.");
        return ResponseEntity.ok(response);
    }

    // 🔥 Doctor Reviews a Report
    @PostMapping("/review-report/{id}")
    public String reviewReport(@PathVariable Long id,
                               @RequestParam("result") String result,
                               HttpSession session) {
        LabReport report = labReportService.getReportById(id);

        if (report == null) {
            return "redirect:/error";
        }

        User doctor = (User) session.getAttribute("loggedInUser");

        report.setAssignedDoctor(doctor);
        report.setResult(result);
        report.setStatus("Completed");
        report.setReviewed(true);

        labReportService.save(report);

        return "redirect:/doctor/reports";
    }
}
