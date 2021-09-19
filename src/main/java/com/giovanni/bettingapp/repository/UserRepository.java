package com.giovanni.bettingapp.repository;

import com.giovanni.bettingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}