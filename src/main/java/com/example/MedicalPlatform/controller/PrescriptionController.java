package com.example.MedicalPlatform.controller;

import com.example.MedicalPlatform.dto.request.PrescriptionRequestDTO;
import com.example.MedicalPlatform.dto.response.PrescriptionResponseDTO;
import com.example.MedicalPlatform.entity.Prescription;
import com.example.MedicalPlatform.service.PrescriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/create")
    public String createPrescription(@RequestBody PrescriptionRequestDTO request){
        return prescriptionService.createPrescription(request);
    }

    @GetMapping("/appointment/{appointmentId}")
    public PrescriptionResponseDTO getPrescriptionByAppointment(@PathVariable Long appointmentId){
       return prescriptionService.getPrescriptionByAppointment(appointmentId);
    }
}
