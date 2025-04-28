package com.medipath.labcare.service;

import com.medipath.labcare.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    /**
     * Authenticates a user based on email and password.
     *
     * @param email The email provided during login.
     * @param enteredPassword The password provided during login.
     * @return true if authentication is successful; false otherwise.
     */
    public boolean authenticateUser(String email, String enteredPassword) {
        Optional<User> user = userService.findByEmail(email);

        // Check if user is present
        if (user.isPresent()) {
            String storedPassword = user.get().getPassword();
            // Compare entered password with the stored password (plain text)
            return enteredPassword.equals(storedPassword);
        }
        return false;
    }
}
