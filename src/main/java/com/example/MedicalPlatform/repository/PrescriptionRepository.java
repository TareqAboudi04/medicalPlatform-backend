package com.example.MedicalPlatform.repository;

import com.example.MedicalPlatform.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByAppointment_AppointmentId(Long appointmentAppointmentId);

}
