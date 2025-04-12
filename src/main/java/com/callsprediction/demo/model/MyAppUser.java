package com.callsprediction.demo.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class MyAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MyAppUserRole role;

    // Constructors, Getters, Setters
    public MyAppUser() {
    }

    public MyAppUser(String username, String email, String password, MyAppUserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public MyAppUserRole getRole() {
        return role;
    }

    public void setRole(MyAppUserRole role) {
        this.role = role;
    }

    public UserDetails toUserDetails() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .roles(this.role.name())
                .build();
    }
}
