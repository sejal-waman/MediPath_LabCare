package com.medipath.labcare.utility;

import com.medipath.labcare.entity.Role;
import com.medipath.labcare.entity.User;

import java.util.Objects;

public class RoleChecker {

    public static boolean hasRole(User user, String role) {
        if (user == null || user.getRoles() == null || role == null || role.trim().isEmpty()) {
            return false;
        }

        // Check if the user's roles contain the specified role (case insensitive)
        return user.getRoles().stream()
                .map(Role::getName)
                .filter(Objects::nonNull)
                .anyMatch(r -> r.equalsIgnoreCase(role));
    }
}
