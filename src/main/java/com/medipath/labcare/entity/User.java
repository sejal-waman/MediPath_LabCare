package com.medipath.labcare.entity;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a User in the MediPath LabCare system.
 */
@Entity
@Table(name = "users")
public class User {

    // === Fields ===
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    private User assignedDoctor;

    @Column(name = "is_deleted")
    private boolean isDeleted;
    
    
 // Add below your other fields (like mobileNo, assignedDoctor etc.)

    @Column(name = "specialization")
    private String specialization;

    // Getter and Setter
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    
    @Column(name = "age")
    private Integer age;

    // Getter and Setter
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    
    @Column(name = "gender")
    private String gender;

    // Getter and Setter
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    
    
    private boolean appointmentApproved;  // Track appointment approval status

    // Getters and setters for appointmentApproved
    public boolean isAppointmentApproved() {
        return appointmentApproved;
    }

    public void setAppointmentApproved(boolean appointmentApproved) {
        this.appointmentApproved = appointmentApproved;
    }
    
    

    // === Constructors ===

    public User() {
        // Default constructor for JPA
    }

    public User(String email, String password, String username, boolean enabled, String mobileNo, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.mobileNo = mobileNo;
        this.roles = roles;
    }
    
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    // Getter and Setter
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    
    
    
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    // Getter and Setter
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }


    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public User getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(User assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    /**
     * Expose a single “role” property for JSP/EL.
     * Returns the first role’s name, or null if none assigned.
     */
    @Transient
    public String getRole() {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return roles.iterator().next().getName();
    }

    /**
     * Expose a "name" property for JSP/EL.
     * Returns the username (used in dropdowns).
     */
    @Transient
    public String getName() {
        return username;
    }

    // === Object Override Methods ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
    
    
    @Column(name = "selected_report_id") 
    private Long selectedReportId;


    public Long getSelectedReportId() {
        return selectedReportId;
    }
    
    public void setSelectedReportId(Long selectedReportId) {
        this.selectedReportId = selectedReportId;
    }

    public boolean isDoctor() {
        // Check if the user has the "DOCTOR" role
        if (roles != null) {
            for (Role role : roles) {
                if ("DOCTOR".equals(role.getName())) {
                    return true; // User is a doctor
                }
            }
        }
        return false; // User is not a doctor
    }

	

    


}
