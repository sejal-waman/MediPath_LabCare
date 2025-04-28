package com.medipath.labcare.service.impl;

import com.medipath.labcare.model.SystemSettings;
import com.medipath.labcare.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    private SystemSettingsRepository settingRepository;

    /**
     * Loads system settings from the database.
     * If not found, creates default settings and saves it.
     */
    public SystemSettings load() {
        Optional<SystemSettings> optionalSettings = settingRepository.findById(1L);
        if (optionalSettings.isPresent()) {
            return optionalSettings.get();
        } else {
            SystemSettings defaultSettings = new SystemSettings();
            defaultSettings.setId(1L);
            defaultSettings.setAppName("MediPath LabCare");
            defaultSettings.setSupportEmail("support@medipath.com");
            return settingRepository.save(defaultSettings); // 👈 No typo here
        }
    }

    /**
     * Saves or updates the system settings with singleton record.
     */
    public void save(SystemSettings newSettings) {
        newSettings.setId(1L); // ensures only one record exists
        settingRepository.save(newSettings);
    }
}
