package com.medipath.labcare.service.impl;

import com.medipath.labcare.entity.Appointment;
import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;
import com.medipath.labcare.repository.AppointmentRepository;
import com.medipath.labcare.repository.RoleRepository;
import com.medipath.labcare.repository.UserRepository;
import com.medipath.labcare.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Constructor-based dependency injection
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Save a new user (without hashing password, store plaintext)
    @Override
    public void saveUser(User user) {
        userRepository.save(user);  // Save user with plaintext password
    }

    // Check if a user with the given email exists
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Check if a user with the given mobile number exists
    @Override
    public boolean existsByMobileNo(String mobileNo) {
        return userRepository.existsByMobileNo(mobileNo);
    }

    // Find a user by email
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find a user by username
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    // Register a new user after checking email and mobile uniqueness
    public boolean registerUser(User user) {
        // Check if the email or mobile number already exists
        if (existsByEmail(user.getEmail()) || existsByMobileNo(user.getMobileNo())) {
            return false; // Return false if email or mobile number already exists
        }

        // Save the user with plaintext password
        saveUser(user);
        return true;  // Registration successful
    }

    // Retrieve all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Check if the password matches the stored plaintext password
    @Override
    public boolean checkPassword(String plainPassword, String storedPassword) {
        // Directly compare the plaintext password with the stored password
        return plainPassword.equals(storedPassword);
    }

    // Find a user by ID
    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Return null if the user doesn't exist
    }

    // Delete a user by ID (soft delete for now)
    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        if (user != null) {
            user.setDeleted(true);  // Mark user as deleted
            saveUser(user);  // Save the updated user with deleted status
        }
    }

    // Update user details
    @Override
    public void updateUser(User user) {
        // Check if the user exists
        User existingUser = findById(user.getId());
        
        if (existingUser == null) {
            // If the user doesn't exist, handle the error or return early
            throw new IllegalArgumentException("User not found with id: " + user.getId());
        }

        // Update user details only if provided
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        
        if (user.getMobileNo() != null && !user.getMobileNo().isEmpty()) {
            existingUser.setMobileNo(user.getMobileNo());
        }
        
        // Update enabled status if it's provided
        existingUser.setEnabled(user.isEnabled());

        // Update password if it's provided and not empty
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());  // Save the password as plaintext
        }

        // Assign roles if provided
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            existingUser.setRoles(user.getRoles());
        }

        // Update age if provided
        if (user.getAge() != null) {
            existingUser.setAge(user.getAge());
        }

        // Update gender if provided
        if (user.getGender() != null && !user.getGender().isEmpty()) {
            existingUser.setGender(user.getGender());
        }

        // Save the updated user
        saveUser(existingUser);
    }

    // Assign roles to a user
    @Override
    public void assignRoleToUser(User user, Set<Role> roles) {
        user.setRoles(roles); // Assign roles to the user
        saveUser(user); // Save the updated user with new roles
    }

    // Get the currently logged-in user (custom logic for manual authentication using session)
    @Override
    public User getLoggedInUser(HttpSession session) {
        String username = (String) session.getAttribute("username");  // Assuming session stores the username
        return username != null ? findByUserName(username) : null;  // Return user by username stored in session
    }


    @Override
    public Role findByRId(Long roleId) {
        // Use the findById method to fetch the Role by ID from the database.
        return roleRepository.findById(roleId).orElse(null); // Return null if the Role is not found.
    }
    
    @Override
    public void assignRolesByIds(User user, Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }
    
    public List<User> findPatientsAssignedToDoctor(Long doctorId) {
        // Fetch the "PATIENT" role
        Role patientRole = roleRepository.findByName("PATIENT");
        
        if (patientRole == null) {
            // Handle the case if the role is not found (Optional)
            throw new IllegalArgumentException("Role 'PATIENT' not found");
        }

        // Fetch the assigned doctor entity
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        // Query for patients with the "PATIENT" role and assigned to the doctor
        return userRepository.findByRolesAndAssignedDoctor(patientRole, doctor);
    }

    @Override
    public List<User> getAllPatients() {
        return userRepository.findAllByRoles_Name("PATIENT");
    }

    @Override
    public List<User> findAllByRole(String roleName) {
        return userRepository.findAllByRoles_Name(roleName);
    }

    @Override
    public List<User> findAllDoctors() {
        // Find doctors by their role (assuming the role name is "DOCTOR")
        return userRepository.findAllByRoles_Name("DOCTOR");
    }

    @Override
    public User getUserById(Long id) {
        // Retrieve the user by its ID from the repository
        Optional<User> userOptional = userRepository.findById(id);
        
        // Return the user if found, otherwise return null (or throw an exception if needed)
        return userOptional.orElse(null);  // Return null if no user is found
    }

    

	@Override
    public List<User> findByRolesAndAssignedDoctor(Role role, User assignedDoctor) {
        return userRepository.findByRolesAndAssignedDoctor(role, assignedDoctor);
    }
	
    

	@Override
	public User getLoggedInUser() {
        // Implement logic to fetch the logged-in user
        // For example, using Spring Security or session
        return userRepository.findById(1L).orElse(null); // This is just an example; adapt accordingly
    }

	

	@Override
	 // Method to approve a patient's appointment
    public void approveAppointment(Long patientId) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Invalid patient ID"));
        patient.setAppointmentApproved(true);  // Set the appointment approval status
        userRepository.save(patient);  // Save the updated patient
    }
    
    
    

	@Override
    // Method to get the assigned patients for a doctor
    public List<User> getAssignedPatientsForDoctor(User doctor) {
        return userRepository.findPatientsAssignedToDoctor(doctor.getId());
    }
	
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public List<User> findAssignedPatientsByDoctor(Long doctorId) {
	    // Fetch the appointments for the doctor and get the corresponding patients
	    List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

	    // Extract the patients from the appointments
	    List<User> assignedPatients = new ArrayList<>();
	    for (Appointment appointment : appointments) {
	        User patient = appointment.getPatient();  // Assuming Appointment has a reference to the patient
	        assignedPatients.add(patient);
	    }

	    return assignedPatients;
	}

   
    
}
