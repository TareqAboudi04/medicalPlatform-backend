package com.example.MedicalPlatform.service;

import com.example.MedicalPlatform.dto.request.AppointmentRequestDTO;
import com.example.MedicalPlatform.dto.response.AppointmentResponseDTO;
import com.example.MedicalPlatform.entity.Appointment;
import com.example.MedicalPlatform.entity.Doctor;
import com.example.MedicalPlatform.entity.Patient;
import com.example.MedicalPlatform.entity.User;
import com.example.MedicalPlatform.repository.AppointmentRepository;
import com.example.MedicalPlatform.repository.DoctorRepository;
import com.example.MedicalPlatform.repository.PatientRepository;
import com.example.MedicalPlatform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createAppointment(AppointmentRequestDTO request){

        User currentUser = getCurrentUser();

        if (!currentUser.getRole().equalsIgnoreCase("PATIENT")) {
            throw new RuntimeException("Only patients can book appointments");
        }

        Patient currentPatient = getCurrentPatient(currentUser);

        if (!currentPatient.getPatientId().equals(request.getPatientId())) {
            throw new RuntimeException("You cannot book an appointment for another patient");
        }

        if (!isSpotAvailable(request.getDoctorId(), request.getAppointmentDate(), request.getAppointmentTime())){
            return "Spot Is Not Available!";
        }
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor Not Found!"));
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new RuntimeException("Patient Not Found!"));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus("PENDING");

        appointmentRepository.save(appointment);

        return "Appointment booked successfully.";



    }

    public String updateAppointmentStatus(Long appointmentId, String status){

        User currentUser = getCurrentUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!isAdmin(currentUser)) {
            Doctor currentDoctor = getCurrentDoctor(currentUser);

            if (!appointment.getDoctor().getDoctorId().equals(currentDoctor.getDoctorId())) {
                throw new RuntimeException("You are not allowed to update another doctor's appointment");
            }
        }

        appointment.setStatus(status);
        appointmentRepository.save(appointment);

        return "Appointment Status is changed to " + status.toUpperCase();
    }

    public String confirmAppointment(Long appointmentId){
        return updateAppointmentStatus(appointmentId, "CONFIRMED");
    }

    public String completeAppointment(Long appointmentId){
        return updateAppointmentStatus(appointmentId, "COMPLETED");
    }

    public String cancelAppointment(Long appointmentId){
        return updateAppointmentStatus(appointmentId, "CANCELLED");
    }

    public List<AppointmentResponseDTO> getAllDoctorAppointments(Long doctorId){

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            Doctor currentDoctor = getCurrentDoctor(currentUser);

            if (!currentDoctor.getDoctorId().equals(doctorId)) {
                throw new RuntimeException("You are not allowed to view another doctor's appointments");
            }
        }

        return appointmentRepository.findAllByDoctor_DoctorId(doctorId).stream()
                .map(this::mapToAppointmentResponseDTO).toList();

    }
    public List<AppointmentResponseDTO> getAllPatientAppointments(Long patientId){

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            Patient currentPatient = getCurrentPatient(currentUser);

            if (!currentPatient.getPatientId().equals(patientId)) {
                throw new RuntimeException("You are not allowed to view another patient's appointments");
            }
        }

        return appointmentRepository.findAllByPatient_PatientId(patientId).stream()
                .map(this::mapToAppointmentResponseDTO).toList();
    }

    public List<AppointmentResponseDTO> getDoctorAppointmentsByStatus(Long doctorId, String status){

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            Doctor currentDoctor = getCurrentDoctor(currentUser);

            if (!currentDoctor.getDoctorId().equals(doctorId)) {
                throw new RuntimeException("You are not allowed to view another doctor's appointments");
            }
        }

        return appointmentRepository.findAllByDoctor_DoctorIdAndStatus(doctorId, status).stream()
                .map(this::mapToAppointmentResponseDTO).toList();
    }
    public List<AppointmentResponseDTO> getPatientAppointmentsByStatus(Long patientId, String status){

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            Patient currentPatient = getCurrentPatient(currentUser);

            if (!currentPatient.getPatientId().equals(patientId)) {
                throw new RuntimeException("You are not allowed to view another patient's appointments");
            }
        }

        return appointmentRepository.findAllByPatient_PatientIdAndStatus(patientId, status).stream()
                .map(this::mapToAppointmentResponseDTO).toList();
    }
    public List<AppointmentResponseDTO> getDoctorSchedule(Long doctorId, LocalDate startDate, LocalDate endDate){

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            Doctor currentDoctor = getCurrentDoctor(currentUser);

            if (!currentDoctor.getDoctorId().equals(doctorId)) {
                throw new RuntimeException("You are not allowed to view another doctor's schedule");
            }
        }

        return appointmentRepository.findAllByDoctor_DoctorIdAndAppointmentDateBetween(doctorId, startDate, endDate).stream()
                .map(this::mapToAppointmentResponseDTO).toList();
    }
    public boolean isSpotAvailable(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime){
        return !appointmentRepository.existsByDoctor_DoctorIdAndAppointmentDateAndAppointmentTime(doctorId, appointmentDate, appointmentTime);
    }

    private AppointmentResponseDTO mapToAppointmentResponseDTO(Appointment appointment) {
        String patientName = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        return new AppointmentResponseDTO(
                appointment.getAppointmentId(),
                appointment.getPatient().getPatientId(),
                patientName,
                appointment.getDoctor().getDoctorId(),
                doctorName,
                appointment.getDoctor().getSpecialization(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus()
        );
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
    }

    private boolean isAdmin(User user) {
        return user.getRole().equalsIgnoreCase("ADMIN");
    }

    private Patient getCurrentPatient(User user) {
        return patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
    }

    private Doctor getCurrentDoctor(User user) {
        return doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));
    }

}
