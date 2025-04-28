package com.medipath.labcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medipath.labcare.entity.LabSample;

@Repository
public interface LabSampleRepository extends JpaRepository<LabSample, Long> 
{
    
}
