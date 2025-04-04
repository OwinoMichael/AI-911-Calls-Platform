package com.callsprediction.demo.Users;

import com.callsprediction.demo.Users.QueryHandler.GetAllUsers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private GetAllUsers getAllUsers;

    public UserController(GetAllUsers getAllUsers) {
        this.getAllUsers = getAllUsers;
    }

    @GetMapping("/")
    public ResponseEntity<List<Users>> getAllUsers(){
        return getAllUsers.execute(null);
    }
}
