package com.example.MedicalPlatform.repository;

import com.example.MedicalPlatform.entity.Doctor;
import com.example.MedicalPlatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    
    Optional<Doctor> findByUser(User user);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByLocation(String location);
    List<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

}
