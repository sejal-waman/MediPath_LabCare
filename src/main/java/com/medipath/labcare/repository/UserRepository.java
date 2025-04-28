package com.medipath.labcare.repository;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobileNo);

    User findByUsername(String username);

    // Find users by role object and assigned doctor
    List<User> findByRolesAndAssignedDoctor(Role role, User assignedDoctor);

    // Find all users by role name
    List<User> findAllByRoles_Name(String roleName);

    // Find all users who have the 'DOCTOR' role
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'DOCTOR'")
    List<User> findAllDoctors();

    // Find all patients assigned to a specific doctor
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'PATIENT' AND u.assignedDoctor.id = :doctorId")
    List<User> findPatientsAssignedToDoctor(@Param("doctorId") Long doctorId);

}
