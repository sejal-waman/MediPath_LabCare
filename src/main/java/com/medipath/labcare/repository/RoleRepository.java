package com.medipath.labcare.repository;

import com.medipath.labcare.entity.Role;



import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    //List<Role> findAllById(Set<Long> ids);
}
