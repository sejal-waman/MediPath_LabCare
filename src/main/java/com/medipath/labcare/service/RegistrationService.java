/*package com.medipath.labcare.service;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import com.medipath.labcare.entity.User;


public class RegistrationService
 {

    @Autowired
    private UserService userService;

 
    public void registerUser(User user) {
        // Hash the plain-text password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        // Save the user with the hashed password
        userService.saveUser(user);
    }
}  
*/


