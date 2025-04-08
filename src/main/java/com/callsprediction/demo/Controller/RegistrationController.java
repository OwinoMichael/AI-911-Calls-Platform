package com.callsprediction.demo.Controller;

import com.callsprediction.demo.Model.MyAppUser;
import com.callsprediction.demo.Model.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myAppUserRepository.save(user);
    }

    @PostMapping(value = "/admin/signup", consumes = "application/json")
    public MyAppUser registerAdmin(@RequestBody MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ADMIN"));
        return myAppUserRepository.save(user);
    }

    @PostMapping(value = "/superadmin/signup", consumes = "application/json")
    public MyAppUser registerSuperAdmin(@RequestBody MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("SUPER_ADMIN"));
        return myAppUserRepository.save(user);
    }

}
