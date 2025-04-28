package com.medipath.labcare.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.Equipment;
import com.medipath.labcare.repository.EquipmentRepository;
import com.medipath.labcare.service.EquipmentService;

@Service

public class EquipmentServiceImpl implements EquipmentService
{

	@Autowired
	private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository repository) 
    {
        this.equipmentRepository = repository;
    }

    @Override
    public Equipment getById(Long id) 
    {
        return equipmentRepository.findById(id).orElse(null);
    }

    @Override
    public Equipment save(Equipment equipment)
    {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void delete(Long id) {
    	equipmentRepository.deleteById(id);
    }
    
    @Override
    public List<Equipment> getAllEquipment() {
        // returns all rows from the `equipment` table
        return equipmentRepository.findAll();
    }
    

    @Override
    public Equipment findEquipmentById(long id) {
        // look up by PK, return null if not found (or throw your own exception)
        Optional<Equipment> opt = equipmentRepository.findById(id);
        return opt.orElse(null);
    }

}
