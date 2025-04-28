package com.medipath.labcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medipath.labcare.model.SystemSettings;

public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> 
{

	Optional<SystemSettings> findById(long l);

}
