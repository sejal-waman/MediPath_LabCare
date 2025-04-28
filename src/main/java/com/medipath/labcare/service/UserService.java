package com.medipath.labcare.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;

import jakarta.servlet.http.HttpSession;

@Service
public interface UserService {

    void saveUser(User user);

    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobileNo);

    Optional<User> findByEmail(String email);

    User findByUserName(String username); // No Spring Security, just a direct database lookup

    boolean registerUser(User user);

    List<User> getAllUsers();

    boolean checkPassword(String plainPassword, String storedPassword); //  checking password manually

    User findById(Long id);

    void deleteUser(Long id);

    User getUserById(Long id);

    void updateUser(User user);

    User getLoggedInUser(); // Implement custom logic to get logged-in user

    void assignRoleToUser(User user, Set<Role> roles);

    User getLoggedInUser(HttpSession session); // Method to fetch user using session

	Role findByRId(Long roleId);
	
	void assignRolesByIds(User user, Set<Long> roleIds);

	List<User> findPatientsAssignedToDoctor(Long doctorId);

	
	List<User> getAllPatients();

	List<User> findAllByRole(String string);

	List<User> findAllDoctors();
	
	List<User> findByRolesAndAssignedDoctor(Role role, User assignedDoctor);

	void approveAppointment(Long patientId);

	List<User> findAssignedPatientsByDoctor(Long id);

	List<User> getAssignedPatientsForDoctor(User doctor);


}
