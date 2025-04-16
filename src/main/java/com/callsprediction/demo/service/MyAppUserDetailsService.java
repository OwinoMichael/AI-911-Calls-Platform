package com.callsprediction.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.callsprediction.demo.repository.MyAppUserRepository;
import com.callsprediction.demo.model.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.Optional;

@Service
public class MyAppUserDetailsService implements UserDetailsService {

    @Autowired
    private MyAppUserRepository userRepository;

    public MyAppUser save(MyAppUser user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyAppUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return userObj.toUserDetails();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
