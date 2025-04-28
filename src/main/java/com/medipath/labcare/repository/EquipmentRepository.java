package com.medipath.labcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medipath.labcare.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>
{
	
}

