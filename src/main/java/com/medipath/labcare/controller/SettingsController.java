/*package com.medipath.labcare.controller;



import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.Settings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;



@Controller
@RequestMapping("/admin")
public class SettingsController {

    // Display settings page
    @GetMapping("/settings")
    public String showSettingsPage(Model model) {
        // Add any necessary attributes to the model
        return "admin/settings";
    }
    
    @PostMapping("/settings")
    public String updateSettings(@Valid @ModelAttribute("settings") Settings settings,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "admin/settings";
        }
        // Process the valid settings object
        // settingsService.save(settings);
        return "redirect:/admin/settings";
    }


}*/

