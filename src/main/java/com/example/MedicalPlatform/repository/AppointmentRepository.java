package com.example.MedicalPlatform.repository;

import com.example.MedicalPlatform.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // annotation @query, jbql, native query. for complex query
    List<Appointment> findAllByDoctor_DoctorId(Long doctorId);
    List<Appointment> findAllByPatient_PatientId(Long patientId);
    List<Appointment> findAllByDoctor_DoctorIdAndStatus(Long doctorDoctorId, String status);

    List<Appointment> findAllByPatient_PatientIdAndStatus(Long patientPatientId, String status);
    boolean existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(Long doctorDoctorId, LocalDate appointmentDate, LocalTime appointmentTime);
    List<Appointment> findAllByDoctor_DoctorIdAndAppointmentDateBetween(Long doctorDoctorId, LocalDate appointmentDateAfter, LocalDate appointmentDateBefore);

}
