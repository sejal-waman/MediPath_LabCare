/*package com.medipath.labcare.controller;



import com.medipath.labcare.entity.LabTest;
import com.medipath.labcare.service.LabTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/lab-tests")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    // Show list of all lab tests
    @GetMapping
    public String listLabTests(Model model) {
        List<LabTest> labTests = labTestService.getAllTests();
        model.addAttribute("labTests", labTests);
        return "admin/lab-test-list"; // JSP to show table of tests
    }

    // Show form to add new test
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("labTest", new LabTest());
        return "admin/lab-test-form";
    }

    // Show form to edit test
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        LabTest test = labTestService.getTestById(id);
        model.addAttribute("labTest", test);
        return "admin/lab-test-form";
    }

    // Save test (Add or Update)
    @PostMapping("/save")
    public String saveTest(@ModelAttribute("labTest") LabTest test) {
        labTestService.saveTest(test);
        return "redirect:/admin/lab-tests";
    }

    // Delete test
    @GetMapping("/delete/{id}")
    public String deleteTest(@PathVariable("id") Long id) {
        labTestService.deleteTest(id);
        return "redirect:/admin/lab-tests";
    }
}
*/