package com.callsprediction.demo.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Integer> {

    Optional<MyAppUser> findByUsername(String Username);
}
