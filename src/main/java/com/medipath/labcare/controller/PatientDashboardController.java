package com.medipath.labcare.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.LabReport;
import com.medipath.labcare.entity.PatientEnquiry;
import com.medipath.labcare.entity.Profile;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.LabReportRepository;
import com.medipath.labcare.repository.ProfileRepository;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.service.AppointmentService;
import com.medipath.labcare.service.LabReportService;
import com.medipath.labcare.service.PatientEnquiryService;
import com.medipath.labcare.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/patient")
public class PatientDashboardController {

    private final LabReportService labReportService;
    private final UserRepository userRepository;
    private final LabReportRepository labReportRepository;
    private final UserService userService;
    private final AppointmentService appointmentService;

    public PatientDashboardController(
        LabReportService labReportService,
        UserRepository userRepository,
        LabReportRepository labReportRepository,
        UserService userService,
        AppointmentService appointmentService
    ) {
        this.labReportService = labReportService;
        this.userRepository = userRepository;
        this.labReportRepository = labReportRepository;
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    // Utility method to serve file download
    private ResponseEntity<UrlResource> serveFile(String filename, boolean attachment) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), "uploads").resolve(filename);
        File file = filePath.toFile();
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        UrlResource resource = new UrlResource(filePath.toUri());
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        String contentDisposition = (attachment ? "attachment" : "inline") + "; filename=\"" + filename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    // Download by File Name
    @GetMapping("/report/download/{fileName:.+}")
    public ResponseEntity<UrlResource> downloadPatientReport(@PathVariable String fileName) {
        try {
            return serveFile(fileName, true);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Download by Report ID
    @GetMapping("/download-report/{reportId}")
    public ResponseEntity<UrlResource> downloadPatientReportById(@PathVariable Long reportId) {
        LabReport report = labReportService.getReportById(reportId);

        if (report == null || report.getPdfPath() == null || report.getPdfPath().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            return serveFile(report.getPdfPath(), false);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // View Patient's Reviewed Test Reports
    @GetMapping("/my-tests")
    public String viewReviewedTestReports(HttpSession session, Model model) {
        User patient = (User) session.getAttribute("loggedInUser");
        if (patient == null) {
            return "redirect:/login";
        }

        List<LabReport> reports = labReportService.getReviewedReportsByPatient(patient);

        // No need to reset assignedDoctor here
        for (LabReport report : reports) {
            if (report.getReportDate() != null) {
                report.setReportDate(java.sql.Date.valueOf(report.getReportDate()));
            }
        }

        model.addAttribute("reportList", reports);
        return "patient/my-tests"; // Your JSP page
    }


    // Assign Doctor to Patient (create Appointment)
    @PostMapping("/assign-doctor")
    public String assignDoctor(@RequestParam Long doctorId, HttpSession session) {
        User patient = (User) session.getAttribute("loggedInUser");

        if (patient == null) {
            return "redirect:/login";
        }

        // Fetch the doctor
        User doctor = userService.findById(doctorId);

        // Create a new appointment object
        Appointment newAppointment = new Appointment();
        newAppointment.setPatient(patient);
        newAppointment.setDoctor(doctor);
        newAppointment.setAppointmentDate(LocalDateTime.now());
        newAppointment.setAppointmentApproved(false);  // Pending approval

        // Initialize doctor approvals map
        Map<Long, Boolean> approvals = new HashMap<>();
        approvals.put(doctor.getId(), false); // Not approved yet
        newAppointment.setDoctorApprovals(approvals);

        // Save appointment
        appointmentService.saveAppointment(newAppointment);

        // Update the patient with the assigned doctor (optional, depends on your logic)
        patient.setAssignedDoctor(doctor);
        userService.updateUser(patient);

        return "redirect:/patient/dashboard";  // Redirect back to patient's dashboard
    }


    // Update Patient's Personal Details
    @PostMapping("/update-details")
    public String updatePatientDetails(@RequestParam Integer age, @RequestParam String gender, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        loggedInUser.setAge(age);
        loggedInUser.setGender(gender);

        userService.updateUser(loggedInUser);
        session.setAttribute("loggedInUser", loggedInUser);

        return "redirect:/patient/dashboard";
    }

    // Review Report by Doctor
    @PostMapping("/review-report")
    public String reviewReport(@RequestParam("reportId") Long reportId,
                                @RequestParam("doctorRemarks") String doctorRemarks,
                                HttpSession session) {

        User doctor = (User) session.getAttribute("loggedInUser");
        if (doctor == null) {
            return "redirect:/login";
        }

        LabReport report = labReportService.getReportById(reportId);
        if (report != null) {
            report.setRemarks(doctorRemarks);
            report.setAssignedDoctor(doctor);
            report.setStatus("Completed");
            report.setReportDate(LocalDate.now());

            labReportService.saveReport(report);
        }

        return "redirect:/doctor/my-tests";
    }

    // View Patient's Appointments
  /*  @GetMapping("/appointments")
    public String viewAppointments(HttpSession session, Model model) {
        User patient = (User) session.getAttribute("loggedInUser");

        if (patient == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        // Fetch appointments for this patient
        List<Appointment> appointments = appointmentService.findAppointmentsByPatientId(patient.getId());

        // Convert LocalDateTime to Date for each appointment if necessary
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentTime() != null) {
                // If you already have Date, no need to convert back and forth
                Date date = appointment.getAppointmentTime(); // Get the Date object directly
                appointment.setAppointmentTime(date); // Set it back if needed
            }
        }

        // Add appointments to the model
        model.addAttribute("appointments", appointments);

        return "patient/view-appointments"; // JSP page for viewing appointments
    }*/
    
    @GetMapping("/appointments")
    public String viewAppointments(HttpSession session, Model model) {
        User patient = (User) session.getAttribute("loggedInUser");

        if (patient == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        // Fetch appointments for this patient
        List<Appointment> appointments = appointmentService.findAppointmentsByPatientId(patient.getId());

        // Add appointments to the model directly
        model.addAttribute("appointments", appointments);

        return "patient/view-appointments"; // JSP page for viewing appointments
    }

 // PatientDashboardController.java

    @PostMapping("/appointments/approve/{appointmentId}")
    public String approveAppointment(@PathVariable Long appointmentId, HttpSession session) {
        User doctor = (User) session.getAttribute("loggedInUser");

        if (doctor == null || !doctor.isDoctor()) {
            return "redirect:/login"; // Only doctors can approve
        }

        // Fetch appointment by ID
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);

        if (appointment != null && !appointment.isAppointmentApproved()) {
            appointment.setAppointmentApproved(true); // Set appointment as approved
            appointmentService.saveAppointment(appointment); // ⚡ USE correct save method
        }

        return "redirect:/doctor/appointments"; // Redirect to doctor's appointment page
    }

    @GetMapping("/my-profile")
    public String showPatientProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect if not logged in
        }

        model.addAttribute("patient", loggedInUser); // Pass user directly to JSP

        return "patient/profile"; // This will open /WEB-INF/views/patient/profile.jsp
    }
    
    
    
    
    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String mobileNo,
            HttpSession session,
            Model model
    ) {
        User patient = (User) session.getAttribute("loggedInUser");
        if (patient == null) {
            return "redirect:/login";
        }

        // Check if the new email already exists
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(patient.getId())) {
            // Email already exists for another user
            model.addAttribute("errorMessage", "This email is already in use. Please try a different one.");
            return "redirect:/patient/my-profile";  // Redirect back to the profile page
        }

        // Update the patient profile
        patient.setUsername(name);  // Correct method name
        patient.setEmail(email);
        patient.setMobileNo(mobileNo);
        userService.updateUser(patient);

        Profile profile = profileRepository.findByUserId(patient.getId());
        if (profile == null) {
            profile = new Profile();
            profile.setUser(patient);
        }
        profile.setuserName(name);  // Correct method name
        profile.setEmail(email);
        profile.setMobileNo(mobileNo);

        profileRepository.save(profile);

        session.setAttribute("loggedInUser", patient);

        return "redirect:/patient/my-profile?success";
    }
    
    
    @Autowired
    private PatientEnquiryService patientEnquiryService; // Autowiring the service to handle enquiries

    // This method displays the form for sending an enquiry
    @GetMapping("/send-enquiries")
    public String showEnquiryForm(Model model) {
        // You can add any additional attributes to the model if necessary
        return "patient/send-enquiries"; // This JSP page will display the form
    }
    
    @PostMapping("/send-enquiries")
    public String submitEnquiry(@RequestParam(required = false) String subject, 
                                 @RequestParam(required = false) String message,
                                 @RequestParam(required = false) String enquiryDetails,
                                 HttpSession session, Model model) {
        try {
            User patient = (User) session.getAttribute("loggedInUser");

            if (patient != null) {
                String patientName = patient.getUsername();
                if (patientName == null || patientName.isEmpty()) {
                    model.addAttribute("errorMessage", "Patient name is missing.");
                    return "send-enquiries";
                }

                PatientEnquiry enquiry = new PatientEnquiry();
                enquiry.setPatientName(patientName);
                enquiry.setSubject(subject != null ? subject : "");
                enquiry.setMessage(message != null ? message : "");
                enquiry.setStatus("Pending");

                patientEnquiryService.saveEnquiry(enquiry);
                model.addAttribute("message", "Your enquiry has been successfully submitted.");
            } else {
                model.addAttribute("errorMessage", "Please log in to submit an enquiry.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while submitting your enquiry.");
        }
        return "patient/send-enquiries";
    }

}
