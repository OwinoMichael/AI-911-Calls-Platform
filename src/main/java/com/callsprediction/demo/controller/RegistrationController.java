package com.callsprediction.demo.controller;

import com.callsprediction.demo.model.MyAppUser;
import com.callsprediction.demo.model.MyAppUserRole;
import com.callsprediction.demo.repository.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup", consumes = "application/json")
    public MyAppUser registerUser(@RequestBody MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role is required.");
        }

        // Optional: validate against valid roles
        if (!isValidRole(user.getRole())) {
            throw new IllegalArgumentException("Invalid role provided.");
        }

        return myAppUserRepository.save(user);
    }

    private boolean isValidRole(MyAppUserRole role) {
        return role == MyAppUserRole.GENERAL ||
                role == MyAppUserRole.DEVELOPER ||
                role == MyAppUserRole.ADMIN;
    }
}