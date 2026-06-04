package com.example.MedicalPlatform.repository;

import com.example.MedicalPlatform.entity.Patient;
import com.example.MedicalPlatform.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser(User user);
}
