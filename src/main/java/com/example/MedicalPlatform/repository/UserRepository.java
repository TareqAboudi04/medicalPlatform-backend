package com.example.MedicalPlatform.repository;

import com.example.MedicalPlatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByRole(String role);

    List<User> findAllByRoleAndStatus(String role, String status);

    long countByRoleAndStatus(String role, String status);
}
