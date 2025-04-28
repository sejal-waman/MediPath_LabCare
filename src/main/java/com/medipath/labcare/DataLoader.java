package com.medipath.labcare;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("DOCTOR");
        createRoleIfNotFound("PATIENT");
        createRoleIfNotFound("LABTECH");
        createRoleIfNotFound("RECEPTIONIST");
        System.out.println("✅ Roles created successfully if not already present.");
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("🔧 Created role: " + roleName);
        }
    }

}
