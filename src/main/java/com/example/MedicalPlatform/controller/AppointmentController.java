package com.example.MedicalPlatform.controller;

import com.example.MedicalPlatform.dto.request.AppointmentRequestDTO;
import com.example.MedicalPlatform.dto.response.AppointmentResponseDTO;
import com.example.MedicalPlatform.entity.Appointment;
import com.example.MedicalPlatform.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDTO> getAllDoctorAppointments(@PathVariable Long doctorId){
        return appointmentService.getAllDoctorAppointments(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDTO> getAllPatientAppointments(@PathVariable Long patientId){
        return appointmentService.getAllPatientAppointments(patientId);
    }

    @PostMapping("/create/appointment")
    public String createAppointment(@Valid @RequestBody AppointmentRequestDTO request){
        return appointmentService.createAppointment(request);
    }
    @GetMapping("/doctor/{doctorId}/status")
    public List<AppointmentResponseDTO> getDoctorAppointmentsByStatus(@PathVariable Long doctorId, @RequestParam String status){
        return appointmentService.getDoctorAppointmentsByStatus(doctorId, status);
    }

    @GetMapping("/patient/{patientId}/status")
    public List<AppointmentResponseDTO> getPatientAppointmentsByStatus(@PathVariable Long patientId, @RequestParam String status){
        return appointmentService.getPatientAppointmentsByStatus(patientId, status);
    }

    @GetMapping("/doctor/{doctorId}/schedule")
    public List<AppointmentResponseDTO> getDoctorSchedule(@PathVariable Long doctorId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return appointmentService.getDoctorSchedule(doctorId, startDate, endDate);
    }

    @GetMapping("/doctor/{doctorId}/availability")
    public boolean isSpotAvailable(@PathVariable Long doctorId, @RequestParam LocalDate appointmentDate, @RequestParam LocalTime appointmentTime){
        return appointmentService.isSpotAvailable(doctorId, appointmentDate, appointmentTime);
    }

    @PutMapping("/{appointmentId}/confirm")
    public String confirmAppointment(@PathVariable Long appointmentId){
        return appointmentService.confirmAppointment(appointmentId);
    }

    @PutMapping("/{appointmentId}/complete")
    public String completeAppointment(@PathVariable Long appointmentId){
        return appointmentService.completeAppointment(appointmentId);
    }

    @PutMapping("/{appointmentId}/cancel")
    public String cancelAppointment(@PathVariable Long appointmentId){
        return appointmentService.cancelAppointment(appointmentId);
    }




}
