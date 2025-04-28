package com.medipath.labcare.service;

import java.util.List;

import com.medipath.labcare.entity.Equipment;

public interface EquipmentService {

	List<Equipment> getAllEquipment();
    Equipment getById(Long id);
    Equipment save(Equipment equipment);
    void delete(Long id);

   Equipment findEquipmentById(long id);

}
