package com.callsprediction.demo.Users.QueryHandler;

import com.callsprediction.demo.Query;
import com.callsprediction.demo.Users.UserRepository;
import com.callsprediction.demo.Users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsers implements Query<Void, List<Users>> {

    private UserRepository userRepository;

    public GetAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Users>> execute(Void input) {
        List<Users> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
