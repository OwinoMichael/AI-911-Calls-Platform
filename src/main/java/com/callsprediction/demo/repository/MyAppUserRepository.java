package com.callsprediction.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.callsprediction.demo.model.MyAppUser;

@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Integer> {
    Optional<MyAppUser> findByUsername(String username);
}